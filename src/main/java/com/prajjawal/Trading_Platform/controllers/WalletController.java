package com.prajjawal.Trading_Platform.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prajjawal.Trading_Platform.model.Orders;
import com.prajjawal.Trading_Platform.model.PaymentOrder;
import com.prajjawal.Trading_Platform.model.User;
import com.prajjawal.Trading_Platform.model.Wallet;
import com.prajjawal.Trading_Platform.model.WalletTransaction;
import com.prajjawal.Trading_Platform.service.OrderService;
import com.prajjawal.Trading_Platform.service.PaymentService;
import com.prajjawal.Trading_Platform.service.UserService;
import com.prajjawal.Trading_Platform.service.WalletService;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

  @Autowired
  private WalletService walletService;

  @Autowired
  private UserService userService;
  @Autowired
  private OrderService orderService;

  @Autowired
  private PaymentService paymentService;

  @GetMapping("/api/wallet")
  public ResponseEntity<Wallet> getUserWallet(@RequestHeader("Authorization") String jwt) throws Exception {
    User user = userService.findUserProfileByJwt(jwt);

    Wallet wallet = walletService.getUserWallet(user);

    return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
  }

  @PutMapping("/api/wallet/{walletId}/transfer")
  public ResponseEntity<Wallet> walletToWalletTransfer(
      @RequestHeader("Authorization") String jwt,
      @PathVariable Long walletId,
      @RequestBody WalletTransaction req) throws Exception {
    User senderUser = userService.findUserProfileByJwt(jwt);
    Wallet receiverWallet = walletService.findWalletById(walletId);

    Wallet wallet = walletService
        .walletToWalletTransfer(senderUser, receiverWallet,
            req.getAmount());

    return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
  }

  @PutMapping("/api/wallet/order/{orderId}/pay")
  public ResponseEntity<Wallet> payOrderPayment(
      @RequestHeader("Authorization") String jwt,
      @PathVariable Long orderId,
      @RequestBody WalletTransaction req) throws Exception {
    User user = userService.findUserProfileByJwt(jwt);
    Orders order = orderService.getOrderById(orderId);

    Wallet wallet = walletService.payOrderPayment(order, user);

    return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
  }

  @PutMapping("/api/wallet/order/deposit")
  public ResponseEntity<Wallet> addBalanceToWallet(
      @RequestHeader("Authorization") String jwt,
      @RequestParam(name = "order_id") Long orderId,
      @RequestParam(name = "payment_id") String paymentId, @RequestBody WalletTransaction req) throws Exception {
    User user = userService.findUserProfileByJwt(jwt);

    Wallet wallet = walletService.getUserWallet(user);
    PaymentOrder order = paymentService.getPaymentOrderById(orderId);
    Boolean status = paymentService.proceedPaymentOrder(order, paymentId);

    if (status) {
      wallet = walletService.addBalanceToWallet(wallet, order.getAmount());
    }

    return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
  }
}
