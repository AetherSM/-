package com.example.demo.pojo.dto;

import com.example.demo.pojo.entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录响应DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "登录响应数据")
public class LoginResponseDTO {
    @Schema(description = "JWT Token")
    private String token; // JWT token
    @Schema(description = "用户信息")
    private UserEntity user; // 用户信息
}

