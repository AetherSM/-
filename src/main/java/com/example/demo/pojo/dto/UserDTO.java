package com.example.demo.pojo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {

    private Long userId; // 用户ID（主键，插入后自动生成）

    private String phone; // 必传

    private String password; // 必传

    private String nickname; // 必传

    private String avatar; // 可选（默认头像）

    private String gender; // 可选

    private String studentId; // 可选

    private String school; // 可选

    private String email; // 可选

    private LocalDateTime createTime; // 创建时间

    private LocalDateTime updateTime; // 更新时间

}
