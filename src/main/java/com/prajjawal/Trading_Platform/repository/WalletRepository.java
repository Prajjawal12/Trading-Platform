package com.prajjawal.Trading_Platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prajjawal.Trading_Platform.model.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
  Wallet findByUserId(Long userId);
}
