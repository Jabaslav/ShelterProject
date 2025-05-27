import { useState } from 'react';
import { postsApi } from '../../api/posts';
import Button from '../ui/Button';
import styles from './PostCard.module.css';
// import PetTag from '../pets/PetTag'; // Компонент для отображения привязанного животного
// import CommentSection from './CommentSection'; // Компонент комментариев (реализуем ниже)

const PostCard = ({ post, onUpdate }) => {
  const [isLiked, setIsLiked] = useState(post.isLiked);
  const [isBookmarked, setIsBookmarked] = useState(post.isBookmarked);
  const [showComments, setShowComments] = useState(false);

  const handleLike = async () => {
    try {
      if (isLiked) {
        await postsApi.unlikePost(post.id);
      } else {
        await postsApi.likePost(post.id);
      }
      setIsLiked(!isLiked);
      onUpdate?.(); // Обновляем список постов при необходимости
    } catch (error) {
      console.error('Like error:', error);
    }
  };

  const handleBookmark = async () => {
    try {
      if (isBookmarked) {
        await postsApi.removeBookmark(post.id);
      } else {
        await postsApi.addBookmark(post.id);
      }
      setIsBookmarked(!isBookmarked);
    } catch (error) {
      console.error('Bookmark error:', error);
    }
  };

  return (
    <div className={styles.postCard}>
      <div className={styles.postHeader}>
        <div className={styles.authorInfo}>
          <img 
            src={post.author.avatar || '/default-avatar.png'} 
            alt={post.author.name}
            className={styles.avatar}
          />
          <span className={styles.authorName}>{post.author.name}</span>
          <span className={styles.postDate}>
            {new Date(post.createdAt).toLocaleDateString()}
          </span>
        </div>
        
{/*         {post.relatedPet && ( */}
{/*           <PetTag pet={post.relatedPet} /> */}
{/*         )} */}
      </div>

      <div className={styles.postContent}>
        <p className={styles.postText}>{post.text}</p>
        
        {post.image && (
          <img 
            src={post.image} 
            alt="Post content" 
            className={styles.postImage}
            loading="lazy"
          />
        )}
      </div>

      <div className={styles.postStats}>
        <span>{post.likesCount} лайков</span>
        <span>{post.commentsCount} комментариев</span>
      </div>

      <div className={styles.postActions}>
        <Button 
          variant="icon" 
          onClick={handleLike}
          aria-label={isLiked ? 'Убрать лайк' : 'Поставить лайк'}
        >
          {isLiked ? '❤️' : '🤍'}
        </Button>
        
        <Button 
          variant="icon"
          onClick={() => setShowComments(!showComments)}
          aria-label="Показать комментарии"
        >
          💬
        </Button>
        
        <Button 
          variant="icon" 
          onClick={handleBookmark}
          aria-label={isBookmarked ? 'Удалить из закладок' : 'Добавить в закладки'}
        >
          {isBookmarked ? '🔖' : '📌'}
        </Button>
      </div>

      {showComments && (
        <CommentSection postId={post.id} />
      )}
    </div>
  );
};

export default PostCard;