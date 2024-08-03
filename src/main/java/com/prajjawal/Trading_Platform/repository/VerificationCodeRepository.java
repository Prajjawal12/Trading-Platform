package com.prajjawal.Trading_Platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prajjawal.Trading_Platform.model.VerificationCode;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
  VerificationCode findByUserId(Long userId);
}
