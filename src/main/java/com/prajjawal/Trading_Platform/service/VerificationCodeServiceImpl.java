package com.prajjawal.Trading_Platform.service;

import java.util.Optional;

import org.hibernate.internal.ExceptionConverterImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prajjawal.Trading_Platform.domain.VERIFICATION_TYPE;
import com.prajjawal.Trading_Platform.model.User;
import com.prajjawal.Trading_Platform.model.VerificationCode;
import com.prajjawal.Trading_Platform.repository.VerificationCodeRepository;
import com.prajjawal.Trading_Platform.utils.otpUtils;

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {
  @Autowired
  private VerificationCodeRepository verificationCodeRepository;

  @Override
  public VerificationCode sendVerififcationCode(VerificationCode verififcationCode, User user,
      VERIFICATION_TYPE verification_TYPE) {
    VerificationCode verificationCode1 = new VerificationCode();

    verificationCode1.setOtp(otpUtils.generateOtp());
    verificationCode1.setVerification_TYPE(verification_TYPE);
    verificationCode1.setUser(user);
    return verificationCodeRepository.save(verificationCode1);

  }

  @Override
  public VerificationCode getVerififcationCodeById(Long Id) throws Exception {
    Optional<VerificationCode> verificationCode = verificationCodeRepository.findById(Id);

    if (verificationCode.isPresent()) {
      return verificationCode.get();
    }
    throw new Exception("Verification Code Not Found!");
  }

  @Override
  public VerificationCode getVerififcationCodeByUser(Long userId) {
    return verificationCodeRepository.findByUserId(userId);
  }

  @Override
  public void deleteVerificationCodeById(VerificationCode verififcationCode) {
    verificationCodeRepository.delete(verififcationCode);
  }

}
