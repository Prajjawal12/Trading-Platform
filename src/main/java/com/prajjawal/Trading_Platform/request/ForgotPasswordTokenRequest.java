package com.prajjawal.Trading_Platform.request;

import com.prajjawal.Trading_Platform.domain.VERIFICATION_TYPE;

import lombok.Data;

@Data
public class ForgotPasswordTokenRequest {
  private String sendTo;
  private VERIFICATION_TYPE verification_TYPE;
}
