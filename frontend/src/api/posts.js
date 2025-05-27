import api from './config';

export const postsApi = {
  // Создать пост (POST /api/posts)
  create: async (postData, image) => {
    const formData = new FormData();
    formData.append('postDto', JSON.stringify(postData));
    if (image) formData.append('image', image);

    const response = await api.post('/posts', formData, {
      headers: { 'Content-Type': 'multipart/form-data' },
    });
    return response.data;
  },

  // Лайкнуть пост (этот эндпоинт нужно добавить в твой бэкенд)
  likePost: async (postId) => {
    const response = await api.post(`/posts/${postId}/like`);
    return response.data;
  },
};