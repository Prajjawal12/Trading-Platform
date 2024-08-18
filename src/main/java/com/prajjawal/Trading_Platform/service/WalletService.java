package com.prajjawal.Trading_Platform.service;

import com.prajjawal.Trading_Platform.model.Orders;
import com.prajjawal.Trading_Platform.model.User;
import com.prajjawal.Trading_Platform.model.Wallet;

public interface WalletService {
  Wallet getUserWallet(User user);

  Wallet addBalanceToWallet(Wallet wallet, Long money);

  Wallet findWalletById(Long id) throws Exception;

  Wallet walletToWalletTransfer(User sender, Wallet receiverWallet, Long amount) throws Exception;

  Wallet payOrderPayment(Orders order, User user) throws Exception;
}
