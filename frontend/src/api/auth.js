import api from './config';
import axios from 'axios';

export const authApi = {
  // Вход (POST /api/auth/signin)
  login: async (email, password) => {
    const response = await api.post('/auth/signin', { email, password });
    return response.data; // { accessToken, refreshToken }
  },

  // Регистрация (POST /api/auth/signup)
  register: async (userData, image) => {
    const formData = new FormData();
    formData.append('userDto', JSON.stringify(userData));
    if (image) formData.append('image', image);

    const response = await api.post('/auth/signup', formData, {
      headers: { 'Content-Type': 'multipart/form-data' },
    });
    return response.data;
  },

  // Обновление access-токена (POST /api/auth/access)
  refreshAccessToken: async (refreshToken) => {
    const response = await api.post('/auth/access', { refreshToken });
    return response.data;
  },
};