package com.prajjawal.Trading_Platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prajjawal.Trading_Platform.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
