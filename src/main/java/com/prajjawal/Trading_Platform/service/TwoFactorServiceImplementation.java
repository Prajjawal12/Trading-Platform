package com.prajjawal.Trading_Platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prajjawal.Trading_Platform.model.TwoFactorOTP;
import com.prajjawal.Trading_Platform.model.User;
import com.prajjawal.Trading_Platform.repository.TwoFactorOtpRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class TwoFactorServiceImplementation implements TwoFactorOtpService {
  @Autowired
  private TwoFactorOtpRepository twoFactorOTPRepository;

  @Override
  public TwoFactorOTP createTwoFactorOtp(User user, String otp, String jwt) {
    UUID uuid = UUID.randomUUID();
    String id = uuid.toString();
    TwoFactorOTP twoFactorOTP = new TwoFactorOTP();
    twoFactorOTP.setOtp(otp);
    twoFactorOTP.setJwt(jwt);
    twoFactorOTP.setId(id);
    twoFactorOTP.setUser(user);
    return twoFactorOTPRepository.save(twoFactorOTP);
  }

  @Override
  public TwoFactorOTP findByUser(Long userId) {
    return twoFactorOTPRepository.findByUserId(userId);
  }

  @Override
  public TwoFactorOTP findById(String id) {
    Optional<TwoFactorOTP> otp = twoFactorOTPRepository.findById(id);
    return otp.orElse(null);
  }

  @Override
  public boolean verifyTwoFactorOtp(TwoFactorOTP twoFactorOTP, String otp) {
    return twoFactorOTP.getOtp().equals(otp);

  }

  @Override
  public void deleteTwoFactorOtp(TwoFactorOTP twoFactorOTP) {
    twoFactorOTPRepository.delete(twoFactorOTP);
  }
}
