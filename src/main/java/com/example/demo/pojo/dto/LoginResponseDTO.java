package com.example.demo.pojo.dto;

import com.example.demo.pojo.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录响应DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    private String token; // JWT token
    private UserEntity user; // 用户信息
}

