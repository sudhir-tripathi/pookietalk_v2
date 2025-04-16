package com.pookietalk.controller;

import com.pookietalk.dto.response.ChatRoomResponse;
import com.pookietalk.model.entity.User;
import com.pookietalk.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping
    public ResponseEntity<ChatRoomResponse> createRoom(
            @RequestParam String name,
            @RequestParam(required = false) String description,
            @RequestParam(defaultValue = "false") boolean isPrivate,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(chatRoomService.createRoom(name, description, isPrivate, user));
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<ChatRoomResponse> getRoom(@PathVariable Long roomId) {
        return ResponseEntity.ok(chatRoomService.getRoom(roomId));
    }

    @GetMapping
    public ResponseEntity<List<ChatRoomResponse>> getAllRooms() {
        return ResponseEntity.ok(chatRoomService.getAllRooms());
    }

    @GetMapping("/my")
    public ResponseEntity<List<ChatRoomResponse>> getUserRooms(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(chatRoomService.getUserRooms(user));
    }

    @PostMapping("/{roomId}/members/{username}")
    public ResponseEntity<Void> addMember(
            @PathVariable Long roomId,
            @PathVariable String username,
            @AuthenticationPrincipal User user) {
        chatRoomService.addMember(roomId, username, user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{roomId}/members/{username}")
    public ResponseEntity<Void> removeMember(
            @PathVariable Long roomId,
            @PathVariable String username,
            @AuthenticationPrincipal User user) {
        chatRoomService.removeMember(roomId, username, user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> deleteRoom(
            @PathVariable Long roomId,
            @AuthenticationPrincipal User user) {
        chatRoomService.deleteRoom(roomId, user);
        return ResponseEntity.ok().build();
    }
}