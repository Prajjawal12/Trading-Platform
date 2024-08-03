package com.prajjawal.Trading_Platform.request;

import lombok.Data;

@Data
public class ResetPasswordRequest {
  private String otp;
  private String password;

}
