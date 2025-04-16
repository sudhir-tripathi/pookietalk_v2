package com.pookietalk.mapper;

import com.pookietalk.dto.response.ChatRoomResponse;
import com.pookietalk.model.entity.ChatRoom;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ChatRoomMapper {
    
    public ChatRoomResponse toResponse(ChatRoom room) {
        return ChatRoomResponse.builder()
                .id(room.getId())
                .name(room.getName())
                .description(room.getDescription())
                .creatorUsername(room.getCreator().getUsername())
                .memberUsernames(room.getMembers().stream()
                        .map(user -> user.getUsername())
                        .collect(Collectors.toSet()))
                .createdAt(room.getCreatedAt())
                .type(room.getType())
                .isPrivate(room.isPrivate())
                .build();
    }
}