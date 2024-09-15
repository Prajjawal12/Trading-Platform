package com.prajjawal.Trading_Platform.model;

import com.prajjawal.Trading_Platform.domain.ORDER_STATUS;
import com.prajjawal.Trading_Platform.domain.ORDER_TYPE;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "Orders")
public class Orders {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne
  private User user;
  @Column(nullable = false)
  private ORDER_TYPE order_TYPE;

  @Column(nullable = false)
  private BigDecimal price;

  private LocalDateTime timeStamp = LocalDateTime.now();

  @Column(nullable = false)
  private ORDER_STATUS status;
  @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
  private OrderItem orderItem;
}
