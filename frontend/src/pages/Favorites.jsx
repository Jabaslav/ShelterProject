import { useState, useEffect } from 'react';
import { petsApi } from '../api/pets.js';
import PetList from '../components/pets/PetList';
import Loader from '../components/ui/Loader';
// import styles from './Favorites.module.css';

const Favorites = () => {
  const [favorites, setFavorites] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const fetchFavorites = async () => {
      try {
        setIsLoading(true);
        const data = await petsApi.getFavorites();
        setFavorites(data);
      } catch (error) {
        console.error('Failed to fetch favorites:', error);
      } finally {
        setIsLoading(false);
      }
    };

    fetchFavorites();
  }, []);

  if (isLoading) return <Loader />;

  return (
    <div className={styles.favorites}>
      <h1>Избранные питомцы</h1>
      {favorites.length > 0 ? (
        <PetList pets={favorites} />
      ) : (
        <p className={styles.empty}>У вас пока нет избранных питомцев</p>
      )}
    </div>
  );
};

export default Favorites;