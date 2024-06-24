package com.example.current.services;

import com.example.current.model.LocalUser;
import com.example.current.model.WebOrder;
import com.example.current.repository.WebOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final WebOrderRepository orderRepository;
    public List<WebOrder> getAllOrders(LocalUser user) {
        return orderRepository.findAllByUser(user);

    }
}
