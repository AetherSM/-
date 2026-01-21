package com.example.demo.pojo.dto;

import lombok.Data;

@Data
public class CategoryDTO {
    private String categoryName;
    private Integer parentId;
    private Integer sortOrder;
    private String icon;
    private Integer status;
}

