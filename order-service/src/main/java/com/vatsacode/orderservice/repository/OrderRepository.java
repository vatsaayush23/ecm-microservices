package com.vatsacode.orderservice.repository;

import com.vatsacode.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    //
}
