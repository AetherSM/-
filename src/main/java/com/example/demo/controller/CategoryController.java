package com.example.demo.controller;

import com.example.demo.context.BaseContext;
import com.example.demo.pojo.dto.CategoryDTO;
import com.example.demo.pojo.entity.Category;
import com.example.demo.pojo.entity.UserEntity;
import com.example.demo.pojo.result.Result;
import com.example.demo.service.CategoryService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public Result<List<Category>> list(@RequestParam(value = "parentId", required = false) Integer parentId) {
        return Result.success(categoryService.list(parentId));
    }

    @PostMapping
    public Result<Category> create(@RequestBody CategoryDTO dto) {
        ensureSeller();
        return Result.success(categoryService.create(dto));
    }

    @PutMapping("/{id}")
    public Result<Category> update(@PathVariable Integer id, @RequestBody CategoryDTO dto) {
        ensureSeller();
        return Result.success(categoryService.update(id, dto));
    }

    @PatchMapping("/{id}/status")
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

