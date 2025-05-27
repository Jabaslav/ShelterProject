import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { postsApi } from '../../api/posts';
import { useNotification } from '../../context/NotificationContext';
import Input from '../../components/ui/Input';
import Button from '../../components/ui/Button';
// import styles from './PostCreate.module.css';

const PostCreate = () => {
  const [text, setText] = useState('');
  const [image, setImage] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const { showNotification } = useNotification();
  const navigate = useNavigate();

  const handleImageChange = (e) => {
    if (e.target.files && e.target.files[0]) {
      setImage(e.target.files[0]);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!text.trim() && !image) return;

    setIsLoading(true);
    try {
      const formData = new FormData();
      formData.append('text', text);
      if (image) formData.append('image', image);

      await postsApi.create(formData);
      showNotification('Пост опубликован', '', 'success');
      navigate('/');
    } catch (error) {
      showNotification('Ошибка', 'Не удалось создать пост', 'error');
      console.error(error);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className={styles.container}>
      <h1>Создать публикацию</h1>
      <form onSubmit={handleSubmit} className={styles.form}>
        <Input
          as="textarea"
          value={text}
          onChange={(e) => setText(e.target.value)}
          placeholder="Расскажите что-нибудь..."
          rows={5}
          className={styles.textarea}
        />
        
        <div className={styles.imageUpload}>
          <label>
            <input 
              type="file" 
              onChange={handleImageChange} 
              accept="image/*"
              className={styles.fileInput}
            />
            <Button type="button" variant="outlined">
              {image ? 'Изменить фото' : 'Добавить фото'}
            </Button>
          </label>
          {image && (
            <div className={styles.imagePreview}>
              <img 
                src={URL.createObjectURL(image)} 
                alt="Предпросмотр" 
                className={styles.previewImage}
              />
              <Button 
                type="button" 
                variant="text"
                onClick={() => setImage(null)}
                className={styles.removeImage}
              >
                Удалить
              </Button>
            </div>
          )}
        </div>

        <div className={styles.actions}>
          <Button 
            type="submit" 
            loading={isLoading}
            disabled={!text.trim() && !image}
          >
            Опубликовать
          </Button>
        </div>
      </form>
    </div>
  );
};

export default PostCreate;