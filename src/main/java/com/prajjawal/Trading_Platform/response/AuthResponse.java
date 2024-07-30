package com.prajjawal.Trading_Platform.response;

import lombok.Data;

@Data
public class AuthResponse {
  private String jwt;
  private boolean status;
  private String message;
  private boolean isTwoFactorAuthEnabled;
  private String session;
}
