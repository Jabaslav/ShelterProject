// import { useState, useEffect } from 'react';
// import { useParams, useNavigate } from 'react-router-dom';
// import { useAuth } from '../../context/AuthContext';
// import { useNotification } from '../../context/NotificationContext';
// import { petsApi } from '../../api/pets.js';
// import PetList from '../../components/pets/PetList';
// import Button from '../../components/ui/Button';
// import Loader from '../../components/ui/Loader';
// import ShelterInfoCard from '../../components/shelters/ShelterInfoCard';
// import ContactButton from '../../components/shelters/ContactButton';
// import styles from './ShelterProfile.module.css';
//
// const ShelterProfile = () => {
//   const { id } = useParams();
//   const navigate = useNavigate();
//   const { user, isAuthenticated } = useAuth();
//   const { showNotification } = useNotification();
//
//   const [shelter, setShelter] = useState(null);
//   const [pets, setPets] = useState([]);
//   const [isLoading, setIsLoading] = useState(true);
//   const [isPetsLoading, setIsPetsLoading] = useState(false);
//   const [isFollowing, setIsFollowing] = useState(false);
//   const [error, setError] = useState(null);
//
//   // Загрузка данных приюта
//   useEffect(() => {
//     const fetchShelterData = async () => {
//       try {
//         setIsLoading(true);
//         const shelterData = await sheltersApi.getById(id);
//         setShelter(shelterData);
//
//         // Проверяем, подписан ли пользователь
//         if (isAuthenticated) {
//           const followStatus = await sheltersApi.checkFollowStatus(id);
//           setIsFollowing(followStatus.isFollowing);
//         }
//       } catch (err) {
//         setError('Не удалось загрузить информацию о приюте');
//         console.error(err);
//       } finally {
//         setIsLoading(false);
//       }
//     };
//
//     fetchShelterData();
//   }, [id, isAuthenticated]);
//
//   // Загрузка животных приюта
//   useEffect(() => {
//     const fetchPets = async () => {
//       try {
//         setIsPetsLoading(true);
//         const petsData = await petsApi.getByShelter(id);
//         setPets(petsData);
//       } catch (err) {
//         showNotification('Ошибка', 'Не удалось загрузить список животных', 'error');
//       } finally {
//         setIsPetsLoading(false);
//       }
//     };
//
//     if (shelter) {
//       fetchPets();
//     }
//   }, [shelter, id, showNotification]);
//
//   // Обработчик подписки/отписки
//   const handleFollow = async () => {
//     if (!isAuthenticated) {
//       showNotification('Войдите в аккаунт', 'Чтобы подписаться на приют', 'info');
//       return;
//     }
//
//     try {
//       if (isFollowing) {
//         await sheltersApi.unfollow(id);
//         showNotification('Вы отписались', `От ${shelter.name}`, 'success');
//       } else {
//         await sheltersApi.follow(id);
//         showNotification('Вы подписались', `На ${shelter.name}`, 'success');
//       }
//       setIsFollowing(!isFollowing);
//     } catch (error) {
//       showNotification('Ошибка', 'Не удалось выполнить действие', 'error');
//     }
//   };
//
//   // Обработчик добавления животного (для администраторов приюта)
//   const handleAddPet = () => {
//     navigate('/pets/new', { state: { shelterId: id } });
//   };
//
//   if (isLoading) return <Loader />;
//   if (error) return <div className={styles.error}>{error}</div>;
//   if (!shelter) return null;
//
//   const isShelterAdmin = isAuthenticated && user?.role === 'SHELTER_ADMIN' && user?.shelterId === shelter.id;
//
//   return (
//     <div className={styles.container}>
//       <div className={styles.header}>
//         <Button
//           onClick={() => navigate(-1)}
//           variant="text"
//           className={styles.backButton}
//         >
//           ← Назад к списку приютов
//         </Button>
//
//         <div className={styles.coverImage}>
//           {shelter.coverImage && (
//             <img
//               src={shelter.coverImage}
//               alt={`Обложка ${shelter.name}`}
//               className={styles.coverImg}
//             />
//           )}
//         </div>
//       </div>
//
//       <div className={styles.profileContent}>
//         <div className={styles.sidebar}>
//           <ShelterInfoCard
//             shelter={shelter}
//             className={styles.infoCard}
//           />
//
//           <div className={styles.actions}>
//             <ContactButton
//               shelter={shelter}
//               className={styles.contactButton}
//             />
//
//             <Button
//               onClick={handleFollow}
//               variant={isFollowing ? 'outlined' : 'primary'}
//               fullWidth
//             >
//               {isFollowing ? 'Отписаться' : 'Подписаться'}
//             </Button>
//
//             {isShelterAdmin && (
//               <Button
//                 onClick={handleAddPet}
//                 variant="primary"
//                 fullWidth
//                 className={styles.addPetButton}
//               >
//                 Добавить животное
//               </Button>
//             )}
//           </div>
//         </div>
//
//         <div className={styles.mainContent}>
//           <section className={styles.section}>
//             <h2 className={styles.sectionTitle}>О приюте</h2>
//             <div className={styles.description}>
//               {shelter.description || 'Приют пока не добавил описание.'}
//             </div>
//           </section>
//
//           <section className={styles.section}>
//             <div className={styles.sectionHeader}>
//               <h2 className={styles.sectionTitle}>Наши питомцы</h2>
//               {pets.length > 0 && (
//                 <span className={styles.petsCount}>{pets.length} животных</span>
//               )}
//             </div>
//
//             {isPetsLoading ? (
//               <Loader />
//             ) : pets.length > 0 ? (
//               <PetList
//                 pets={pets}
//                 className={styles.petList}
//               />
//             ) : (
//               <div className={styles.noPets}>
//                 <p>В этом приюте пока нет животных</p>
//                 {isShelterAdmin && (
//                   <Button
//                     onClick={handleAddPet}
//                     variant="primary"
//                   >
//                     Добавить первое животное
//                   </Button>
//                 )}
//               </div>
//             )}
//           </section>
//         </div>
//       </div>
//     </div>
//   );
// };
//
// export default ShelterProfile;