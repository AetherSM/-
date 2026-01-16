package com.example.demo.controller;

import com.example.demo.pojo.dto.LoginDTO;
import com.example.demo.pojo.dto.LoginResponseDTO;
import com.example.demo.pojo.dto.UserDTO;
import com.example.demo.pojo.entity.UserEntity;
import com.example.demo.pojo.result.Result;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    /*注册用户接口*/
    @PostMapping("/register")
    public Result<UserEntity> register(@RequestBody UserDTO userDTO) {
        try {
            UserEntity user = userService.register(userDTO);
            return Result.success(user);
        } catch (IllegalArgumentException e) {
            return Result.error(e.getMessage());
        } catch (Exception e) {
            return Result.error("注册失败：" + e.getMessage());
        }
    }
    
    /*查询用户列表接口*/
    @GetMapping("/query")
    public Result<List<UserEntity>> query() {
        try {
            List<UserEntity> users = userService.findAllUsers();
            // 移除密码信息
            users.forEach(user -> user.setPassword(null));
            return Result.success(users);
        } catch (Exception e) {
            return Result.error("查询失败：" + e.getMessage());
        }
    }
    
    /*根据手机号查询用户接口*/
    @GetMapping("/query/{phone}")
    public Result<UserEntity> queryByPhone(@PathVariable String phone) {
        try {
            UserEntity user = userService.findByPhone(phone);
            if (user == null) {
                return Result.error("用户不存在");
            }
            user.setPassword(null); // 不返回密码
            return Result.success(user);
        } catch (Exception e) {
            return Result.error("查询失败：" + e.getMessage());
        }
    }
    /*用户登录接口*/
    @PostMapping("/login")
    public Result<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        try {
            LoginResponseDTO response = userService.login(loginDTO);
            if (response == null) {
                return Result.error("登录失败：返回数据为空");
            }
            if (response.getToken() == null || response.getUser() == null) {
                return Result.error("登录失败：token或用户信息为空");
            }
            return Result.success(response);
        } catch (IllegalArgumentException e) {
            return Result.error(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(); // 打印异常堆栈
            return Result.error("登录失败：" + e.getMessage());
        }
    }
    /*获取用户信息接口（需要JWT认证）*/
    @GetMapping("/profile")
    public Result<UserEntity> profile(@RequestAttribute("userId") Long userId) {
        try {
            UserEntity user = userService.findById(userId);
            if (user == null) {
                return Result.error("用户不存在");
            }
            user.setPassword(null); // 不返回密码
            return Result.success(user);
        } catch (Exception e) {
            return Result.error("获取用户信息失败：" + e.getMessage());
        }
    }
    /*修改用户信息接口*/
    @PutMapping("/update")
    public Result<UserEntity> update(UserDTO userDTO) {
        return Result.success(new UserEntity());
    }

}
