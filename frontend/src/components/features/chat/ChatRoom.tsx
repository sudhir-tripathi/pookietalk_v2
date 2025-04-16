import React, { useEffect, useRef } from 'react';
import { useParams } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
import { RootState } from '../../../store/store';
import { setCurrentRoom, setMessages } from '../../../store/slices/chatSlice';
import { chatApi } from '../../../api/endpoints/chat';
import wsService from '../../../services/websocket';
import MessageList from './MessageList';
import MessageInput from './MessageInput';

const ChatRoom: React.FC = () => {
  const { roomId } = useParams<{ roomId: string }>();
  const dispatch = useDispatch();
  const messagesEndRef = useRef<HTMLDivElement>(null);
  
  const { currentRoom, messages, loading } = useSelector(
    (state: RootState) => state.chat
  );

  useEffect(() => {
    if (roomId) {
      loadChatRoom();
      wsService.joinRoom(roomId);
    }

    return () => {
      if (roomId) {
        wsService.leaveRoom(roomId);
      }
    };
  }, [roomId]);

  const loadChatRoom = async () => {
    try {
      const [roomResponse, messagesResponse] = await Promise.all([
        chatApi.getChatRooms(),
        chatApi.getChatHistory(roomId!)
      ]);

      const room = roomResponse.data.find(r => r.id === roomId);
      if (room) {
        dispatch(setCurrentRoom(room));
        dispatch(setMessages(messagesResponse.data));
      }
    } catch (error) {
      console.error('Error loading chat room:', error);
    }
  };

  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  };

  useEffect(scrollToBottom, [messages]);

  if (loading) {
    return <div>Loading...</div>;
  }

  if (!currentRoom) {
    return <div>Chat room not found</div>;
  }

  return (
    <div className="h-full flex flex-col">
      <div className="p-4 border-b">
        <h2 className="text-xl font-semibold">{currentRoom.name}</h2>
        <p className="text-sm text-gray-500">
          {currentRoom.participants.length} participants
        </p>
      </div>
      
      <div className="flex-1 overflow-y-auto p-4">
        <MessageList messages={messages} />
        <div ref={messagesEndRef} />
      </div>
      
      <div className="p-4 border-t">
        <MessageInput roomId={roomId!} />
      </div>
    </div>
  );
};

export default ChatRoom;