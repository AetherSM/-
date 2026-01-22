package com.example.demo.controller;

import com.example.demo.context.BaseContext;
import com.example.demo.pojo.dto.AddressDTO;
import com.example.demo.pojo.entity.Address;
import com.example.demo.pojo.result.Result;
import com.example.demo.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
@Tag(name = "地址管理", description = "收货地址相关接口")
public class AddressController {

    @Autowired
    private AddressService addressService;

    /**
     * 获取当前登录用户的地址列表
     * @return 地址列表
     */
    @GetMapping
    @Operation(summary = "获取地址列表", description = "获取当前登录用户的所有收货地址")
    public Result<List<Address>> list() {
        Long userId = requireLogin();
        return Result.success(addressService.list(userId));
    }

    /**
     * 创建新地址
     * @param dto 地址信息
     * @return 创建的地址
     */
    @PostMapping
    @Operation(summary = "创建新地址", description = "创建新的收货地址")
    public Result<Address> create(@RequestBody AddressDTO dto) {
        Long userId = requireLogin();
        return Result.success(addressService.create(userId, dto));
    }

    /**
     * 更新地址信息
     * @param id 地址ID
     * @param dto 更新的地址信息
     * @return 更新后的地址
     */
    @PutMapping("/{id}")
    @Operation(summary = "更新地址", description = "更新指定ID的收货地址信息")
    public Result<Address> update(@PathVariable Long id, @RequestBody AddressDTO dto) {
        Long userId = requireLogin();
        return Result.success(addressService.update(userId, id, dto));
    }

    /**
     * 删除地址
     * @param id 地址ID
     * @return 无
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除地址", description = "删除指定ID的收货地址")
    public Result<Void> delete(@PathVariable Long id) {
        Long userId = requireLogin();
        addressService.delete(userId, id);
        return Result.success();
    }

    /**
     * 设置默认地址
     * @param id 地址ID
     * @return 无
     */
    @PatchMapping("/{id}/default")
    @Operation(summary = "设置默认地址", description = "将指定地址设置为默认收货地址")
    public Result<Void> setDefault(@PathVariable Long id) {
        Long userId = requireLogin();
        addressService.setDefault(userId, id);
        return Result.success();
    }

    private Long requireLogin() {
        Long userId = BaseContext.getCurrentId();
        if (userId == null) {
            throw new IllegalArgumentException("未登录");
        }
        return userId;
    }
}

