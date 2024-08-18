package com.prajjawal.Trading_Platform.controllers;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.prajjawal.Trading_Platform.domain.WALLET_TRANSACTION_TYPE;
import com.prajjawal.Trading_Platform.model.User;
import com.prajjawal.Trading_Platform.model.Wallet;
import com.prajjawal.Trading_Platform.model.Withdrawal;
import com.prajjawal.Trading_Platform.service.UserService;
import com.prajjawal.Trading_Platform.service.WalletService;
import com.prajjawal.Trading_Platform.service.WithdrawalService;

@RestController
public class WithdrawalController {
  @Autowired
  private WithdrawalService withdrawalService;

  @Autowired
  private WalletService walletService;

  @Autowired
  private UserService userService;

  // @Autowired
  // private WalletTransactionService walletTransactionService;
  @PostMapping("/api/withdrawal/{amount}")
  public ResponseEntity<?> withdrawalRequest(
      @PathVariable Long amount,
      @RequestHeader("Authorization") String jwt) throws Exception {
    User user = userService.findUserProfileByJwt(jwt);
    Wallet userWallet = walletService.getUserWallet(user);

    Withdrawal withdrawal = withdrawalService.requestWithdrawal(amount, user);
    walletService.addBalanceToWallet(userWallet, -withdrawal.getAmount());

    // WalletTransaction WalletTransaction =
    // walletTransactionService.createTransaction(
    // userWallet,
    // WALLET_TRANSACTION_TYPE.WITHDRAWAL,
    // null,
    // "Withdrawal From Bank Account",
    // withdrawal.getAmount());
    return new ResponseEntity<>(withdrawal, HttpStatus.OK);
  }

  @PatchMapping("/api/admin/withdrawal/{id}/proceed/{accept}")
  public ResponseEntity<?> proceedWithWithdrawal(
      @PathVariable Long id,
      @PathVariable boolean accept,
      @RequestHeader("Authorization") String jwt) throws Exception {

    User user = userService.findUserProfileByJwt(jwt);
    Withdrawal withdrawal = withdrawalService.proceedWithWithdrawal(id, accept);

    Wallet userWallet = walletService.getUserWallet(user);
    if (!accept) {
      walletService.addBalanceToWallet(userWallet, withdrawal.getAmount());
    }

    return new ResponseEntity<>(withdrawal, HttpStatus.OK);
  }

  @GetMapping("/api/withdrawal")
  public ResponseEntity<List<Withdrawal>> getWithdrawalHistory(
      @RequestHeader("Authorization") String jwt) throws Exception {
    User user = userService.findUserProfileByJwt(jwt);
    List<Withdrawal> withdrawal = withdrawalService.getUserWithdrawalHistory(user);
    return new ResponseEntity<>(withdrawal, HttpStatus.OK);

  }

  @GetMapping("/api/admin/withdrawal")
  public ResponseEntity<List<Withdrawal>> getAllWithdrawalRequest(
      @RequestHeader("Authorization") String jwt) throws Exception {
    User user = userService.findUserProfileByJwt(jwt);

    List<Withdrawal> withdrawals = withdrawalService.getAllWithdrawalRequest();

    return new ResponseEntity<>(withdrawals, HttpStatus.OK);
  }
}
