package com.prajjawal.Trading_Platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.prajjawal.Trading_Platform.domain.PAYMENT_METHOD;
import com.prajjawal.Trading_Platform.domain.PAYMENT_ORDER_STATUS;
import com.prajjawal.Trading_Platform.model.PaymentOrder;
import com.prajjawal.Trading_Platform.model.User;
import com.prajjawal.Trading_Platform.repository.PaymentOrderRepository;
import com.prajjawal.Trading_Platform.response.PaymentResponse;
import com.razorpay.Payment;
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
  public PaymentResponse createRazorpayPaymentLink(User user, Long amount) {
    Long Amount = amount * 100;

  }

  @Override
  public PaymentResponse createStripePaymentLink(User user, Long amount, Long orderId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'createStripePaymentLink'");
  }

}
