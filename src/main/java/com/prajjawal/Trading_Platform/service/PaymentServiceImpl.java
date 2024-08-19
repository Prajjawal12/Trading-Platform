package com.prajjawal.Trading_Platform.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import com.prajjawal.Trading_Platform.domain.PAYMENT_METHOD;
import com.prajjawal.Trading_Platform.domain.PAYMENT_ORDER_STATUS;
import com.prajjawal.Trading_Platform.model.PaymentOrder;
import com.prajjawal.Trading_Platform.model.User;
import com.prajjawal.Trading_Platform.repository.PaymentOrderRepository;
import com.prajjawal.Trading_Platform.response.PaymentResponse;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Service
public class PaymentServiceImpl implements PaymentService {
  @Autowired
  private PaymentOrderRepository paymentOrderRepository;
  @Value("${stripe.api.key}")
  private String stripeSecretKey;
  @Value("${razorpay.api.key}")
  private String razorpayApiKey;

  @Value("{${razorpay.api.secret}")
  private String apiSecretKey;

  @Override
  public PaymentOrder createOrder(User user, Long amount, PAYMENT_METHOD payment_METHOD) {
    PaymentOrder paymentOrder = new PaymentOrder();
    paymentOrder.setUser(user);
    paymentOrder.setAmount(amount);
    paymentOrder.setPayment_METHOD(payment_METHOD);

    return paymentOrderRepository.save(paymentOrder);
  }

  @Override
  public PaymentOrder getPaymentOrderById(Long id) throws Exception {
    return paymentOrderRepository.findById(id).orElseThrow(() -> new Exception("Payment Order Not Found"));
  }

  @Override
  public Boolean proceedPaymentOrder(PaymentOrder paymentOrder, String paymentId) throws RazorpayException {
    if (paymentOrder.getPayment_ORDER_STATUS().equals(PAYMENT_ORDER_STATUS.PENDING)) {

      if (paymentOrder.getPayment_METHOD().equals(PAYMENT_METHOD.RAZORPAY)) {

        RazorpayClient razorpay = new RazorpayClient(razorpayApiKey, apiSecretKey);

        Payment payment = razorpay.payments.fetch(paymentId);

        Integer amount = payment.get("amount");
        String status = payment.get("status");

        if (status.equals("captured")) {
          paymentOrder.setPayment_ORDER_STATUS(PAYMENT_ORDER_STATUS.SUCCESS);
          paymentOrderRepository.save(paymentOrder);
          return true;
        } else {

          paymentOrder.setPayment_ORDER_STATUS(PAYMENT_ORDER_STATUS.FAILED);
          paymentOrderRepository.save(paymentOrder);
          return false;
        }
      }
    }

    return false;
  }

  @Override
  public PaymentResponse createRazorpayPaymentLink(User user, Long amount) throws RazorpayException {
    Long Amount = amount * 100;
    try {
      RazorpayClient razorpay = new RazorpayClient(razorpayApiKey, apiSecretKey);
      JSONObject paymentLinkRequest = new JSONObject();
      paymentLinkRequest.put("amount", Amount);
      paymentLinkRequest.put("currency", "INR");

      JSONObject customer = new JSONObject();
      customer.put("name", user.getFullName());
      customer.put("email", user.getEmail());
      paymentLinkRequest.put("customer", customer);
      JSONObject notify = new JSONObject();
      notify.put("email", true);
      paymentLinkRequest.put("notify", notify);
      paymentLinkRequest.put("reminder_enable", true);
      paymentLinkRequest.put("callback_url", "http://localhost:5173/wallet");
      paymentLinkRequest.put("callback_method", "get");

      PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);
      String paymentLinkId = payment.get("id");
      String paymentLinkUrl = payment.get("short_url");

      PaymentResponse res = new PaymentResponse();
      res.setPayment_url(paymentLinkUrl);
      return res;
    } catch (Exception e) {
      System.out.println("Error Creating Payment Link: " + e.getMessage());
      throw new RazorpayException(e.getMessage());
    }

  }

  @Override
  public PaymentResponse createStripePaymentLink(User user, Long amount, Long orderId) {
    Stripe.apiKey = stripeSecretKey;

    try {
      // Create the session parameters
      SessionCreateParams params = SessionCreateParams.builder()
          .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
          .setMode(SessionCreateParams.Mode.PAYMENT)
          .setSuccessUrl("http://localhost:5173/wallet?order_id=" + orderId)
          .setCancelUrl("http://localhost:5173/payment/cancel")
          .addLineItem(
              SessionCreateParams.LineItem.builder()
                  .setQuantity(1L)
                  .setPriceData(
                      SessionCreateParams.LineItem.PriceData.builder()
                          .setCurrency("usd")
                          .setUnitAmount(amount * 100) // Amount should be in cents
                          .setProductData(
                              SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                  .setName("Top Up Wallet")
                                  .build())
                          .build())
                  .build())
          .build();

      Session session = Session.create(params);
      System.out.println("session ________ " + session);
      PaymentResponse res = new PaymentResponse();

      res.setPayment_url(session.getUrl());
      return res;

    } catch (StripeException e) {
      e.printStackTrace();
      return null;
    }
  }

}