package com.example.demo.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    private Long userId; // 对应 user_id（主键）
    private String phone; // 对应 phone
    private String password; // 对应 password
    private String nickname; // 对应 nickname
    private String avatar; // 对应 avatar
    private String email; // 对应 email
    private Integer gender; // 对应 gender（0-未知，1-男，2-女）
    private String studentId; // 对应 student_id
    private String school; // 对应 school
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime; // 对应 create_time
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime; // 对应 update_time
}
