package com.prajjawal.Trading_Platform.service;

import com.prajjawal.Trading_Platform.model.PaymentDetails;
import com.prajjawal.Trading_Platform.model.User;

public interface PaymentDetailsService {
  public PaymentDetails addPaymentDetails(String accountNumber, String accountHolderName, String ifsc, String bankName,
      User user);

  public PaymentDetails getUsersPaymentDetails(User user);
}
