package com.example.demo.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "聊天消息实体")
public class ChatMessage {
    @Schema(description = "消息ID")
    private Long messageId;
    
    @Schema(description = "发送者ID")
    private Long senderId;
    
    @Schema(description = "接收者ID")
    private Long receiverId;
    
    @Schema(description = "消息内容")
    private String content;
    
    @Schema(description = "消息类型: 1-文字, 2-图片")
    private Integer msgType; 
    
    @Schema(description = "是否已读: 0-未读, 1-已读")
    private Integer isRead; 
    
    @Schema(description = "发送时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
