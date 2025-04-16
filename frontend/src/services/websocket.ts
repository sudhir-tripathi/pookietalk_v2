import { io, Socket } from 'socket.io-client';
import { store } from '../store/store';
import { addMessage } from '../store/slices/chatSlice';
import { Message } from '../types/chat';

class WebSocketService {
  private socket: Socket | null = null;
  
  connect() {
    this.socket = io('/ws', {
      auth: {
        token: localStorage.getItem('accessToken')
      }
    });

    this.setupListeners();
  }

  private setupListeners() {
    if (!this.socket) return;

    this.socket.on('connect', () => {
      console.log('WebSocket connected');
    });

    this.socket.on('disconnect', () => {
      console.log('WebSocket disconnected');
    });

    this.socket.on('message', (message: Message) => {
      store.dispatch(addMessage(message));
    });

    this.socket.on('error', (error: Error) => {
      console.error('WebSocket error:', error);
    });
  }

  joinRoom(roomId: string) {
    if (!this.socket) return;
    this.socket.emit('join-room', roomId);
  }

  leaveRoom(roomId: string) {
    if (!this.socket) return;
    this.socket.emit('leave-room', roomId);
  }

  sendMessage(message: Omit<Message, 'id' | 'createdAt'>) {
    if (!this.socket) return;
    this.socket.emit('send-message', message);
  }

  disconnect() {
    if (this.socket) {
      this.socket.disconnect();
      this.socket = null;
    }
  }
}

export const wsService = new WebSocketService();
export default wsService;