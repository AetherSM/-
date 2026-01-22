package com.example.demo.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 登录DTO
 */
@Data
@Schema(description = "用户登录信息")
public class LoginDTO {
    @Schema(description = "手机号")
    private String phone; // 手机号
    @Schema(description = "密码")
    private String password; // 密码
}

