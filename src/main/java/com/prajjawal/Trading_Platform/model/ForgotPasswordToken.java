package com.prajjawal.Trading_Platform.model;

import com.prajjawal.Trading_Platform.domain.VERIFICATION_TYPE;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class ForgotPasswordToken {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private String id;

  @OneToOne
  private User user;

  private String otp;

  private VERIFICATION_TYPE verification_TYPE;

  private String sendTo;
}
