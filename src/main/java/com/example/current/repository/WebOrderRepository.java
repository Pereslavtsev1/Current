package com.example.current.repository;

import com.example.current.model.LocalUser;
import com.example.current.model.WebOrder;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WebOrderRepository extends CrudRepository<WebOrder, Long> {
    List<WebOrder> findAllByUser(LocalUser user);
}
