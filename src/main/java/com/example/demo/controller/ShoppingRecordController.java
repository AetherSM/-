package com.example.demo.controller;

import com.example.demo.context.BaseContext;
import com.example.demo.pojo.entity.ShoppingRecord;
import com.example.demo.pojo.result.Result;
import com.example.demo.service.ShoppingRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shopping-records")
@Tag(name = "购物记录", description = "用户购物记录查询接口")
public class ShoppingRecordController {

    @Autowired
    private ShoppingRecordService shoppingRecordService;

    @GetMapping
    @Operation(summary = "查询我的购物记录", description = "查询当前登录用户的购物记录")
    public Result<List<ShoppingRecord>> listMy(
            @RequestParam(required = false) String start,
            @RequestParam(required = false) String end,
            @RequestParam(required = false) String orderNo
    ) {
        Long userId = BaseContext.getCurrentId();
        if (userId == null) throw new IllegalArgumentException("未登录");
        if ((start != null && !start.isEmpty()) || (end != null && !end.isEmpty()) || (orderNo != null && !orderNo.isEmpty())) {
            return Result.success(shoppingRecordService.listByUserFilter(userId, start, end, orderNo));
        }
        return Result.success(shoppingRecordService.listByUser(userId));
    }

    @DeleteMapping("/{recordId}")
    @Operation(summary = "删除购物记录", description = "删除我的一条购物记录")
    public Result<Void> delete(@PathVariable Long recordId) {
        Long userId = BaseContext.getCurrentId();
        if (userId == null) throw new IllegalArgumentException("未登录");
        shoppingRecordService.delete(userId, recordId);
        return Result.success();
    }

    @DeleteMapping("/batch")
    @Operation(summary = "批量删除购物记录", description = "根据记录ID列表批量删除当前用户的购物记录")
    public Result<Void> deleteBatch(@RequestBody List<Long> ids) {
        Long userId = BaseContext.getCurrentId();
        if (userId == null) throw new IllegalArgumentException("未登录");
        shoppingRecordService.deleteBatch(userId, ids);
        return Result.success();
    }
}
