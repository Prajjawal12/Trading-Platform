package com.prajjawal.Trading_Platform.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prajjawal.Trading_Platform.config.JwtProvider;
import com.prajjawal.Trading_Platform.model.TwoFactorOTP;
import com.prajjawal.Trading_Platform.model.User;
import com.prajjawal.Trading_Platform.repository.UserRepository;
import com.prajjawal.Trading_Platform.response.AuthResponse;
import com.prajjawal.Trading_Platform.service.CustomUserDetailsService;
import com.prajjawal.Trading_Platform.service.EmailService;
import com.prajjawal.Trading_Platform.service.TwoFactorOtpService;
import com.prajjawal.Trading_Platform.utils.otpUtils;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TwoFactorOtpService twoFactorOtpService;
  @Autowired
  private CustomUserDetailsService customUserDetailsService;

  @Autowired
  private EmailService emailService;

  @PostMapping("/signup")
  public ResponseEntity<AuthResponse> register(@RequestBody User user) throws Exception {
    User isEmailExists = userRepository.findByEmail(user.getEmail());
    if (isEmailExists != null) {
      throw new Exception("Email is already associated with another account");
    }

    User newUser = new User();
    newUser.setEmail(user.getEmail());
    newUser.setPassword(user.getPassword());
    newUser.setFullName(user.getFullName());

    User savedUser = userRepository.save(newUser);

    Authentication auth = new UsernamePasswordAuthenticationToken(newUser.getEmail(), newUser.getPassword());

    SecurityContextHolder.getContext().setAuthentication(auth);
    String jwt = JwtProvider.generateToken(auth);
    AuthResponse res = new AuthResponse();
    res.setJwt(jwt);
    res.setStatus(true);
    res.setMessage("Registration was Successful");
    return new ResponseEntity<>(res, HttpStatus.CREATED);
  }

  @PostMapping("/signin")
  public ResponseEntity<AuthResponse> login(@RequestBody User user) throws Exception {
    User isEmailExist = userRepository.findByEmail(user.getEmail());

    if (isEmailExist == null) {
      throw new Exception("Email is not associated with any account");
    }

    String username = user.getEmail();
    String password = user.getPassword();

    Authentication auth = authenticate(username, password);

    SecurityContextHolder.getContext().setAuthentication(auth);
    String jwt = JwtProvider.generateToken(auth);
    User authUser = userRepository.findByEmail(username);
    if (user.getTwoFactorAuth().isEnabled()) {
      AuthResponse res = new AuthResponse();
      res.setMessage("Two Factor Authentication is enabled!!");
      res.setTwoFactorAuthEnabled(true);
      String otp = otpUtils.generateOtp();

      TwoFactorOTP oldTwoFactorOTP = twoFactorOtpService.findByUser(authUser.getId());
      if (oldTwoFactorOTP != null) {
        twoFactorOtpService.deleteTwoFactorOtp(oldTwoFactorOTP);
      }

      TwoFactorOTP newTwoFactorOTP = twoFactorOtpService.createTwoFactorOtp(authUser, otp, jwt);
      emailService.sendVerificationOtpEmail(username, otp);
      res.setSession(newTwoFactorOTP.getId());

      return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }
    AuthResponse res = new AuthResponse();
    res.setJwt(jwt);
    res.setStatus(true);
    res.setMessage("Login was Successful");
    return new ResponseEntity<>(res, HttpStatus.OK);
  }

  private Authentication authenticate(String username, String password) {
    UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

    if (userDetails == null) {
      throw new BadCredentialsException("Invalid Username");
    }

    if (!password.equals(userDetails.getPassword())) {
      throw new BadCredentialsException("Invalid Password");
    }

    return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
  }

  public ResponseEntity<AuthResponse> verifySignInOtp(
      @PathVariable String otp,
      @RequestParam String id) throws Exception {
    TwoFactorOTP twoFactorOTP = twoFactorOtpService.findById(id);
    if (twoFactorOtpService.verifyTwoFactorOtp(twoFactorOTP, otp)) {
      AuthResponse res = new AuthResponse();
      res.setMessage("Two factor authentication verified!");
      res.setTwoFactorAuthEnabled(true);
      res.setJwt(twoFactorOTP.getJwt());
      return new ResponseEntity<>(res, HttpStatus.OK);
    }

    throw new Exception("Invalid OTP");
  }
}
