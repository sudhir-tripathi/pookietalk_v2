package com.pookietalk.dto.response;

import com.pookietalk.model.enum.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageResponse {
    private Long id;
    private String content;
    private String senderUsername;
    private Long chatRoomId;
    private LocalDateTime timestamp;
    private MessageType messageType;
}