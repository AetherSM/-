package com.example.demo.controller;

import com.example.demo.context.BaseContext;
import com.example.demo.pojo.dto.CategoryDTO;
import com.example.demo.pojo.entity.Category;
import com.example.demo.pojo.entity.UserEntity;
import com.example.demo.pojo.result.Result;
import com.example.demo.service.CategoryService;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@Tag(name = "分类管理", description = "商品分类相关接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    /**
     * 获取分类列表
     * @param parentId 父分类ID（可选）
     * @return 分类列表
     */
    @GetMapping
    @Operation(summary = "获取分类列表", description = "获取商品分类列表，可指定父分类ID")
    public Result<List<Category>> list(@Parameter(description = "父分类ID") @RequestParam(value = "parentId", required = false) Integer parentId) {
        return Result.success(categoryService.list(parentId));
    }

    /**
     * 创建分类（仅商家）
     * @param dto 分类信息
     * @return 创建的分类
     */
    @PostMapping
    @Operation(summary = "创建分类", description = "创建新的商品分类（仅商家）")
    public Result<Category> create(@RequestBody CategoryDTO dto) {
        ensureSeller();
        return Result.success(categoryService.create(dto));
    }

    /**
     * 更新分类信息（仅商家）
     * @param id 分类ID
     * @param dto 更新信息
     * @return 更新后的分类
     */
    @PutMapping("/{id}")
    @Operation(summary = "更新分类", description = "更新商品分类信息（仅商家）")
    public Result<Category> update(@PathVariable Integer id, @RequestBody CategoryDTO dto) {
        ensureSeller();
        return Result.success(categoryService.update(id, dto));
    }

    /**
     * 更新分类状态（仅商家）
     * @param id 分类ID
     * @param dto 包含状态信息
     * @return 无
     */
    @PatchMapping("/{id}/status")
    @Operation(summary = "更新分类状态", description = "更新商品分类状态（启用/禁用）（仅商家）")
    public Result<Void> updateStatus(@PathVariable Integer id, @RequestBody CategoryDTO dto) {
        ensureSeller();
        categoryService.updateStatus(id, dto.getStatus());
        return Result.success();
    }

    private void ensureSeller() {
        Long userId = BaseContext.getCurrentId();
        if (userId == null) {
            throw new IllegalArgumentException("未登录");
        }
        UserEntity user = userService.findById(userId);
        if (user == null || user.getUserType() == null || user.getUserType() != 3) {
            throw new IllegalArgumentException("仅商家可操作分类");
        }
    }
}

