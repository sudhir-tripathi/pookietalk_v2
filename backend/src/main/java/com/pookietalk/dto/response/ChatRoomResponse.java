package com.pookietalk.dto.response;

import com.pookietalk.model.enum.ChatRoomType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomResponse {
    private Long id;
    private String name;
    private String description;
    private String creatorUsername;
    private Set<String> memberUsernames;
    private LocalDateTime createdAt;
    private ChatRoomType type;
    private boolean isPrivate;
}