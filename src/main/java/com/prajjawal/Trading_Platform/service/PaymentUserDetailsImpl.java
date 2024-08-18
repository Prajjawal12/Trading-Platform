package com.prajjawal.Trading_Platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prajjawal.Trading_Platform.model.PaymentDetails;
import com.prajjawal.Trading_Platform.model.User;
import com.prajjawal.Trading_Platform.repository.PaymentDetailsRepository;

@Service
public class PaymentUserDetailsImpl implements PaymentDetailsService {
  @Autowired
  private PaymentDetailsRepository paymentDetailsRepository;

  @Override
  public PaymentDetails addPaymentDetails(String accountNumber, String accountHolderName, String ifsc, String bankName,
      User user) {
    PaymentDetails paymentDetails = new PaymentDetails();
    paymentDetails.setAccountHolderName(accountHolderName);
    paymentDetails.setUser(user);
    paymentDetails.setBankName(bankName);
    paymentDetails.setAccountNumber(accountNumber);
    paymentDetails.setIfsc(ifsc);
    return paymentDetailsRepository.save(paymentDetails);
  }

  @Override
  public PaymentDetails getUsersPaymentDetails(User user) {

    return paymentDetailsRepository.findByUserId(user.getId());
  }
}