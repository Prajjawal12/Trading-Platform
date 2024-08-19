package com.prajjawal.Trading_Platform.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prajjawal.Trading_Platform.domain.PAYMENT_METHOD;
import com.prajjawal.Trading_Platform.model.PaymentOrder;
import com.prajjawal.Trading_Platform.model.User;
import com.prajjawal.Trading_Platform.response.PaymentResponse;
import com.prajjawal.Trading_Platform.service.PaymentService;
import com.prajjawal.Trading_Platform.service.UserService;

@RestController
@RequestMapping("/api")
public class PaymentController {
  @Autowired
  private UserService userService;

  @Autowired
  private PaymentService paymentService;

  @PostMapping("/api/payment/{paymentMethod}/amount/{amount}")
  public ResponseEntity<PaymentResponse> paymentHandler(
      @PathVariable PAYMENT_METHOD paymentMethod,
      @PathVariable Long amount,
      @RequestHeader("Authorization") String jwt) throws Exception {
    User user = userService.findUserProfileByJwt(jwt);
    PaymentResponse paymentResponse;

    PaymentOrder order = paymentService.createOrder(user, amount, paymentMethod);
    if (paymentMethod.equals(PAYMENT_METHOD.RAZORPAY)) {
      paymentResponse = paymentService.createRazorpayPaymentLink(user, amount);

    } else {
      paymentResponse = paymentService.createStripePaymentLink(user, amount, order.getId());
    }
    return new ResponseEntity<>(paymentResponse, HttpStatus.CREATED);
  }
}
