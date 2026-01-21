package com.example.demo.pojo.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class UserDTO {

    private Long userId; // 用户ID（主键，插入后自动生成）

    private String phone; // 必传

    private String password; // 必传

    private String nickname; // 必传

    private String avatar; // 可选（默认头像）

    private Integer gender; // 可选，0/1/2

    private String studentId; // 可选

    private String school; // 可选

    private String dormitory; // 可选

    private BigDecimal balance; // 可选，默认 0

    private Integer creditScore; // 可选，默认 100

    private Integer userType; // 1-普通，2-跑腿员，3-商家

    private Integer status; // 0-禁用，1-正常

    private LocalDateTime createdAt; // 创建时间

    private LocalDateTime updatedAt; // 更新时间

}
