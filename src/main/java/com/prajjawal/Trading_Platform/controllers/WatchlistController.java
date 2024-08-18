package com.prajjawal.Trading_Platform.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prajjawal.Trading_Platform.model.Coin;
import com.prajjawal.Trading_Platform.model.User;
import com.prajjawal.Trading_Platform.model.Watchlist;
import com.prajjawal.Trading_Platform.service.CoinService;
import com.prajjawal.Trading_Platform.service.UserService;
import com.prajjawal.Trading_Platform.service.WatchListService;

@RestController
@RequestMapping("/api/watchlist")
public class WatchlistController {
  @Autowired
  private WatchListService watchListService;

  @Autowired
  private CoinService coinService;

  @Autowired
  private UserService userService;

  @GetMapping("/user")
  public ResponseEntity<Watchlist> getUserWatchlist(
      @RequestHeader("Authorization") String jwt

  ) throws Exception {
    User user = userService.findUserProfileByJwt(jwt);

    Watchlist watchlist = watchListService.findUserWatchList(user.getId());

    return ResponseEntity.ok(watchlist);
  }

  @GetMapping("/{watchlistId}")
  public ResponseEntity<Watchlist> getWatchlistById(
      @PathVariable Long watchlistId) throws Exception {
    Watchlist watchlist = watchListService.findById(watchlistId);
    return ResponseEntity.ok(watchlist);
  }

  @PatchMapping("/add/coin/{coinId}")
  public ResponseEntity<Coin> addItemToWatchList(
      @RequestHeader("Authorization") String jwt,
      @PathVariable String coinId) throws Exception {
    User user = userService.findUserProfileByJwt(jwt);
    Coin coin = coinService.findById(coinId);
    Coin addedCoin = watchListService.addItemToWatchList(coin, user);
    return ResponseEntity.ok(addedCoin);
  }
}
