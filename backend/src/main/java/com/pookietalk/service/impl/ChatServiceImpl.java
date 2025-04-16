package com.pookietalk.service.impl;

import com.pookietalk.dto.request.ChatMessageRequest;
import com.pookietalk.dto.response.ChatMessageResponse;
import com.pookietalk.exception.ChatException;
import com.pookietalk.mapper.ChatMapper;
import com.pookietalk.model.entity.ChatMessage;
import com.pookietalk.model.entity.ChatRoom;
import com.pookietalk.model.entity.User;
import com.pookietalk.model.enum.MessageType;
import com.pookietalk.repository.ChatMessageRepository;
import com.pookietalk.repository.ChatRoomRepository;
import com.pookietalk.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMapper chatMapper;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    @Transactional
    public ChatMessageResponse sendMessage(ChatMessageRequest request, User sender) {
        ChatRoom chatRoom = chatRoomRepository.findById(request.getChatRoomId())
                .orElseThrow(() -> new ChatException("Chat room not found"));

        if (!chatRoom.getMembers().contains(sender)) {
            throw new ChatException("User is not a member of this chat room");
        }

        ChatMessage message = ChatMessage.builder()
                .sender(sender)
                .chatRoom(chatRoom)
                .content(request.getContent())
                .timestamp(LocalDateTime.now())
                .messageType(MessageType.CHAT)
                .deleted(false)
                .build();

        ChatMessage savedMessage = chatMessageRepository.save(message);
        ChatMessageResponse response = chatMapper.toResponse(savedMessage);

        // Send message to WebSocket subscribers
        messagingTemplate.convertAndSend("/topic/room." + chatRoom.getId(), response);

        return response;
    }

    @Override
    public Page<ChatMessageResponse> getChatHistory(Long chatRoomId, Pageable pageable) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new ChatException("Chat room not found"));

        return chatMessageRepository.findByChatRoomOrderByTimestampDesc(chatRoom, pageable)
                .map(chatMapper::toResponse);
    }

    @Override
    @Transactional
    public void deleteMessage(Long messageId, User user) {
        ChatMessage message = chatMessageRepository.findById(messageId)
                .orElseThrow(() -> new ChatException("Message not found"));

        if (!message.getSender().equals(user)) {
            throw new ChatException("User is not authorized to delete this message");
        }

        message.setDeleted(true);
        message.setContent("This message has been deleted");
        chatMessageRepository.save(message);

        // Notify WebSocket subscribers about the deletion
        messagingTemplate.convertAndSend(
                "/topic/room." + message.getChatRoom().getId(),
                chatMapper.toResponse(message)
        );
    }

    @Override
    public void markMessageAsRead(Long messageId, User user) {
        // Implement message read status tracking logic here
    }
}