package com.pookietalk.mapper;

import com.pookietalk.dto.response.ChatMessageResponse;
import com.pookietalk.model.entity.ChatMessage;
import org.springframework.stereotype.Component;

@Component
public class ChatMapper {
    
    public ChatMessageResponse toResponse(ChatMessage message) {
        return ChatMessageResponse.builder()
                .id(message.getId())
                .content(message.getContent())
                .senderUsername(message.getSender().getUsername())
                .chatRoomId(message.getChatRoom().getId())
                .timestamp(message.getTimestamp())
                .messageType(message.getMessageType())
                .build();
    }
}