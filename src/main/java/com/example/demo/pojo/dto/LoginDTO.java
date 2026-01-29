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

    @Schema(description = "用户类型: 0-管理员, 1-普通, 2-跑腿员, 3-商家")
    private Integer userType;
}

