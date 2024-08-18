package com.prajjawal.Trading_Platform.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prajjawal.Trading_Platform.domain.ORDER_TYPE;
import com.prajjawal.Trading_Platform.model.Coin;
import com.prajjawal.Trading_Platform.model.Orders;
import com.prajjawal.Trading_Platform.model.User;
import com.prajjawal.Trading_Platform.request.CreateOrderRequest;
import com.prajjawal.Trading_Platform.service.CoinService;
import com.prajjawal.Trading_Platform.service.OrderService;
import com.prajjawal.Trading_Platform.service.UserService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
  @Autowired

  private OrderService orderService;
  @Autowired

  private UserService userService;

  @Autowired
  private CoinService coinService;

  // @Autowired
  // private WalletTransactionService walletTransactionService;

  @PostMapping("/pay")
  public ResponseEntity<Orders> payOrderPayment(
      @RequestHeader("Authorization") String jwt,
      @RequestBody CreateOrderRequest req) throws Exception {
    User user = userService.findUserProfileByJwt(jwt);
    Coin coin = coinService.findById(req.getCoinId());

    Orders order = orderService.processOrder(coin, req.getQuantity(), req.getOrder_TYPE(), user);

    return ResponseEntity.ok(order);
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<Orders> getOrderById(
      @RequestHeader("Authorization") String jwt,
      @PathVariable Long orderId) throws Exception {

    User user = userService.findUserProfileByJwt(jwt);

    Orders order = orderService.getOrderById(orderId);

    if (order.getUser().getId().equals(user.getId())) {
      return ResponseEntity.ok(order);
    } else {

      throw new Exception("You don't have access!");
    }
  }

  @GetMapping()
  public ResponseEntity<List<Orders>> getAllOrdersForUser(
      @RequestHeader("Authorization") String jwt,
      @RequestParam(required = false) ORDER_TYPE order_type,
      @RequestParam(required = false) String asset_symbol) throws Exception {
    Long userId = userService.findUserProfileByJwt(jwt).getId();
    List<Orders> userOrders = orderService.getAllOrdersOfUser(userId, order_type, asset_symbol);
    return ResponseEntity.ok(userOrders);
  }
}
