package com.example.demo.mapper;

import com.example.demo.pojo.entity.ChatMessage;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ChatMessageMapper {
    void insert(ChatMessage message);
    List<ChatMessage> listHistory(Long userId1, Long userId2);
    void markRead(Long senderId, Long receiverId);
}
