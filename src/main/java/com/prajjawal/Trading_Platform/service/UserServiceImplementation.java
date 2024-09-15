package com.prajjawal.Trading_Platform.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.prajjawal.Trading_Platform.config.JwtProvider;
import com.prajjawal.Trading_Platform.domain.VERIFICATION_TYPE;
import com.prajjawal.Trading_Platform.model.TwoFactorAuth;
import com.prajjawal.Trading_Platform.model.User;
import com.prajjawal.Trading_Platform.repository.UserRepository;

@RestController
public class UserServiceImplementation implements UserService {
  @Autowired
  private UserRepository userRepository;

  @Override
  public User findUserProfileByJwt(String jwt) throws Exception {
    String email = JwtProvider.getEmailFromToken(jwt);

    User user = userRepository.findByEmail(email);

    if (user == null) {
      throw new Exception("User Not Found!!");
    }
    return user;
  }

  @Override
  public User findUserByEmail(String email) throws Exception {
    User user = userRepository.findByEmail(email);

    if (user == null) {
      throw new Exception("User Not Found!!!");
    }
    return user;
  }

  @Override
  public User findUserById(Long userId) throws Exception {
    Optional<User> user = userRepository.findById(userId);
    if (user.isEmpty()) {
      throw new Exception("User Not Found!!");
    }
    return user.get();
  }

  @Override
  public User updatePassword(User user, String newPassword) {
    user.setPassword(newPassword);
    return userRepository.save(user);
  }

  @Override
  public User enableTwoFactorAuthentication(VERIFICATION_TYPE verification_TYPE, String sendTo, User user)

  {
    TwoFactorAuth twoFactorAuth = new TwoFactorAuth();
    twoFactorAuth.setEnabled(true);
    twoFactorAuth.setSendTo(verification_TYPE);
    user.setTwoFactorAuth(twoFactorAuth);

    return userRepository.save(user);

  }

}