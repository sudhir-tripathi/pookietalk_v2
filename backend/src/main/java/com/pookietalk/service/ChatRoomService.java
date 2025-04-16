package com.pookietalk.service;

import com.pookietalk.dto.response.ChatRoomResponse;
import com.pookietalk.model.entity.User;

import java.util.List;

public interface ChatRoomService {
    ChatRoomResponse createRoom(String name, String description, boolean isPrivate, User creator);
    ChatRoomResponse getRoom(Long roomId);
    List<ChatRoomResponse> getAllRooms();
    List<ChatRoomResponse> getUserRooms(User user);
    void addMember(Long roomId, String username, User requestingUser);
    void removeMember(Long roomId, String username, User requestingUser);
    void deleteRoom(Long roomId, User requestingUser);
}