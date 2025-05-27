import { createContext, useState, useEffect, useContext, useCallback } from 'react';
import { authApi } from '../api/auth';
// import { jwtDecode } from 'jwt-decode';
import { useNotification } from './NotificationContext';

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [isLoading, setIsLoading] = useState(true);
  const { showNotification } = useNotification();

  // Проверка авторизации при загрузке
  useEffect(() => {
    const checkAuth = async () => {
      try {
        const token = localStorage.getItem('accessToken');
        if (token) {
//           const decoded = jwtDecode(token);
          const userData = await authApi.getMe();
          setUser(userData);
        }
      } catch (error) {
        console.error('Auth check failed:', error);
        localStorage.removeItem('accessToken');
      } finally {
        setIsLoading(false);
      }
    };
    checkAuth();
  }, []);

  const login = useCallback(async (email, password) => {
    try {
      setIsLoading(true);
      const { accessToken, refreshToken, user } = await authApi.login(email, password);
      localStorage.setItem('accessToken', accessToken);
      localStorage.setItem('refreshToken', refreshToken);
      setUser(user);
      showNotification('Вход выполнен', 'Добро пожаловать!', 'success');
      return true;
    } catch (error) {
      showNotification('Ошибка входа', error.response?.data?.message || 'Неверные данные', 'error');
      throw error;
    } finally {
      setIsLoading(false);
    }
  }, [showNotification]);

  const register = useCallback(async (userData) => {
    try {
      setIsLoading(true);
      const { accessToken, refreshToken, user } = await authApi.register(userData);
      localStorage.setItem('accessToken', accessToken);
      localStorage.setItem('refreshToken', refreshToken);
      setUser(user);
      showNotification('Регистрация успешна', 'Аккаунт создан', 'success');
      return true;
    } catch (error) {
      showNotification('Ошибка регистрации', error.response?.data?.message || 'Ошибка сервера', 'error');
      throw error;
    } finally {
      setIsLoading(false);
    }
  }, [showNotification]);

  const logout = useCallback(() => {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    setUser(null);
    showNotification('Вы вышли из системы', '', 'info');
  }, [showNotification]);

  const refreshToken = useCallback(async () => {
    try {
      const refreshToken = localStorage.getItem('refreshToken');
      if (!refreshToken) throw new Error('No refresh token');
      
      const { accessToken } = await authApi.refreshAccessToken(refreshToken);
      localStorage.setItem('accessToken', accessToken);
      return true;
    } catch (error) {
      logout();
      return false;
    }
  }, [logout]);

  const updateUser = useCallback(async (updatedData) => {
    try {
      const updatedUser = await authApi.updateProfile(updatedData);
      setUser(updatedUser);
      showNotification('Профиль обновлен', '', 'success');
      return updatedUser;
    } catch (error) {
      showNotification('Ошибка обновления', error.response?.data?.message || 'Ошибка сервера', 'error');
      throw error;
    }
  }, [showNotification]);

  return (
    <AuthContext.Provider
      value={{
        user,
        isAuthenticated: !!user,
        isLoading,
        login,
        register,
        logout,
        refreshToken,
        updateUser
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};