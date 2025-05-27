import api from './config';

export const usersApi = {
  // Получить профиль пользователя (GET /api/users/{id})
  getProfile: async (userId) => {
    const response = await api.get(`/users/${userId}`);
    return response.data;
  },

  // Обновить аватар (PUT /api/users/{id}/update)
  updateAvatar: async (userId, image) => {
    const formData = new FormData();
    formData.append('image', image);

    const response = await api.put(`/users/${userId}/update`, formData, {
      headers: { 'Content-Type': 'multipart/form-data' },
    });
    return response.data;
  },
};