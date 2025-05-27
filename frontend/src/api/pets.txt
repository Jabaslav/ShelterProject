import api from './config';

export const petsApi = {
  // Получить всех животных (GET /api/pets)
  getAll: async () => {
    const response = await api.get('/pets');
    return response.data;
  },

  // Создать карточку животного (POST /api/pets)
  create: async (petData, image) => {
    const formData = new FormData();
    formData.append('petDto', JSON.stringify(petData));
    if (image) formData.append('image', image);

    const response = await api.post('/pets', formData, {
      headers: { 'Content-Type': 'multipart/form-data' },
    });
    return response.data;
  },

  // Получить животное по ID (GET /api/pets/{id})
  getById: async (id) => {
    const response = await api.get(`/pets/${id}`);
    return response.data;
  },
};