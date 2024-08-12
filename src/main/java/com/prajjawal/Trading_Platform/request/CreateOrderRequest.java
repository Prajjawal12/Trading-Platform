package com.prajjawal.Trading_Platform.request;

import com.prajjawal.Trading_Platform.domain.ORDER_TYPE;

import lombok.Data;

@Data
public class CreateOrderRequest {
  private String coinId;
  private double quantity;
  private ORDER_TYPE order_TYPE;
}
