import axios from 'axios';

const API_URL = 'http://localhost:8080/api'; // Замени на свой бэкенд-URL

// Настройка глобальных параметров Axios
const api = axios.create({
  baseURL: API_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Добавляем интерцептор для JWT-токена
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('accessToken');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export default api;