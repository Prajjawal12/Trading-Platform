package com.prajjawal.Trading_Platform.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prajjawal.Trading_Platform.domain.WITHDRAWAL_STATUS;
import com.prajjawal.Trading_Platform.model.User;
import com.prajjawal.Trading_Platform.model.Withdrawal;
import com.prajjawal.Trading_Platform.repository.WithdrawalRepository;
import java.util.Optional;

@Service
public class WithdrawalServiceImpl implements WithdrawalService {
  @Autowired
  private WithdrawalRepository withdrawalRepository;

  @Override
  public Withdrawal requestWithdrawal(Long amount, User user) {
    Withdrawal withdrawal = new Withdrawal();
    withdrawal.setAmount(amount);
    withdrawal.setUser(user);
    withdrawal.setWithdrawal_STATUS(WITHDRAWAL_STATUS.PENDING);
    return withdrawalRepository.save(withdrawal);

  }

  @Override
  public Withdrawal proceedWithWithdrawal(Long withdrawalId, boolean accept) throws Exception {
    Optional<Withdrawal> withdrawal = withdrawalRepository.findById(withdrawalId);
    if (withdrawal.isEmpty()) {
      throw new Exception("Withdrawal Not Found!");
    }

    Withdrawal withdrawal1 = withdrawal.get();

    withdrawal1.setDate(LocalDateTime.now());

    if (accept) {
      withdrawal1.setWithdrawal_STATUS(WITHDRAWAL_STATUS.SUCCESS);
    } else {
      withdrawal1.setWithdrawal_STATUS(WITHDRAWAL_STATUS.PENDING);
    }
    return withdrawalRepository.save(withdrawal1);
  }

  @Override
  public List<Withdrawal> getUserWithdrawalHistory(User user) {
    return withdrawalRepository.findByUserId(user.getId());
  }

  @Override
  public List<Withdrawal> getAllWithdrawalRequest() {
    return withdrawalRepository.findAll();
  }

}
