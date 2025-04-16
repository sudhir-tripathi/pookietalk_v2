import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import { Provider } from 'react-redux';
import { BrowserRouter } from 'react-router-dom';
import configureStore from 'redux-mock-store';
import ChatRoom from '../ChatRoom';

const mockStore = configureStore([]);

describe('ChatRoom', () => {
  let store: any;

  beforeEach(() => {
    store = mockStore({
      chat: {
        currentRoom: {
          id: '1',
          name: 'Test Room',
          participants: [
            { id: '1', username: 'user1' },
            { id: '2', username: 'user2' },
          ],
        },
        messages: [
          {
            id: '1',
            content: 'Hello World',
            sender: { id: '1', username: 'user1' },
            roomId: '1',
            createdAt: new Date().toISOString(),
          },
        ],
        loading: false,
      },
      auth: {
        user: { id: '1', username: 'user1' },
      },
    });
  });

  it('renders chat room with messages', () => {
    render(
      <Provider store={store}>
        <BrowserRouter>
          <ChatRoom />
        </BrowserRouter>
      </Provider>
    );

    expect(screen.getByText('Test Room')).toBeInTheDocument();
    expect(screen.getByText('Hello World')).toBeInTheDocument();
  });

  it('shows loading state', () => {
    store = mockStore({
      chat: {
        currentRoom: null,
        messages: [],
        loading: true,
      },
      auth: {
        user: { id: '1', username: 'user1' },
      },
    });

    render(
      <Provider store={store}>
        <BrowserRouter>
          <ChatRoom />
        </BrowserRouter>
      </Provider>
    );

    expect(screen.getByText('Loading...')).toBeInTheDocument();
  });

  it('shows error when room not found', () => {
    store = mockStore({
      chat: {
        currentRoom: null,
        messages: [],
        loading: false,
      },
      auth: {
        user: { id: '1', username: 'user1' },
      },
    });

    render(
      <Provider store={store}>
        <BrowserRouter>
          <ChatRoom />
        </BrowserRouter>
      </Provider>
    );

    expect(screen.getByText('Chat room not found')).toBeInTheDocument();
  });
});