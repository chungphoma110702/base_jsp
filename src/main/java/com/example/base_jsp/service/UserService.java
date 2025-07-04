package com.example.base_jsp.service;


import com.example.base_jsp.aop.exceptions.ApiException;
import com.example.base_jsp.aop.exceptions.ERROR;
import com.example.base_jsp.entities.entity.UserEntity;
import com.example.base_jsp.models.dto.UserDto;
import com.example.base_jsp.repositories.UserRepository;
import com.example.base_jsp.utils.DataUtils;
import com.example.base_jsp.utils.StringUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public String createUser(UserDto request) {

        log.info("Start - create user with : {}", request);
        // validate request
        this.validateUserDto(request);
        // check trung
        Optional<UserEntity> userEntity = userRepository.findByUsername(request.getUsername());
        if (userEntity.isPresent()) {
            log.error("Exception : user existed!");
            throw new ApiException(ERROR.RESOURCE_NOT_FOUND, "Tài khoản đã tồn tại!");
        }
        // set db
        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setAddress(request.getAddress());
        user.setEmail(request.getEmail());
        user.setDateOfBirth(request.getDateOfBirth());

        log.info("Done - create user with : {}", request);
        userRepository.save(user);
        return "Tạo tài khoản thành công !";
    }

//    @PostAuthorize("returnObject.username == authentication.name")
    public UserDto getUser(int id) throws ApiException {
        log.info("Start - get user with id : {}", id);
        UserEntity userEntity = userRepository.findById(id).orElseThrow(()-> new ApiException(ERROR.RESOURCE_NOT_FOUND, "Not found"));
        UserDto userDto = new UserDto();
        userDto.setFullName(userEntity.getFullName());
        userDto.setPhoneNumber(userEntity.getPhoneNumber());
        userDto.setAddress(userEntity.getAddress());
        userDto.setEmail(userEntity.getEmail());
        userDto.setDateOfBirth(userEntity.getDateOfBirth());
        userDto.setUsername(userEntity.getUsername());
        return userDto;
    }

    private void validateUserDto(UserDto request) throws ApiException {
        if (StringUtil.isNullOrEmpty(request.getFullName()) || request.getFullName().length() > 255) {
            throw new ApiException(ERROR.BAD_REQUEST, "Họ và tên không hợp lệ");
        }
        if (StringUtil.isNullOrEmpty(request.getPhoneNumber()) || !DataUtils.isPhoneNumber(request.getPhoneNumber())) {
            throw new ApiException(ERROR.BAD_REQUEST, "Số điện thoại không hợp lệ");
        }
        if (StringUtil.isNullOrEmpty(String.valueOf(request.getDateOfBirth()))) {
            throw new ApiException(ERROR.BAD_REQUEST, "Ngày sinh không hợp lệ");
        }
        if (StringUtil.isNullOrEmpty(request.getAddress()) || request.getAddress().length() > 255) {
            throw new ApiException(ERROR.BAD_REQUEST, "Địa chỉ thường trú không hợp lệ");
        }
        if (StringUtil.isNullOrEmpty(request.getEmail()) || (!DataUtils.isEmail(request.getEmail()))) {
            throw new ApiException(ERROR.BAD_REQUEST, "Email không hợp lệ");
        }
        if (StringUtil.isNullOrEmpty(request.getUsername()) || request.getUsername().length() > 255) {
            throw new ApiException(ERROR.BAD_REQUEST, "Tên đăng nhập không hợp lệ");
        }
        if (!DataUtils.isSecurePassword(request.getPassword())) {
            throw new ApiException(ERROR.BAD_REQUEST, "Mật khẩu không hợp lệ ");
        }
    }
}
