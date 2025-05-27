import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { usersApi } from '../../api/users';
import { useAuth } from '../../context/AuthContext';
import PostFeed from '../../components/posts/PostFeed';
import Button from '../../components/ui/Button';
import Loader from '../../components/ui/Loader';
import styles from './Profile.module.css';

const Profile = () => {
  const { id } = useParams();
  const { user: currentUser } = useAuth();
  const [profileUser, setProfileUser] = useState(null);
  const [posts, setPosts] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const navigate = useNavigate();

  const isCurrentUser = currentUser?.id === id;

  useEffect(() => {
    const fetchData = async () => {
      try {
        setIsLoading(true);
        const [userData, userPosts] = await Promise.all([
          usersApi.getById(id),
          usersApi.getPosts(id)
        ]);
        setProfileUser(userData);
        setPosts(userPosts);
      } catch (error) {
        console.error('Failed to fetch profile:', error);
        navigate('/');
      } finally {
        setIsLoading(false);
      }
    };

    fetchData();
  }, [id, navigate]);

  if (isLoading) return <Loader />;
  if (!profileUser) return null;

  return (
    <div className={styles.profile}>
      <div className={styles.header}>
        <div className={styles.avatarContainer}>
          <img 
            src={profileUser.avatar || '/default-avatar.png'} 
            alt={profileUser.name}
            className={styles.avatar}
          />
        </div>
        
        <div className={styles.info}>
          <h1 className={styles.name}>{profileUser.name}</h1>
          <p className={styles.bio}>{profileUser.bio || 'Нет информации'}</p>
          
          {isCurrentUser && (
            <Button 
              onClick={() => navigate('/profile/edit')}
              variant="outlined"
            >
              Редактировать профиль
            </Button>
          )}
        </div>
      </div>

      <div className={styles.content}>
        <h2>Публикации</h2>
        {posts.length > 0 ? (
          <PostFeed posts={posts} />
        ) : (
          <p className={styles.noPosts}>
            {isCurrentUser ? 'У вас пока нет публикаций' : 'У пользователя нет публикаций'}
          </p>
        )}
      </div>
    </div>
  );
};

export default Profile;