package com.example.demo.service.impl;

import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.dto.LoginDTO;
import com.example.demo.pojo.dto.LoginResponseDTO;
import com.example.demo.pojo.dto.UserDTO;
import com.example.demo.pojo.entity.UserEntity;
import com.example.demo.service.UserService;
import com.example.demo.util.JwtUtil;
import com.example.demo.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserEntity register(UserDTO userDTO) {
        // 1. 参数验证
        if (!StringUtils.hasText(userDTO.getPhone())) {
            throw new IllegalArgumentException("手机号不能为空");
        }
        if (!StringUtils.hasText(userDTO.getPassword())) {
            throw new IllegalArgumentException("密码不能为空");
        }
        if (!StringUtils.hasText(userDTO.getNickname())) {
            throw new IllegalArgumentException("昵称不能为空");
        }
        
        // 验证手机号格式（简单验证，11位数字）
        if (!userDTO.getPhone().matches("^1[3-9]\\d{9}$")) {
            throw new IllegalArgumentException("手机号格式不正确");
        }
        
        // 验证密码长度
        if (userDTO.getPassword().length() < 6 || userDTO.getPassword().length() > 20) {
            throw new IllegalArgumentException("密码长度必须在6-20位之间");
        }

        // 2. 检查手机号是否已存在
        UserEntity existingUser = userMapper.findByPhone(userDTO.getPhone());
        if (existingUser != null) {
            throw new IllegalArgumentException("该手机号已被注册");
        }

        // 3. 密码加密（MD5）
        String encodedPassword = MD5Util.encode(userDTO.getPassword());
        userDTO.setPassword(encodedPassword);

        // 4. 设置默认值
        if (!StringUtils.hasText(userDTO.getAvatar())) {
            userDTO.setAvatar("https://via.placeholder.com/150"); // 默认头像
        }
        if (!StringUtils.hasText(userDTO.getGender())) {
            userDTO.setGender("0"); // 默认未知（0-未知，1-男，2-女）
        }
        
        // 验证gender值是否有效
        String gender = userDTO.getGender();
        if (!gender.matches("^[0-2]$")) {
            throw new IllegalArgumentException("性别参数不正确（0-未知，1-男，2-女）");
        }

        // 5. 设置创建时间和更新时间
        LocalDateTime now = LocalDateTime.now();
        userDTO.setCreateTime(now);
        userDTO.setUpdateTime(now);

        // 6. 插入数据库
        userMapper.register(userDTO);
        
        // 7. 查询刚注册的用户信息（不包含密码）
        UserEntity newUser = userMapper.findByPhone(userDTO.getPhone());
        if (newUser != null) {
            newUser.setPassword(null); // 不返回密码
        }
        return newUser;
    }

    @Override
    public LoginResponseDTO login(LoginDTO loginDTO) {
        // 1. 参数验证
        if (!StringUtils.hasText(loginDTO.getPhone())) {
            throw new IllegalArgumentException("手机号不能为空");
        }
        if (!StringUtils.hasText(loginDTO.getPassword())) {
            throw new IllegalArgumentException("密码不能为空");
        }

        // 2. 根据手机号查询用户
        UserEntity user = userMapper.findByPhone(loginDTO.getPhone());
        if (user == null) {
            throw new IllegalArgumentException("用户不存在，请先注册");
        }

        // 3. 验证密码（MD5加密后比较）
        String encodedPassword = MD5Util.encode(loginDTO.getPassword());
        if (user.getPassword() == null || !encodedPassword.equals(user.getPassword())) {
            throw new IllegalArgumentException("手机号或密码错误");
        }

        // 4. 生成JWT token
        String token;
        try {
            token = jwtUtil.generateToken(user.getUserId(), user.getPhone());
        } catch (Exception e) {
            throw new RuntimeException("生成token失败：" + e.getMessage(), e);
        }

        // 5. 创建用户信息副本（避免修改原对象）
        UserEntity userInfo = new UserEntity();
        userInfo.setUserId(user.getUserId());
        userInfo.setPhone(user.getPhone());
        userInfo.setNickname(user.getNickname());
        userInfo.setAvatar(user.getAvatar());
        userInfo.setGender(user.getGender());
        userInfo.setStudentId(user.getStudentId());
        userInfo.setSchool(user.getSchool());
        userInfo.setCreateTime(user.getCreateTime());
        userInfo.setUpdateTime(user.getUpdateTime());
        // 不设置密码

        // 6. 返回登录响应
        return new LoginResponseDTO(token, userInfo);
    }

    @Override
    public UserEntity findByPhone(String phone) {
        return userMapper.findByPhone(phone);
    }

    @Override
    public UserEntity findById(Long userId) {
        return userMapper.findById(userId);
    }

    @Override
    public List<UserEntity> findAllUsers() {
        return userMapper.findAllUsers();
    }
}
