package com.prajjawal.Trading_Platform.service;

import com.prajjawal.Trading_Platform.domain.PAYMENT_METHOD;
import com.prajjawal.Trading_Platform.model.PaymentOrder;
import com.prajjawal.Trading_Platform.model.User;
import com.prajjawal.Trading_Platform.response.PaymentResponse;
import com.razorpay.RazorpayException;

public interface PaymentService {
  PaymentOrder createOrder(User user, Long amount, PAYMENT_METHOD payment_METHOD);

  PaymentOrder getPaymentOrderById(Long id) throws Exception;

  Boolean proceedPaymentOrder(PaymentOrder paymentOrder, String paymentId) throws RazorpayException;

  PaymentResponse createRazorpayPaymentLink(User user, Long amount);

  PaymentResponse createStripePaymentLink(User user, Long amount, Long orderId);

}
