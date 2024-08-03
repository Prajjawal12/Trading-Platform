package com.prajjawal.Trading_Platform.service;

import com.prajjawal.Trading_Platform.domain.VERIFICATION_TYPE;
import com.prajjawal.Trading_Platform.model.User;

public interface UserService {
  public User findUserProfileByJwt(String jwt) throws Exception;

  public User findUserByEmail(String email) throws Exception;

  public User findUserById(Long userId) throws Exception;

  public User enableTwoFactorAuthentication(VERIFICATION_TYPE verification_TYPE, String sendTo, User user)
      throws Exception;

  User updatePassword(User user, String newPassword);
}
