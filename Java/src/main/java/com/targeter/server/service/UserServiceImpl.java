package com.targeter.server.service;

import com.targeter.server.dto.Data;
import com.targeter.server.dto.SignupRequest;
import com.targeter.server.dto.UserDto;
import com.targeter.server.dto.LoginRequest;
import com.targeter.server.entity.User;
import com.targeter.server.repository.UserRepository;
import com.targeter.server.security.JwtUtils;
import com.targeter.server.security.UserDetailsImpl;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final AuthenticationManager authenticationManager;

  private final UserRepository userRepository;

  private final PasswordEncoder encoder;

  private final JwtUtils jwtUtils;

  @Override
  public Data<UserDto> signIn(LoginRequest loginRequest) {
    if ("vk".equalsIgnoreCase(loginRequest.getMethod())) {
      TransportClient transportClient = HttpTransportClient.getInstance();
      VkApiClient vk = new VkApiClient(transportClient);
      try {
        UserAuthResponse authResponse = vk.oauth()
            .userAuthorizationCodeFlow(7662595, "DvEaFLaiXWTYLPJYUTNa", "http://localhost:8081/login", loginRequest.getCode())
            .execute();
        if (!userRepository.existsByUsername(loginRequest.getUsername())) {
          User user = new User();
          user.setPassword(encoder.encode(authResponse.getUserId().toString()));
          user.setUsername(authResponse.getUserId().toString());
          userRepository.save(user);
        }
        return auth(authResponse.getUserId().toString(), authResponse.getUserId().toString());
      } catch (ClientException | ApiException e) {
        return Data.error(Collections.singletonList("Incorrect access token"));
      }
    } else if (!userRepository.existsByUsername(loginRequest.getUsername())) {
      return Data.error(Collections.singletonList("User with this username/password not exists!"));
    }
    return auth(loginRequest.getUsername(), loginRequest.getPassword());
  }

  @Override
  public Data<UserDto> signUp(SignupRequest signupRequest) {
    if (userRepository.existsByUsername(signupRequest.getUsername())) {
      return Data.error(Collections.singletonList("User with this username already exists!"));
    }
    createUser(signupRequest.getUsername(), signupRequest.getPassword());
    return auth(signupRequest.getUsername(), signupRequest.getPassword());
  }

  private void createUser(String username, String password) {
    User user = new User();
    user.setPassword(encoder.encode(password));
    user.setUsername(username);
    userRepository.save(user);
  }

  private Data<UserDto> auth(String username, String password) {

    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    List<String> roles = userDetails.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());
    return Data.ok(new UserDto(userDetails.getId(), userDetails.getUsername(), userDetails.getPassword(), roles, jwt));
  }
}
