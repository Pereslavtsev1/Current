package com.example.current.api.contollers.order;

import com.example.current.model.LocalUser;
import com.example.current.model.WebOrder;
import com.example.current.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/secured/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @GetMapping()
    public ResponseEntity<List<WebOrder>> getOrders(@AuthenticationPrincipal LocalUser user) {
        return ResponseEntity.ok(orderService.getAllOrders(user));
    }
}
