package com.prajjawal.Trading_Platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prajjawal.Trading_Platform.model.Orders;

public interface OrderRepository extends JpaRepository<Orders, Long> {
  List<Orders> findByUserId(Long userId);
}
