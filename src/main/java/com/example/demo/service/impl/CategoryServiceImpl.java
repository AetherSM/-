package com.example.demo.service.impl;

import com.example.demo.constant.Messages;
import com.example.demo.mapper.CategoryMapper;
import com.example.demo.pojo.dto.CategoryDTO;
import com.example.demo.pojo.entity.Category;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> list(Integer parentId) {
        return categoryMapper.listByParent(parentId);
    }

    @Override
    public Category create(CategoryDTO dto) {
        if (!StringUtils.hasText(dto.getCategoryName())) {
            throw new IllegalArgumentException("分类名称不能为空");
        }
        Category c = new Category();
        c.setCategoryName(dto.getCategoryName());
        c.setParentId(dto.getParentId() == null ? 0 : dto.getParentId());
        c.setSortOrder(dto.getSortOrder() == null ? 0 : dto.getSortOrder());
        c.setIcon(dto.getIcon());
        c.setStatus(dto.getStatus() == null ? 1 : dto.getStatus());
        categoryMapper.insert(c);
        return categoryMapper.findById(c.getCategoryId());
    }

    @Override
    public Category update(Integer categoryId, CategoryDTO dto) {
        Category existing = categoryMapper.findById(categoryId);
        if (existing == null) {
            throw new IllegalArgumentException(Messages.CATEGORY_NOT_FOUND);
        }
        existing.setCategoryName(dto.getCategoryName());
        existing.setParentId(dto.getParentId());
        existing.setSortOrder(dto.getSortOrder());
        existing.setIcon(dto.getIcon());
        existing.setStatus(dto.getStatus());
        categoryMapper.update(existing);
        return categoryMapper.findById(categoryId);
    }

    @Override
    public void updateStatus(Integer categoryId, Integer status) {
        categoryMapper.updateStatus(categoryId, status);
    }

    @Override
    public Category findById(Integer categoryId) {
        return categoryMapper.findById(categoryId);
    }
}
