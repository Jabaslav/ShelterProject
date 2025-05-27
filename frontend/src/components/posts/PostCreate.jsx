import { useState, useRef } from 'react';
import { postsApi } from '../../api/posts';
import Button from '../ui/Button';
// import styles from './PostCreate.module.css';

const PostCreate = ({ onPostCreated }) => {
  const [text, setText] = useState('');
  const [image, setImage] = useState(null);
  const [selectedPet, setSelectedPet] = useState(null);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const fileInputRef = useRef(null);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!text.trim() && !image) return;

    setIsSubmitting(true);
    try {
      const postData = {
        text,
        petId: selectedPet?.id || null
      };

      const createdPost = await postsApi.createPost(postData, image);
      onPostCreated?.(createdPost);
      
      // Сброс формы
      setText('');
      setImage(null);
      setSelectedPet(null);
      if (fileInputRef.current) {
        fileInputRef.current.value = '';
      }
    } catch (error) {
      console.error('Post creation error:', error);
    } finally {
      setIsSubmitting(false);
    }
  };

  const handleImageChange = (e) => {
    if (e.target.files && e.target.files[0]) {
      setImage(e.target.files[0]);
    }
  };

  return (
    <form onSubmit={handleSubmit} className={styles.postForm}>
      <div className={styles.formGroup}>
        <textarea
          value={text}
          onChange={(e) => setText(e.target.value)}
          placeholder="Расскажите что-нибудь..."
          className={styles.postInput}
          rows={3}
        />
      </div>

      <div className={styles.formRow}>
        <div className={styles.fileInputWrapper}>
          <Button 
            type="button" 
            variant="secondary"
            onClick={() => fileInputRef.current?.click()}
          >
            📷 Добавить фото
          </Button>
          <input
            type="file"
            ref={fileInputRef}
            onChange={handleImageChange}
            accept="image/*"
            className={styles.fileInput}
          />
          {image && (
            <span className={styles.fileName}>
              {image.name}
              <button 
                type="button" 
                onClick={() => setImage(null)}
                className={styles.removeFile}
              >
                ×
              </button>
            </span>
          )}
        </div>

        {/* Здесь можно добавить выбор животного из списка */}
        {/* <PetSelector onSelect={setSelectedPet} selectedPet={selectedPet} /> */}
      </div>

      <div className={styles.submitButton}>
        <Button 
          type="submit" 
          disabled={isSubmitting || (!text.trim() && !image)}
        >
          {isSubmitting ? 'Публикация...' : 'Опубликовать'}
        </Button>
      </div>
    </form>
  );
};

export default PostCreate;