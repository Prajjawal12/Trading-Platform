package com.prajjawal.Trading_Platform.service;

import com.prajjawal.Trading_Platform.domain.VERIFICATION_TYPE;
import com.prajjawal.Trading_Platform.model.ForgotPasswordToken;
import com.prajjawal.Trading_Platform.model.User;

public interface ForgotPasswordService {
  ForgotPasswordToken createToken(User user, String id, String otp, VERIFICATION_TYPE verification_TYPE, String sendTo);

  ForgotPasswordToken findById(String id);

  ForgotPasswordToken findByUser(Long userId);

  void deleteToken(ForgotPasswordToken token);
}
