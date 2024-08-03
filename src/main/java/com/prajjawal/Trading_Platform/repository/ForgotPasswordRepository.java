package com.prajjawal.Trading_Platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prajjawal.Trading_Platform.model.ForgotPasswordToken;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordToken, String> {
  ForgotPasswordToken findByUserId(Long UserId);
}
