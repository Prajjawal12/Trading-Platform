package com.prajjawal.Trading_Platform.model;

import com.prajjawal.Trading_Platform.domain.PAYMENT_METHOD;
import com.prajjawal.Trading_Platform.domain.PAYMENT_ORDER_STATUS;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class PaymentOrder {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private Long amount;

  private PAYMENT_ORDER_STATUS payment_ORDER_STATUS;

  private PAYMENT_METHOD payment_METHOD;

  @ManyToOne
  private User user;

}
