package com.example.demo.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "二手求购信息实体")
public class WantedPost {
    @Schema(description = "求购ID")
    private Long postId;
    
    @Schema(description = "发布用户ID")
    private Long userId;
    
    @Schema(description = "标题")
    private String title;
    
    @Schema(description = "详细描述")
    private String description;
    
    @Schema(description = "价格范围/预算")
    private String priceRange;
    
    @Schema(description = "状态: 1-求购中, 2-已买到, 3-已关闭")
    private Integer status; 
    
    @Schema(description = "发布时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    
    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
