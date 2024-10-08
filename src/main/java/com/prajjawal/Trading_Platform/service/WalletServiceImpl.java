package com.prajjawal.Trading_Platform.service;

import org.apache.el.lang.ELArithmetic.BigDecimalDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prajjawal.Trading_Platform.domain.ORDER_TYPE;
import com.prajjawal.Trading_Platform.model.Orders;
import com.prajjawal.Trading_Platform.model.User;
import com.prajjawal.Trading_Platform.model.Wallet;
import com.prajjawal.Trading_Platform.repository.WalletRepository;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {
  @Autowired
  private WalletRepository walletRepository;

  @Override
  public Wallet getUserWallet(User user) {
    Wallet wallet = walletRepository.findByUserId(user.getId());
    if (wallet == null) {
      wallet = new Wallet();
      wallet.setUser(user);
    }
    return wallet;
  }

  @Override
  public Wallet addBalanceToWallet(Wallet wallet, Long money) {
    BigDecimal balance = wallet.getBalance();

    BigDecimal newBalance = balance.add(BigDecimal.valueOf(money));
    wallet.setBalance(newBalance);
    return walletRepository.save(wallet);
  }

  @Override
  public Wallet findWalletById(Long id) throws Exception {
    Optional<Wallet> wallet = walletRepository.findById(id);
    if (wallet.isPresent()) {
      return wallet.get();
    }
    throw new Exception("Wallet Not Found!!");
  }

  @Override
  public Wallet walletToWalletTransfer(User sender, Wallet receiverWallet, Long amount) throws Exception {
    Wallet senderWallet = getUserWallet(sender);

    if (senderWallet.getBalance().compareTo(BigDecimal.valueOf(amount)) < 0) {
      throw new Exception("Insufficient Balance");
    }
    BigDecimal senderBalance = senderWallet.getBalance().subtract(BigDecimal.valueOf(amount));
    senderWallet.setBalance(senderBalance);
    walletRepository.save(senderWallet);
    BigDecimal receiverBalance = receiverWallet.getBalance().add(BigDecimal.valueOf(amount));
    receiverWallet.setBalance(receiverBalance);
    return senderWallet;
  }

  @Override
  public Wallet payOrderPayment(Orders order, User user) throws Exception {
    Wallet wallet = getUserWallet(user);
    if (order.getOrder_TYPE().equals(ORDER_TYPE.BUY)) {
      BigDecimal newBalance = wallet.getBalance().subtract(order.getPrice());
      if (newBalance.compareTo(order.getPrice()) < 0) {
        throw new Exception("Insufficient Funds for this transaction");

      }
      wallet.setBalance(newBalance);
    } else {
      BigDecimal newBalance = wallet.getBalance().add(order.getPrice());
      wallet.setBalance(newBalance);
    }
    walletRepository.save(wallet);
    return wallet;

  }

}
