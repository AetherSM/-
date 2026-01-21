package com.example.demo.service;

import com.example.demo.pojo.dto.CategoryDTO;
import com.example.demo.pojo.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> list(Integer parentId);

    Category create(CategoryDTO dto);

    Category update(Integer categoryId, CategoryDTO dto);

    void updateStatus(Integer categoryId, Integer status);

    Category findById(Integer categoryId);
}

