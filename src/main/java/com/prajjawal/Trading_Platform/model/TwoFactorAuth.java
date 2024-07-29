package com.prajjawal.Trading_Platform.model;

import com.prajjawal.Trading_Platform.domain.VERIFICATION_TYPE;

import lombok.Data;

//Generates getter and setter fields
@Data
public class TwoFactorAuth {
  private boolean isEnabled = false;
  private VERIFICATION_TYPE sendTo;
}
