import { createContext, useState, useContext, useCallback } from 'react';

const NotificationContext = createContext();

export const NotificationProvider = ({ children }) => {
  const [notification, setNotification] = useState(null);
  const [timeoutId, setTimeoutId] = useState(null);

  const showNotification = useCallback((title, message = '', type = 'info', duration = 5000) => {
    // Очищаем предыдущий таймаут
    if (timeoutId) clearTimeout(timeoutId);

    setNotification({ title, message, type });
    
    // Устанавливаем новый таймаут для автоматического скрытия
    const id = setTimeout(() => {
      setNotification(null);
    }, duration);
    
    setTimeoutId(id);
  }, [timeoutId]);

  const hideNotification = useCallback(() => {
    setNotification(null);
    if (timeoutId) clearTimeout(timeoutId);
  }, [timeoutId]);

  return (
    <NotificationContext.Provider
      value={{
        notification,
        showNotification,
        hideNotification
      }}
    >
      {children}
    </NotificationContext.Provider>
  );
};

export const useNotification = () => {
  const context = useContext(NotificationContext);
  if (!context) {
    throw new Error('useNotification must be used within a NotificationProvider');
  }
  return context;
};