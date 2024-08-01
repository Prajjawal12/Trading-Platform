package com.prajjawal.Trading_Platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prajjawal.Trading_Platform.model.TwoFactorOTP;

public interface TwoFactorOtpRepository extends JpaRepository<TwoFactorOTP, String> {

  TwoFactorOTP findByUserId(Long userId);
}
