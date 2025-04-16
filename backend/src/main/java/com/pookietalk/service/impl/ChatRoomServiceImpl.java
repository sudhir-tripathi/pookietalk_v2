package com.pookietalk.service.impl;

import com.pookietalk.dto.response.ChatRoomResponse;
import com.pookietalk.exception.ChatException;
import com.pookietalk.mapper.ChatRoomMapper;
import com.pookietalk.model.entity.ChatRoom;
import com.pookietalk.model.entity.User;
import com.pookietalk.model.enum.ChatRoomType;
import com.pookietalk.repository.ChatRoomRepository;
import com.pookietalk.repository.UserRepository;
import com.pookietalk.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final ChatRoomMapper chatRoomMapper;

    @Override
    @Transactional
    public ChatRoomResponse createRoom(String name, String description, boolean isPrivate, User creator) {
        ChatRoom chatRoom = ChatRoom.builder()
                .name(name)
                .description(description)
                .creator(creator)
                .type(isPrivate ? ChatRoomType.PRIVATE : ChatRoomType.PUBLIC)
                .isPrivate(isPrivate)
                .createdAt(LocalDateTime.now())
                .build();

        chatRoom.getMembers().add(creator);
        ChatRoom savedRoom = chatRoomRepository.save(chatRoom);
        return chatRoomMapper.toResponse(savedRoom);
    }

    @Override
    public ChatRoomResponse getRoom(Long roomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new ChatException("Chat room not found"));
        return chatRoomMapper.toResponse(chatRoom);
    }

    @Override
    public List<ChatRoomResponse> getAllRooms() {
        return chatRoomRepository.findAllPublicRooms().stream()
                .map(chatRoomMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ChatRoomResponse> getUserRooms(User user) {
        return chatRoomRepository.findAllRoomsForUser(user).stream()
                .map(chatRoomMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addMember(Long roomId, String username, User requestingUser) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new ChatException("Chat room not found"));

        if (!chatRoom.getCreator().equals(requestingUser)) {
            throw new ChatException("Only the room creator can add members");
        }

        User newMember = userRepository.findByUsername(username)
                .orElseThrow(() -> new ChatException("User not found"));

        chatRoom.getMembers().add(newMember);
        chatRoomRepository.save(chatRoom);
    }

    @Override
    @Transactional
    public void removeMember(Long roomId, String username, User requestingUser) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new ChatException("Chat room not found"));

        if (!chatRoom.getCreator().equals(requestingUser)) {
            throw new ChatException("Only the room creator can remove members");
        }

        User member = userRepository.findByUsername(username)
                .orElseThrow(() -> new ChatException("User not found"));

        if (member.equals(chatRoom.getCreator())) {
            throw new ChatException("Cannot remove the room creator");
        }

        chatRoom.getMembers().remove(member);
        chatRoomRepository.save(chatRoom);
    }

    @Override
    @Transactional
    public void deleteRoom(Long roomId, User requestingUser) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new ChatException("Chat room not found"));

        if (!chatRoom.getCreator().equals(requestingUser)) {
            throw new ChatException("Only the room creator can delete the room");
        }

        chatRoomRepository.delete(chatRoom);
    }
}