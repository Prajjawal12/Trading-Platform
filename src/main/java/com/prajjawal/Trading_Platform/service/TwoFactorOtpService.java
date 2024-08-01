package com.prajjawal.Trading_Platform.service;

import com.prajjawal.Trading_Platform.model.TwoFactorOTP;
import com.prajjawal.Trading_Platform.model.User;

public interface TwoFactorOtpService {
  TwoFactorOTP createTwoFactorOtp(User user, String otp, String jwt);

  TwoFactorOTP findByUser(Long userId);

  TwoFactorOTP findById(String id);

  boolean verifyTwoFactorOtp(TwoFactorOTP twoFactorOTP, String otp);

  void deleteTwoFactorOtp(TwoFactorOTP twoFactorOTP);
}
