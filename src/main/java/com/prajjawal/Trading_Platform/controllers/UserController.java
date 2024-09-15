package com.prajjawal.Trading_Platform.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prajjawal.Trading_Platform.domain.VERIFICATION_TYPE;
import com.prajjawal.Trading_Platform.model.ForgotPasswordToken;
import com.prajjawal.Trading_Platform.model.User;
import com.prajjawal.Trading_Platform.model.VerificationCode;
import com.prajjawal.Trading_Platform.request.ForgotPasswordTokenRequest;
import com.prajjawal.Trading_Platform.request.ResetPasswordRequest;
import com.prajjawal.Trading_Platform.response.ApiResponse;
import com.prajjawal.Trading_Platform.response.AuthResponse;
import com.prajjawal.Trading_Platform.service.EmailService;
import com.prajjawal.Trading_Platform.service.ForgotPasswordService;
import com.prajjawal.Trading_Platform.service.UserService;
import com.prajjawal.Trading_Platform.service.VerificationCodeService;
import com.prajjawal.Trading_Platform.utils.otpUtils;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class UserController {
  @Autowired
  private UserService userService;

  @Autowired
  private EmailService emailService;

  @Autowired
  private VerificationCodeService verificationCodeService;

  @Autowired
  private ForgotPasswordService forgotPasswordService;

  @GetMapping("/api/users/{id}")
  public ResponseEntity<User> getUserProfileById(@PathVariable("id") Long id) throws Exception {
    try {
      User user = userService.findUserById(id);
      if (user != null) {
        return new ResponseEntity<>(user, HttpStatus.OK);
      } else {
        throw new Exception("User not found!");
      }
    } catch (Exception e) {
      throw new Exception("Error retrieving user: " + e.getMessage());
    }
  }

  @GetMapping("/api/users/profile")
  public ResponseEntity<User> getUserProfile(
      @RequestHeader("Authorization") String jwt) throws Exception {
    User user = userService.findUserProfileByJwt(jwt);

    return new ResponseEntity<>(user, HttpStatus.OK);
  }

  @PostMapping("/api/users/verification/{verificationType}/send-otp")
  public ResponseEntity<String> sendVerificationOtp(
      @RequestHeader("Authorization") String jwt,
      @PathVariable VERIFICATION_TYPE verification_TYPE

  ) throws Exception {

    User user = userService.findUserProfileByJwt(jwt);

    VerificationCode verificationCode = verificationCodeService.getVerififcationCodeByUser(user.getId());

    if (verificationCode == null) {
      verificationCode = verificationCodeService.sendVerififcationCode(verificationCode, user, verification_TYPE);
    }

    if (verification_TYPE.equals(VERIFICATION_TYPE.EMAIL)) {
      emailService.sendVerificationOtpEmail(user.getEmail(), verificationCode.getOtp());
    }

    return new ResponseEntity<>("Verification OTP sent succesfully!", HttpStatus.OK);
  }

  @PatchMapping("/api/users/enable-two-factor/verify-otp/{otp}")
  public ResponseEntity<User> enableTwoFactorAuthentication(@PathVariable String otp,
      @RequestHeader("Authorization") String jwt)
      throws Exception {
    User user = userService.findUserProfileByJwt(jwt);

    VerificationCode verificationCode = verificationCodeService.getVerififcationCodeByUser(user.getId());

    String sendto = verificationCode.getVerification_TYPE().equals(VERIFICATION_TYPE.EMAIL)
        ? verificationCode.getEmail()
        : verificationCode.getMobile();

    boolean isVerified = verificationCode.getOtp().equals(otp);

    if (isVerified) {
      User updatedUser = userService.enableTwoFactorAuthentication(verificationCode.getVerification_TYPE(), sendto,
          user);
      verificationCodeService.deleteVerificationCodeById(verificationCode);
      return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
    throw new Exception("OTPs don't match!");

  }

  @PostMapping("/auth/users/reset-password/send-otp")
  public ResponseEntity<AuthResponse> sendForgotPasswordOtp(

      @RequestBody ForgotPasswordTokenRequest req) throws Exception {

    User user = userService.findUserByEmail(req.getSendTo());

    String otp = otpUtils.generateOtp();

    UUID uuid = UUID.randomUUID();

    String id = uuid.toString();

    ForgotPasswordToken token = forgotPasswordService.findByUser(user.getId());

    if (token == null) {
      token = forgotPasswordService.createToken(user, id, otp, req.getVerification_TYPE(), req.getSendTo());
    }

    if (req.getVerification_TYPE().equals(VERIFICATION_TYPE.EMAIL)) {
      emailService.sendVerificationOtpEmail(user.getEmail(), token.getOtp());
    }
    AuthResponse response = new AuthResponse();

    response.setSession(token.getId());
    response.setMessage("Password Reset OTP was sent succesfully");

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PatchMapping("/auth/users/reset-password/verify-otp")
  public ResponseEntity<ApiResponse> resetPassword(
      @RequestParam String id,
      @RequestBody ResetPasswordRequest req,
      @RequestHeader("Authorization") String jwt) throws Exception {
    ForgotPasswordToken forgotPasswordToken = forgotPasswordService.findById(id);
    boolean isVerified = forgotPasswordToken.getOtp().equals(req.getOtp());

    if (isVerified) {
      userService.updatePassword(forgotPasswordToken.getUser(), req.getPassword());
      ApiResponse res = new ApiResponse();
      res.setMessage("Password Updated Succesfully!");
      return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }
    throw new Exception("Wrong OTP");
  }

}
