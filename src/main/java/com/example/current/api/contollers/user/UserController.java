package com.example.current.api.contollers.user;

import com.example.current.model.Address;
import com.example.current.model.LocalUser;
import com.example.current.services.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/secured/users")
@RequiredArgsConstructor
public class UserController {
    private final AddressService addressService;

    @GetMapping("/me")
    public ResponseEntity<LocalUser> getLoggedInUserProfile(@AuthenticationPrincipal LocalUser user) {
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{user-id}/addresses")
    public ResponseEntity<List<Address>> getUserAddresses(@AuthenticationPrincipal LocalUser user, @PathVariable("user-id") Long userId) {
        if (userHasPermission(user, userId)) {
            return ResponseEntity.ok(addressService.findAllAddressByUserId(userId));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

    }

    @PutMapping("/{user-id}/addresses")
    public ResponseEntity<Address> putAddress(@AuthenticationPrincipal LocalUser user, @PathVariable("user-id") Long userId, @RequestBody Address address) {
        if (userHasPermission(user, userId)) {
            address.setId(null);
            var refUser = new LocalUser();
            refUser.setId(userId);
            address.setUser(refUser);
            return ResponseEntity.ok(addressService.saveAddress(address));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PatchMapping("/{userId}/address/{addressId}")
    public ResponseEntity<Address> patchAddress(
            @AuthenticationPrincipal LocalUser user, @PathVariable Long userId,
            @PathVariable Long addressId, @RequestBody Address address) {
        if (!userHasPermission(user, userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (address.getId() == addressId) {
            Optional<Address> opOriginalAddress = addressService.getAddressById(addressId);
            if (opOriginalAddress.isPresent()) {
                LocalUser originalUser = opOriginalAddress.get().getUser();
                if (originalUser.getId() == userId) {
                    address.setUser(originalUser);
                    return ResponseEntity.ok(addressService.saveAddress(address));
                }
            }
        }
        return ResponseEntity.badRequest().build();
    }


    private boolean userHasPermission(LocalUser user, Long userId) {
        return user.getId() == userId;
    }

}
