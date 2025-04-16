package com.pookietalk.controller;

import com.pookietalk.dto.request.ChatMessageRequest;
import com.pookietalk.dto.response.ChatMessageResponse;
import com.pookietalk.model.entity.User;
import com.pookietalk.service.ChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/chat.send")
    public void sendMessage(@Payload @Valid ChatMessageRequest request, @AuthenticationPrincipal User user) {
        chatService.sendMessage(request, user);
    }

    @GetMapping("/rooms/{roomId}/messages")
    public ResponseEntity<Page<ChatMessageResponse>> getChatHistory(
            @PathVariable Long roomId,
            Pageable pageable) {
        return ResponseEntity.ok(chatService.getChatHistory(roomId, pageable));
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Void> deleteMessage(
            @PathVariable Long messageId,
            @AuthenticationPrincipal User user) {
        chatService.deleteMessage(messageId, user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/messages/{messageId}/read")
    public ResponseEntity<Void> markMessageAsRead(
            @PathVariable Long messageId,
            @AuthenticationPrincipal User user) {
        chatService.markMessageAsRead(messageId, user);
        return ResponseEntity.ok().build();
    }
}