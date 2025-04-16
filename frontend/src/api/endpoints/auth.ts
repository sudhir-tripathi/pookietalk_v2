import axiosInstance from '../axios.config';
import { LoginRequest, RegisterRequest, AuthResponse } from '../../types/auth';

export const authApi = {
  login: (data: LoginRequest) =>
    axiosInstance.post<AuthResponse>('/auth/login', data),
    
  register: (data: RegisterRequest) =>
    axiosInstance.post<AuthResponse>('/auth/register', data),
    
  logout: () =>
    axiosInstance.post('/auth/logout'),
    
  refreshToken: (refreshToken: string) =>
    axiosInstance.post<AuthResponse>('/auth/refresh', { refreshToken })
};