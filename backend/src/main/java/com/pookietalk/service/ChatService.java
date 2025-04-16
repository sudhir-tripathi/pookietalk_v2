package com.pookietalk.service;

import com.pookietalk.dto.request.ChatMessageRequest;
import com.pookietalk.dto.response.ChatMessageResponse;
import com.pookietalk.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChatService {
    ChatMessageResponse sendMessage(ChatMessageRequest request, User sender);
    Page<ChatMessageResponse> getChatHistory(Long chatRoomId, Pageable pageable);
    void deleteMessage(Long messageId, User user);
    void markMessageAsRead(Long messageId, User user);
}