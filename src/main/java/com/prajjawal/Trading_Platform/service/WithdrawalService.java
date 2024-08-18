package com.prajjawal.Trading_Platform.service;

import java.util.List;
import com.prajjawal.Trading_Platform.model.User;
import com.prajjawal.Trading_Platform.model.Withdrawal;

public interface WithdrawalService {
  Withdrawal requestWithdrawal(Long amount, User user);

  Withdrawal proceedWithWithdrawal(Long withdrawalId, boolean accept) throws Exception;

  List<Withdrawal> getUserWithdrawalHistory(User user);

  List<Withdrawal> getAllWithdrawalRequest();
}
