package com.prajjawal.Trading_Platform.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prajjawal.Trading_Platform.model.Coin;
import com.prajjawal.Trading_Platform.model.User;
import com.prajjawal.Trading_Platform.model.Watchlist;
import com.prajjawal.Trading_Platform.repository.WatchlistRepository;

@Service
public class WatchlistServiceImpl implements WatchListService {
  @Autowired
  private WatchlistRepository watchlistRepository;

  @Override
  public Watchlist findUserWatchList(Long userId) throws Exception {
    Watchlist watchlist = watchlistRepository.findByUserId(userId);
    if (watchlist == null) {
      throw new Exception("Watchlist Not Found!");
    }
    return watchlist;
  }

  @Override
  public Watchlist createWatchList(User user) {
    Watchlist watchlist = new Watchlist();

    watchlist.setUser(user);

    return watchlistRepository.save(watchlist);
  }

  @Override
  public Watchlist findById(long id) throws Exception {
    Optional<Watchlist> watchlistOptional = watchlistRepository.findById(id);

    if (watchlistOptional.isEmpty()) {
      throw new Exception("Watchlist Not Found");
    }

    return watchlistOptional.get();
  }

  @Override
  public Coin addItemToWatchList(Coin coin, User user) throws Exception {
    Watchlist watchlist = findUserWatchList(user.getId());

    if (watchlist.getCoin().contains(coin)) {
      watchlist.getCoin().remove(coin);
    } else
      watchlist.getCoin().add(coin);

    watchlistRepository.save(watchlist);
    return coin;
  }

}
