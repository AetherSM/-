package com.example.demo.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    private Long userId; // user_id
    private String phone;
    private String password;
    private String nickname;
    private String avatar;
    private Integer gender; // 0-未知，1-男，2-女
    private String studentId;
    private String school;
    private String dormitory;
    private BigDecimal balance;
    private Integer creditScore;
    private Integer userType; // 1-普通，2-跑腿员，3-商家
    private Integer status; // 0-禁用，1-正常
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}
