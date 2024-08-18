package com.prajjawal.Trading_Platform.service;

import com.prajjawal.Trading_Platform.domain.ORDER_TYPE;
import com.prajjawal.Trading_Platform.model.Coin;
import com.prajjawal.Trading_Platform.model.Orders;
import com.prajjawal.Trading_Platform.model.OrderItem;
import com.prajjawal.Trading_Platform.model.User;
import java.util.List;

public interface OrderService {
  Orders createOrder(User user, OrderItem orderItem, ORDER_TYPE order_TYPE);

  Orders getOrderById(Long orderId) throws Exception;

  List<Orders> getAllOrdersOfUser(Long userId, ORDER_TYPE order_TYPE, String assetSymbol);

  Orders processOrder(Coin coin, double quantity, ORDER_TYPE order_TYPE, User user) throws Exception;
}
