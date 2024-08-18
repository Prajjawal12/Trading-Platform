package com.prajjawal.Trading_Platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prajjawal.Trading_Platform.model.Watchlist;

public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {
  Watchlist findByUserId(Long userId);
}
