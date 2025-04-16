import React from 'react';
import { Message } from '../../../types/chat';
import { useSelector } from 'react-redux';
import { RootState } from '../../../store/store';

interface MessageListProps {
  messages: Message[];
}

const MessageList: React.FC<MessageListProps> = ({ messages }) => {
  const currentUser = useSelector((state: RootState) => state.auth.user);

  return (
    <div className="space-y-4">
      {messages.map((message) => {
        const isOwnMessage = message.sender.id === currentUser?.id;
        
        return (
          <div
            key={message.id}
            className={`flex ${isOwnMessage ? 'justify-end' : 'justify-start'}`}
          >
            <div
              className={`max-w-[70%] rounded-lg p-3 ${
                isOwnMessage
                  ? 'bg-blue-500 text-white'
                  : 'bg-gray-100 text-gray-900'
              }`}
            >
              {!isOwnMessage && (
                <div className="text-sm font-semibold mb-1">
                  {message.sender.username}
                </div>
              )}
              <div className="break-words">{message.content}</div>
              {message.attachments && message.attachments.length > 0 && (
                <div className="mt-2 space-y-2">
                  {message.attachments.map((attachment) => (
                    <div
                      key={attachment.id}
                      className="flex items-center space-x-2"
                    >
                      <a
                        href={attachment.url}
                        target="_blank"
                        rel="noopener noreferrer"
                        className="text-sm underline"
                      >
                        {attachment.name}
                      </a>
                      <span className="text-xs">
                        ({Math.round(attachment.size / 1024)}KB)
                      </span>
                    </div>
                  ))}
                </div>
              )}
              <div className="text-xs mt-1 opacity-75">
                {new Date(message.createdAt).toLocaleTimeString()}
              </div>
            </div>
          </div>
        );
      })}
    </div>
  );
};

export default MessageList;