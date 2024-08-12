package com.prajjawal.Trading_Platform.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prajjawal.Trading_Platform.domain.ORDER_STATUS;
import com.prajjawal.Trading_Platform.domain.ORDER_TYPE;
import com.prajjawal.Trading_Platform.model.Asset;
import com.prajjawal.Trading_Platform.model.Coin;
import com.prajjawal.Trading_Platform.model.Order;
import com.prajjawal.Trading_Platform.model.OrderItem;
import com.prajjawal.Trading_Platform.model.User;
import com.prajjawal.Trading_Platform.repository.OrderItemRepository;
import com.prajjawal.Trading_Platform.repository.OrderRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderServiceImple implements OrderService {
  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private AssetService assetService;
  @Autowired
  private WalletService walletService;
  @Autowired
  private OrderItemRepository orderItemRepository;

  @Override
  public Order createOrder(User user, OrderItem orderItem, ORDER_TYPE order_TYPE) {

    double price = orderItem.getCoin().getCurrentPrice() * orderItem.getQuantity();
    Order order = new Order();
    order.setUser(user);
    order.setOrderItem(orderItem);
    order.setOrder_TYPE(order_TYPE);
    order.setPrice(BigDecimal.valueOf(price));
    order.setTimeStamp(LocalDateTime.now());
    order.setStatus(ORDER_STATUS.PENDING);
    return orderRepository.save(order);
  }

  @Override
  public Order getOrderById(Long orderId) throws Exception {
    return orderRepository.findById(orderId).orElseThrow(() -> new Exception("Order Not Found"));
  }

  @Override
  public List<Order> getAllOrdersOfUser(Long userId, ORDER_TYPE order_TYPE, String assetSymbol) {
    return orderRepository.findByUserId(userId);
  }

  private OrderItem createOrderItem(Coin coin, double quantity, double buyPrice, double sellPrice) {
    OrderItem orderItem = new OrderItem();
    orderItem.setCoin(coin);
    orderItem.setQuantity(quantity);
    orderItem.setBuyPrice(buyPrice);
    orderItem.setSellPrice(sellPrice);
    return orderItemRepository.save(orderItem);
  }

  @Transactional
  public Order buyAsset(Coin coin, double quantity, User user) throws Exception {
    if (quantity <= 0) {
      throw new Exception("Quantity cannot be lesser than or equal to Zero");

    }
    double buyPrice = coin.getCurrentPrice();
    OrderItem orderItem = createOrderItem(coin, quantity, buyPrice, 0);
    Order order = createOrder(user, orderItem, ORDER_TYPE.BUY);
    orderItem.setOrder(order);
    walletService.payOrderPayment(order, user);
    order.setStatus(ORDER_STATUS.SUCCESS);
    order.setOrder_TYPE(ORDER_TYPE.BUY);
    Order savedOrder = orderRepository.save(order);
    Asset oldAsset = assetService.findAssetByUserIdAndCoinId(order.getUser().getId(),
        order.getOrderItem().getCoin().getId());
    if (oldAsset == null) {
      assetService.createAsset(user, orderItem.getCoin(), orderItem.getQuantity());
    } else {
      assetService.updateAsset(oldAsset.getId(), quantity);
    }
    return savedOrder;
  }

  @SuppressWarnings("unused")
  @Transactional
  public Order sellAsset(Coin coin, double quantity, User user) throws Exception {
    if (quantity <= 0) {
      throw new Exception("Quantity cannot be lesser than or equal to Zero");

    }

    double sellPrice = coin.getCurrentPrice();
    Asset assetToSell = assetService.findAssetByUserIdAndCoinId(user.getId(), coin.getId());
    double buyPrice = assetToSell.getBuyPrice();
    if (assetToSell != null) {
      OrderItem orderItem = createOrderItem(coin, quantity, buyPrice, sellPrice);

      Order order = createOrder(user, orderItem, ORDER_TYPE.SELL);
      orderItem.setOrder(order);

      if (assetToSell.getQuantity() >= quantity) {
        order.setStatus(ORDER_STATUS.SUCCESS);
        order.setOrder_TYPE(ORDER_TYPE.SELL);
        Order savedOrder = orderRepository.save(order);
        walletService.payOrderPayment(order, user);
        Asset updatedAsset = assetService.updateAsset(assetToSell.getId(), -quantity);

        if (updatedAsset.getQuantity() * coin.getCurrentPrice() <= 1) {
          assetService.deleteAsset(updatedAsset.getId());
        }

        return savedOrder;
      }
      throw new Exception("Insufficient Quantity To Sell!");
    }
    throw new Exception("Asset Not Found!!");

  }

  @Override
  @Transactional
  public Order processOrder(Coin coin, double quantity, ORDER_TYPE order_TYPE, User user) throws Exception {

    if (order_TYPE.equals(ORDER_TYPE.BUY)) {
      return buyAsset(coin, quantity, user);
    } else if (order_TYPE.equals(ORDER_TYPE.SELL)) {
      return sellAsset(coin, quantity, user);
    }

    throw new Exception("Invalid Order Type");
  }

}
