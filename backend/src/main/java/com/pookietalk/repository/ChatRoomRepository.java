package com.pookietalk.repository;

import com.pookietalk.model.entity.ChatRoom;
import com.pookietalk.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    List<ChatRoom> findByMembers(User member);
    List<ChatRoom> findByCreator(User creator);
    
    @Query("SELECT cr FROM ChatRoom cr WHERE cr.isPrivate = false")
    List<ChatRoom> findAllPublicRooms();
    
    @Query("SELECT cr FROM ChatRoom cr JOIN cr.members m WHERE m = :user")
    List<ChatRoom> findAllRoomsForUser(User user);
}