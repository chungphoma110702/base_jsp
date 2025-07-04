package com.example.base_jsp.service;


import com.example.base_jsp.aop.exceptions.ApiException;
import com.example.base_jsp.aop.exceptions.ERROR;
import com.example.base_jsp.config.securities.JwtUtils;
import com.example.base_jsp.entities.entity.UserEntity;
import com.example.base_jsp.models.request.LoginRequest;
import com.example.base_jsp.models.response.AuthenticationResponse;
import com.example.base_jsp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthenticationResponse login(LoginRequest loginRequest) throws ApiException {

        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
        } catch (BadCredentialsException ex) {
            throw new ApiException(ERROR.UNAUTHORIZED, "Mật khẩu không đúng!");
        } catch (UsernameNotFoundException ex) {
            throw new ApiException(ERROR.RESOURCE_NOT_FOUND, "Tài khoản không tồn tại!");
        } catch (Exception ex) {
            throw new ApiException(ERROR.UNAUTHORIZED, "Đăng nhập thất bại: " + ex.getMessage());
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

//        UserEntity entity = userRepository.findByUsername(loginRequest.getUsername())
//                .orElseThrow(() -> new ApiException(ERROR.RESOURCE_NOT_FOUND, "Tài khoản không tồn tại!"));

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("authorities", userDetails.getAuthorities());

        String jwt = jwtUtils.generateToken(extraClaims, userDetails);
        return new AuthenticationResponse(jwt);
    }
}
