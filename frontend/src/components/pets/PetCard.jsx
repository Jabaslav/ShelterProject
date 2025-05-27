import { useState } from 'react';
import { petsApi } from '../../api/pets';
import Button from '../ui/Button';

const PetCard = ({ pet, onUpdate }) => {
  const [isLiked, setIsLiked] = useState(false);

  const handleLike = async () =>
  {
    try {
      await petsApi.like(pet.id); // Эндпоинт нужно добавить в бэкенд
      setIsLiked(!isLiked);
      onUpdate?.();
    } catch (error) {
      alert('Ошибка лайка!');
    }
  };

  return (
    <div className="pet-card">
      <img src={pet.photos[0]} alt={pet.name} />
      <h3>{pet.name}</h3>
      <Button onClick={handleLike}>{isLiked ? '❤️' : '🤍'} {pet.likes}</Button>
    </div>
  );
};
export default PetCard;
