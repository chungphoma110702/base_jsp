package com.example.base_jsp.models.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @Schema(description = "Họ tên")
    private String fullName;

    @Schema(description = "Email")
    private String email;

    @Schema(description = "Địa chỉ")
    private String address;

    @Schema(description = "Số điện thoại")
    private String phoneNumber;

    @Schema(description = "Ngày sinh")
    private LocalDateTime dateOfBirth;

    @Schema(description = "Tên đăng nhâp")
    private String username;

    @Schema(description = "Mật khẩu")
    private String password;
}
