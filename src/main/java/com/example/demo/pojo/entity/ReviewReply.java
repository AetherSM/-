package com.example.demo.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "评价回复实体")
public class ReviewReply {
    @Schema(description = "回复ID")
    private Long replyId;
    @Schema(description = "评价ID")
    private Long reviewId;
    @Schema(description = "回复人ID")
    private Long replierId;
    @Schema(description = "回复人昵称")
    private String replierNickname;
    @Schema(description = "回复内容")
    private String content;
    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
