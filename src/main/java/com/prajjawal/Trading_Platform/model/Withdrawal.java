package com.prajjawal.Trading_Platform.model;

import com.prajjawal.Trading_Platform.domain.WITHDRAWAL_STATUS;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Withdrawal {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private WITHDRAWAL_STATUS withdrawal_STATUS;

  private Long amount;

  @ManyToOne
  private User user;

  private LocalDateTime date = LocalDateTime.now();

}
