package com.prajjawal.Trading_Platform.service;

import com.prajjawal.Trading_Platform.model.Coin;
import com.prajjawal.Trading_Platform.model.User;
import com.prajjawal.Trading_Platform.model.Watchlist;

public interface WatchListService {
  Watchlist findUserWatchList(Long userId) throws Exception;

  Watchlist createWatchList(User user);

  Watchlist findById(long id) throws Exception;

  Coin addItemToWatchList(Coin coin, User user) throws Exception;
}
