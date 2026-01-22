package com.example.demo.service.impl;

import com.example.demo.mapper.ChatMessageMapper;
import com.example.demo.pojo.entity.ChatMessage;
import com.example.demo.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatMessageMapper chatMessageMapper;

    @Override
    public void sendMessage(Long senderId, Long receiverId, String content, Integer type) {
        ChatMessage msg = new ChatMessage();
        msg.setSenderId(senderId);
        msg.setReceiverId(receiverId);
        msg.setContent(content);
        msg.setMsgType(type);
        chatMessageMapper.insert(msg);
    }

    @Override
    public List<ChatMessage> getHistory(Long userId1, Long userId2) {
        return chatMessageMapper.listHistory(userId1, userId2);
    }

    @Override
    public void markRead(Long senderId, Long receiverId) {
        chatMessageMapper.markRead(senderId, receiverId);
    }
}
