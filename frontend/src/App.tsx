import React, { useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
import { RootState } from './store/store';
import { setUser } from './store/slices/authSlice';
import wsService from './services/websocket';

// Layouts
import MainLayout from './components/layout/MainLayout';

// Components
import LoginForm from './components/features/auth/LoginForm';
import RegisterForm from './components/features/auth/RegisterForm';
import ChatRoom from './components/features/chat/ChatRoom';

// Protected Route Component
const ProtectedRoute: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const isAuthenticated = useSelector((state: RootState) => state.auth.isAuthenticated);
  return isAuthenticated ? <>{children}</> : <Navigate to="/login" />;
};

const App: React.FC = () => {
  const dispatch = useDispatch();
  const isAuthenticated = useSelector((state: RootState) => state.auth.isAuthenticated);

  useEffect(() => {
    // Check for existing auth token and reconnect websocket if authenticated
    const token = localStorage.getItem('accessToken');
    if (token && !isAuthenticated) {
      // TODO: Implement token validation and user info fetch
      // For now, just connect websocket if token exists
      wsService.connect();
    }
  }, [isAuthenticated, dispatch]);

  return (
    <Router>
      <Routes>
        {/* Auth Routes */}
        <Route path="/login" element={
          isAuthenticated ? <Navigate to="/chat" /> : <LoginForm />
        } />
        <Route path="/register" element={
          isAuthenticated ? <Navigate to="/chat" /> : <RegisterForm />
        } />

        {/* Protected Routes */}
        <Route path="/" element={
          <ProtectedRoute>
            <MainLayout />
          </ProtectedRoute>
        }>
          <Route index element={<Navigate to="/chat" />} />
          <Route path="chat" element={<ChatRoom />} />
          <Route path="chat/:roomId" element={<ChatRoom />} />
        </Route>

        {/* Catch all route */}
        <Route path="*" element={<Navigate to="/" />} />
      </Routes>
    </Router>
  );
};

export default App;