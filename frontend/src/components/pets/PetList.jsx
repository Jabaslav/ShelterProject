import { useEffect, useState } from 'react';
import { petsApi } from '../../api/pets';
import PetCard from './PetCard';
import Loader from '../ui/Loader';

const PetList = () => {
  const [pets, setPets] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchPets = async () => {
      try {
        const data = await petsApi.getAll();
        setPets(data);
      } finally {
        setLoading(false);
      }
    };
    fetchPets();
  }, []);

  if (loading) return <Loader />;

  return (
    <div className="pet-list">
      {pets.map((pet) => (
        <PetCard key={pet.id} pet={pet} />
      ))}
    </div>
  );
};

export default PetList;