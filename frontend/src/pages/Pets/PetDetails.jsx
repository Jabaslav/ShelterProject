import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { petsApi } from '../../api/pets';
import PetModal from '../../components/pets/PetModal';
import Loader from '../../components/ui/Loader';
import Button from '../../components/ui/Button';
// import styles from './PetDetails.module.css';

const PetDetails = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [pet, setPet] = useState(null);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchPet = async () => {
      try {
        setIsLoading(true);
        const petData = await petsApi.getById(id);
        setPet(petData);
      } catch (err) {
        setError('Не удалось загрузить информацию о питомце');
        console.error(err);
      } finally {
        setIsLoading(false);
      }
    };

    fetchPet();
  }, [id]);

  const handleBack = () => {
    navigate(-1);
  };

  if (isLoading) return <Loader />;
  if (error) return <div className={styles.error}>{error}</div>;
  if (!pet) return null;

  return (
    <div className={styles.container}>
      <Button 
        onClick={handleBack}
        variant="text"
        className={styles.backButton}
      >
        ← Назад
      </Button>
      
      <PetModal 
        pet={pet} 
        onClose={handleBack}
        showCloseButton={false}
      />
    </div>
  );
};

export default PetDetails;