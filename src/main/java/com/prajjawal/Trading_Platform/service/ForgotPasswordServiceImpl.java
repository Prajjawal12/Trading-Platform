package com.prajjawal.Trading_Platform.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prajjawal.Trading_Platform.domain.VERIFICATION_TYPE;
import com.prajjawal.Trading_Platform.model.ForgotPasswordToken;
import com.prajjawal.Trading_Platform.model.User;
import com.prajjawal.Trading_Platform.repository.ForgotPasswordRepository;

@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService {
  @Autowired
  private ForgotPasswordRepository forgotPasswordRepository;

  @Override
  public ForgotPasswordToken createToken(User user, String id, String otp, VERIFICATION_TYPE verification_TYPE,
      String sendTo) {
    ForgotPasswordToken token = new ForgotPasswordToken();
    token.setUser(user);
    token.setSendTo(sendTo);
    token.setVerification_TYPE(verification_TYPE);
    token.setOtp(otp);
    token.setId(id);
    return forgotPasswordRepository.save(token);
  }

  @Override
  public ForgotPasswordToken findById(String id) {
    Optional<ForgotPasswordToken> token = forgotPasswordRepository.findById(id);
    return token.orElse(null);
  }

  @Override
  public ForgotPasswordToken findByUser(Long userId) {
    return forgotPasswordRepository.findByUserId(userId);
  }

  @Override
  public void deleteToken(ForgotPasswordToken token) {
    forgotPasswordRepository.delete(token);
  }
}
