import { useState, useEffect } from 'react';
import { petsApi } from '../../api/pets';
import Button from '../ui/Button';
import Loader from '../ui/Loader';
// import styles from './PetModal.module.css'; // Предполагается использование CSS-модулей

const PetModal = ({ petId, onClose }) => {
  const [pet, setPet] = useState(null);
  const [loading, setLoading] = useState(true);
  const [isLiked, setIsLiked] = useState(false);
  const [isSaved, setIsSaved] = useState(false);
  const [currentImageIndex, setCurrentImageIndex] = useState(0);

  // Загрузка данных о животном
  useEffect(() => {
    const fetchPet = async () => {
      try {
        const petData = await petsApi.getById(petId);
        setPet(petData);
        
        // Проверяем, лайкнул ли пользователь это животное
        // (нужно реализовать соответствующий эндпоинт в API)
        const likedStatus = await petsApi.checkLikeStatus(petId);
        setIsLiked(likedStatus.isLiked);
        setIsSaved(likedStatus.isSaved);
      } catch (error) {
        console.error('Ошибка загрузки данных:', error);
      } finally {
        setLoading(false);
      }
    };
    
    fetchPet();
  }, [petId]);

  const handleLike = async () => {
    try {
      if (isLiked) {
        await petsApi.unlikePet(petId);
      } else {
        await petsApi.likePet(petId);
      }
      setIsLiked(!isLiked);
      setPet(prev => ({ ...prev, likes: isLiked ? prev.likes - 1 : prev.likes + 1 }));
    } catch (error) {
      console.error('Ошибка:', error);
    }
  };

  const handleSave = async () => {
    try {
      if (isSaved) {
        await petsApi.removeFromFavorites(petId);
      } else {
        await petsApi.addToFavorites(petId);
      }
      setIsSaved(!isSaved);
    } catch (error) {
      console.error('Ошибка:', error);
    }
  };

  const nextImage = () => {
    setCurrentImageIndex(prev => 
      prev === pet.photos.length - 1 ? 0 : prev + 1
    );
  };

  const prevImage = () => {
    setCurrentImageIndex(prev => 
      prev === 0 ? pet.photos.length - 1 : prev - 1
    );
  };

  if (loading) return (
    <div className={styles.modalOverlay}>
      <div className={styles.modalContent}>
        <Loader />
      </div>
    </div>
  );

  if (!pet) return (
    <div className={styles.modalOverlay}>
      <div className={styles.modalContent}>
        <p>Не удалось загрузить информацию о животном</p>
        <Button onClick={onClose}>Закрыть</Button>
      </div>
    </div>
  );

  return (
    <div className={styles.modalOverlay} onClick={onClose}>
      <div className={styles.modalContent} onClick={e => e.stopPropagation()}>
        <button className={styles.closeButton} onClick={onClose}>&times;</button>
        
        <div className={styles.imageGallery}>
          {pet.photos.length > 1 && (
            <>
              <button className={styles.navButtonLeft} onClick={prevImage}>&lt;</button>
              <button className={styles.navButtonRight} onClick={nextImage}>&gt;</button>
            </>
          )}
          <img 
            src={pet.photos[currentImageIndex]} 
            alt={pet.name} 
            className={styles.mainImage}
          />
          <div className={styles.imageDots}>
            {pet.photos.map((_, index) => (
              <span 
                key={index} 
                className={`${styles.dot} ${index === currentImageIndex ? styles.activeDot : ''}`}
                onClick={() => setCurrentImageIndex(index)}
              />
            ))}
          </div>
        </div>

        <div className={styles.petInfo}>
          <h2>{pet.name}</h2>
          <p className={styles.shelterInfo}>
            Приют: <span>{pet.shelterName}</span>
          </p>
          <p className={styles.breed}>Порода: {pet.breed}</p>
          <p className={styles.age}>Возраст: {pet.age} лет</p>
          <p className={styles.description}>{pet.description}</p>
          
          <div className={styles.stats}>
            <span>Просмотры: {pet.views}</span>
            <span>Лайки: {pet.likes}</span>
          </div>

          <div className={styles.actionButtons}>
            <Button 
              onClick={handleLike}
              className={`${styles.likeButton} ${isLiked ? styles.liked : ''}`}
            >
              {isLiked ? '❤️' : '🤍'} Лайк
            </Button>
            <Button 
              onClick={handleSave}
              className={`${styles.saveButton} ${isSaved ? styles.saved : ''}`}
            >
              {isSaved ? '✓' : '+'} Избранное
            </Button>
          </div>

          {pet.contactInfo && (
            <div className={styles.contacts}>
              <h3>Контакты для связи:</h3>
              <p>{pet.contactInfo}</p>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default PetModal;