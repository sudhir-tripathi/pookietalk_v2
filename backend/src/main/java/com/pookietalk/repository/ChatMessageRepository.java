package com.pookietalk.repository;

import com.pookietalk.model.entity.ChatMessage;
import com.pookietalk.model.entity.ChatRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    Page<ChatMessage> findByChatRoomOrderByTimestampDesc(ChatRoom chatRoom, Pageable pageable);
    List<ChatMessage> findByChatRoomAndTimestampAfter(ChatRoom chatRoom, LocalDateTime timestamp);
    void deleteByChatRoom(ChatRoom chatRoom);
}