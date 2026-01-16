package com.example.demo.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailVO {

    private Long userId; // 用户ID
    private String phone; // 手机号
    private String nickname; // 昵称
    private String avatar; // 头像URL
    private String email; // 邮箱
    private Integer gender; // 性别（0-未知，1-男，2-女）
    private String genderDesc; // 性别描述（如"男"）
    private String studentId; // 学号
    private String school; // 学校
    private LocalDateTime createTime; // 创建时间（格式化后返回）
}