package com.prajjawal.Trading_Platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prajjawal.Trading_Platform.model.Coin;

public interface CoinRepository extends JpaRepository<Coin, String> {

}
