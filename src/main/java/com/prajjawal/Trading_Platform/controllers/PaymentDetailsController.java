package com.prajjawal.Trading_Platform.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prajjawal.Trading_Platform.model.PaymentDetails;
import com.prajjawal.Trading_Platform.model.User;
import com.prajjawal.Trading_Platform.service.PaymentDetailsService;
import com.prajjawal.Trading_Platform.service.UserService;

@RestController
@RequestMapping("/api")
public class PaymentDetailsController {
  @Autowired
  private UserService userService;

  @Autowired
  private PaymentDetailsService paymentDetailsService;

  @PostMapping("/payment-details")
  public ResponseEntity<PaymentDetails> addPaymentDetails(
      @RequestBody PaymentDetails paymentDetailsRequest,
      @RequestHeader("Authorization") String jwt) throws Exception {
    PaymentDetails paymentDetails = paymentDetailsService.addPaymentDetails(
        paymentDetailsRequest.getAccountNumber(),
        paymentDetailsRequest.getAccountHolderName(),
        paymentDetailsRequest.getIfsc(),
        paymentDetailsRequest.getBankName(),
        paymentDetailsRequest.getUser());

    return new ResponseEntity<>(paymentDetails, HttpStatus.CREATED);
  }

  @GetMapping("payment-details")
  public ResponseEntity<PaymentDetails> getUsersPaymentDetails(
      @RequestHeader("Authorization") String jwt) throws Exception {
    User user = userService.findUserProfileByJwt(jwt);
    PaymentDetails paymentDetails = paymentDetailsService.getUsersPaymentDetails(user);
    return new ResponseEntity<>(paymentDetails, HttpStatus.CREATED);
  }
}
