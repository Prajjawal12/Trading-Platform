package com.prajjawal.Trading_Platform.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class OrderItem {
  private Long id;

  private double quantity;

  @ManyToOne
  private Coin coin;

  private double buyPrice;

  private double sellPrice;

  @JsonIgnore
  @OneToOne
  private Order order;
}
