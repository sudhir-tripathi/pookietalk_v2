import { User } from './auth';

export interface Message {
  id: string;
  content: string;
  sender: User;
  roomId: string;
  createdAt: string;
  attachments?: Attachment[];
}

export interface ChatRoom {
  id: string;
  name: string;
  createdBy: User;
  participants: User[];
  createdAt: string;
  lastMessage?: Message;
}

export interface Attachment {
  id: string;
  url: string;
  type: string;
  name: string;
  size: number;
}