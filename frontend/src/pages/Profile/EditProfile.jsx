import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';
import { useNotification } from '../../context/NotificationContext';
import { usersApi } from '../../api/users';
import Input from '../../components/ui/Input';
import Button from '../../components/ui/Button';
import Loader from '../../components/ui/Loader';
// import AvatarUpload from '../../components/profile/AvatarUpload';
// import styles from './EditProfile.module.css';

const EditProfile = () => {
  const { user, updateUser } = useAuth();
  const { showNotification } = useNotification();
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    name: '',
    email: '',
    bio: '',
    phone: '',
    address: ''
  });
  const [avatar, setAvatar] = useState(null);
  const [isLoading, setIsLoading] = useState(true);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [errors, setErrors] = useState({});

  // Загрузка данных пользователя
  useEffect(() => {
    const fetchUserData = async () => {
      try {
        if (user) {
          const userData = await usersApi.getMe();
          setFormData({
            name: userData.name || '',
            email: userData.email || '',
            bio: userData.bio || '',
            phone: userData.phone || '',
            address: userData.address || ''
          });
        }
      } catch (error) {
        showNotification('Ошибка', 'Не удалось загрузить данные профиля', 'error');
        console.error(error);
      } finally {
        setIsLoading(false);
      }
    };

    fetchUserData();
  }, [user, showNotification]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
    // Очищаем ошибку при изменении поля
    if (errors[name]) {
      setErrors(prev => ({ ...prev, [name]: '' }));
    }
  };

//   const handleAvatarChange = (file) => {
//     setAvatar(file);
//   };

  const validate = () => {
    const newErrors = {};
    if (!formData.name.trim()) newErrors.name = 'Введите имя';
    if (!formData.email.trim()) newErrors.email = 'Введите email';
    else if (!/^\S+@\S+\.\S+$/.test(formData.email)) newErrors.email = 'Некорректный email';

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validate()) return;

    setIsSubmitting(true);
    try {
      const updatedData = { ...formData };
      const formDataToSend = new FormData();

      // Добавляем текстовые данные
      Object.keys(updatedData).forEach(key => {
        formDataToSend.append(key, updatedData[key]);
      });

//       // Добавляем аватар, если он был изменен
//       if (avatar) {
//         formDataToSend.append('avatar', avatar);
//       }

      const updatedUser = await usersApi.updateProfile(formDataToSend);
      await updateUser(updatedUser);

      showNotification('Успех', 'Профиль обновлен', 'success');
      navigate('/profile');
    } catch (error) {
      console.error('Update failed:', error);
      showNotification('Ошибка', 'Не удалось обновить профиль', 'error');
    } finally {
      setIsSubmitting(false);
    }
  };

  if (isLoading) return <Loader />;

  return (
    <div className={styles.container}>
      <h1 className={styles.title}>Редактирование профиля</h1>

      <form onSubmit={handleSubmit} className={styles.form}></form>
        <div className></div>
    </div>
  );

}
