import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { petsApi } from '../../api/pets';
import PetCard from '../../components/pets/PetCard';
import Loader from '../../components/ui/Loader';
import Button from '../../components/ui/Button';
import styles from './Pets.module.css';

const PetList = () => {
  const [pets, setPets] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [page, setPage] = useState(1);
  const [hasMore, setHasMore] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchPets = async () => {
      try {
        setIsLoading(true);
        const data = await petsApi.getAll(page);
        setPets(prev => page === 1 ? data : [...prev, ...data]);
        setHasMore(data.length > 0);
      } catch (error) {
        console.error('Failed to fetch pets:', error);
      } finally {
        setIsLoading(false);
      }
    };

    fetchPets();
  }, [page]);

  const handlePetClick = (petId) => {
    navigate(`/pets/${petId}`);
  };

  const loadMore = () => {
    setPage(prev => prev + 1);
  };

  return (
    <div className={styles.petList}>
      <div className={styles.grid}>
        {pets.map(pet => (
          <PetCard 
            key={pet.id} 
            pet={pet} 
            onClick={() => handlePetClick(pet.id)}
          />
        ))}
      </div>

      {isLoading && <Loader />}

      {hasMore && !isLoading && (
        <div className={styles.loadMore}>
          <Button onClick={loadMore} variant='outline'>
            Показать еще
          </Button>
        </div>
      )}
    </div>
  );
}; // <-- Эта закрывающая скобка была пропущена
export default PetList;