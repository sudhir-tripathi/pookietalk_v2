package com.pookietalk.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageRequest {
    @NotNull(message = "Chat room ID is required")
    private Long chatRoomId;

    @NotBlank(message = "Message content cannot be empty")
    private String content;
}