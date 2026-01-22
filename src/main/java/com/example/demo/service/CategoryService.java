package com.example.demo.service;

import com.example.demo.pojo.dto.CategoryDTO;
import com.example.demo.pojo.entity.Category;

import java.util.List;

/**
 * 商品分类业务接口
 */
public interface CategoryService {
    
    /**
     * 查询分类列表
     * @param parentId 父分类ID (可选，null查询所有或顶级分类)
     * @return 分类列表
     */
    List<Category> list(Integer parentId);

    /**
     * 创建分类
     * @param dto 分类信息
     * @return 创建后的分类
     */
    Category create(CategoryDTO dto);

    /**
     * 更新分类
     * @param categoryId 分类ID
     * @param dto 更新信息
     * @return 更新后的分类
     */
    Category update(Integer categoryId, CategoryDTO dto);

    /**
     * 更新分类状态
     * @param categoryId 分类ID
     * @param status 状态
     */
    void updateStatus(Integer categoryId, Integer status);

    /**
     * 根据ID查询分类
     * @param categoryId 分类ID
     * @return 分类信息
     */
    Category findById(Integer categoryId);
}
