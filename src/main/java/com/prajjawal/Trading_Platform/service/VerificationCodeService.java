package com.prajjawal.Trading_Platform.service;

import com.prajjawal.Trading_Platform.domain.VERIFICATION_TYPE;
import com.prajjawal.Trading_Platform.model.User;
import com.prajjawal.Trading_Platform.model.VerificationCode;

public interface VerificationCodeService {
  VerificationCode sendVerififcationCode(VerificationCode verififcationCode, User user,
      VERIFICATION_TYPE verification_TYPE);

  VerificationCode getVerififcationCodeById(Long Id) throws Exception;

  VerificationCode getVerififcationCodeByUser(Long userid);

  void deleteVerificationCodeById(VerificationCode verififcationCode);
}
