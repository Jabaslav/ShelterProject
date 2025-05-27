// import { useState, useEffect } from 'react';
// import { sheltersApi } from '../../api';
// import ShelterCard from '../../components/shelters/ShelterCard';
// import Loader from '../../components/ui/Loader';
// import Input from '../../components/ui/Input';
// import styles from './Shelters.module.css';
//
// const ShelterList = () => {
//   const [shelters, setShelters] = useState([]);
//   const [filteredShelters, setFilteredShelters] = useState([]);
//   const [searchTerm, setSearchTerm] = useState('');
//   const [isLoading, setIsLoading] = useState(true);
//
//   useEffect(() => {
//     const fetchShelters = async () => {
//       try {
//         const data = await sheltersApi.getAll();
//         setShelters(data);
//         setFilteredShelters(data);
//       } catch (error) {
//         console.error('Failed to fetch shelters:', error);
//       } finally {
//         setIsLoading(false);
//       }
//     };
//
//     fetchShelters();
//   }, []);
//
//   useEffect(() => {
//     const filtered = shelters.filter(shelter =>
//       shelter.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
//       shelter.address.toLowerCase().includes(searchTerm.toLowerCase())
//     );
//     setFilteredShelters(filtered);
//   }, [searchTerm, shelters]);
//
//   if (isLoading) return <Loader />;
//
//   return (
//     <div className={styles.shelterList}>
//       <div className={styles.search}>
//         <Input
//           type="text"
//           placeholder="Поиск приютов..."
//           value={searchTerm}
//           onChange={(e) => setSearchTerm(e.target.value)}
//         />
//       </div>
//
//       <div className={styles.grid}>
//         {filteredShelters.map(shelter => (
//           <ShelterCard key={shelter.id} shelter={shelter} />
//         ))}
//       </div>
//
//       {filteredShelters.length === 0 && (
//         <p className={styles.noResults}>Приюты не найдены</p>
//       )}
//     </div>
//   );
// };
//
// export default ShelterList;