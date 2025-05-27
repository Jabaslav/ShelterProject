import api from './config';

export const chatsApi = {
  // Получить все чаты пользователя (GET /api/chats)
  getAll: async () => {
    const response = await api.get('/chats');
    return response.data;
  },

  // Отправить сообщение (POST /api/chats/{chat_id}/messages)
  sendMessage: async (chatId, message, image) => {
    const formData = new FormData();
    formData.append('messageDto', JSON.stringify(message));
    if (image) formData.append('image', image);

    const response = await api.post(`/chats/${chatId}/messages`, formData, {
      headers: { 'Content-Type': 'multipart/form-data' },
    });
    return response.data;
  },
};