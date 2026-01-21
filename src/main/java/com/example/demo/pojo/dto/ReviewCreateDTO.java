package com.example.demo.pojo.dto;

import lombok.Data;

import java.util.List;

@Data
public class ReviewCreateDTO {
    private Integer rating; // 1-5
    private String content;
    private List<String> images;
    private Integer isAnonymous;
}

