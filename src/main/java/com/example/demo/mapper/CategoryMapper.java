package com.example.demo.mapper;

import com.example.demo.pojo.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CategoryMapper {
    List<Category> listByParent(@Param("parentId") Integer parentId);

    Category findById(@Param("categoryId") Integer categoryId);

    void insert(Category category);

    void update(Category category);

    void updateStatus(@Param("categoryId") Integer categoryId, @Param("status") Integer status);
}

