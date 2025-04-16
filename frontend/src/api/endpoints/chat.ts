import axiosInstance from '../axios.config';
import { Message, ChatRoom } from '../../types/chat';

export const chatApi = {
  getChatRooms: () =>
    axiosInstance.get<ChatRoom[]>('/chat/rooms'),
    
  getChatHistory: (roomId: string) =>
    axiosInstance.get<Message[]>(`/chat/rooms/${roomId}/messages`),
    
  createChatRoom: (name: string) =>
    axiosInstance.post<ChatRoom>('/chat/rooms', { name }),
    
  joinChatRoom: (roomId: string) =>
    axiosInstance.post(`/chat/rooms/${roomId}/join`),
    
  leaveChatRoom: (roomId: string) =>
    axiosInstance.post(`/chat/rooms/${roomId}/leave`)
};