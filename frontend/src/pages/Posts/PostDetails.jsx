import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';
import { useNotification } from '../../context/NotificationContext';
import { postsApi } from '../../api/posts';
import PostCard from '../../components/posts/PostCard';
// import CommentList from '../../components/comments/CommentList';
// import CommentForm from '../../components/comments/CommentForm';
import Button from '../../components/ui/Button';
import Loader from '../../components/ui/Loader';
import styles from './PostDetails.module.css';

const PostDetails = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const { user, isAuthenticated } = useAuth();
  const { showNotification } = useNotification();
  
  const [post, setPost] = useState(null);
//   const [comments, setComments] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [isDeleting, setIsDeleting] = useState(false);
  const [error, setError] = useState(null);

  // Загрузка поста и комментариев
  useEffect(() => {
    const fetchPostData = async () => {
      try {
        setIsLoading(true);
        const [postData, commentsData] = await Promise.all([
          postsApi.getById(id),
//           postsApi.getComments(id)
        ]);
        setPost(postData);
//         setComments(commentsData);
      } catch (err) {
        setError('Не удалось загрузить пост');
        console.error(err);
      } finally {
        setIsLoading(false);
      }
    };

    fetchPostData();
  }, [id]);

  // Обработчик лайка поста
  const handleLike = async () => {
    if (!isAuthenticated) {
      showNotification('Войдите в аккаунт', 'Чтобы ставить лайки', 'info');
      return;
    }

    try {
      const updatedPost = await postsApi.toggleLike(post.id);
      setPost(updatedPost);
    } catch (error) {
      showNotification('Ошибка', 'Не удалось поставить лайк', 'error');
    }
  };

  // Обработчик добавления комментария
  const handleAddComment = async (commentText) => {
    if (!isAuthenticated) {
      showNotification('Войдите в аккаунт', 'Чтобы оставлять комментарии', 'info');
      return;
    }

    try {
      const newComment = await postsApi.addComment(post.id, {
        text: commentText,
        authorId: user.id
      });
      setComments(prev => [newComment, ...prev]);
    } catch (error) {
      showNotification('Ошибка', 'Не удалось добавить комментарий', 'error');
    }
  };

  // Обработчик удаления комментария
  const handleDeleteComment = async (commentId) => {
    try {
      await postsApi.deleteComment(post.id, commentId);
      setComments(prev => prev.filter(c => c.id !== commentId));
      showNotification('Комментарий удален', '', 'success');
    } catch (error) {
      showNotification('Ошибка', 'Не удалось удалить комментарий', 'error');
    }
  };

  // Обработчик удаления поста
  const handleDeletePost = async () => {
    if (!window.confirm('Вы уверены, что хотите удалить этот пост?')) return;

    try {
      setIsDeleting(true);
      await postsApi.delete(post.id);
      showNotification('Пост удален', '', 'success');
      navigate('/');
    } catch (error) {
      showNotification('Ошибка', 'Не удалось удалить пост', 'error');
    } finally {
      setIsDeleting(false);
    }
  };

  // Обработчик возврата назад
  const handleBack = () => {
    navigate(-1);
  };

  if (isLoading) return <Loader />;
  if (error) return <div className={styles.error}>{error}</div>;
  if (!post) return null;

  const isAuthor = isAuthenticated && user.id === post.author.id;

  return (
    <div className={styles.container}>
      <Button 
        onClick={handleBack}
        variant="text"
        className={styles.backButton}
      >
        ← Назад
      </Button>

      <div className={styles.postContainer}>
        <PostCard 
          post={post} 
          onLike={handleLike}
          showDetails={false}
        />

        {isAuthor && (
          <div className={styles.postActions}>
            <Button
              onClick={() => navigate(`/posts/${post.id}/edit`)}
              variant="outlined"
            >
              Редактировать
            </Button>
            <Button
              onClick={handleDeletePost}
              variant="danger"
              loading={isDeleting}
              disabled={isDeleting}
            >
              Удалить пост
            </Button>
          </div>
        )}
      </div>

      <div className={styles.commentsSection}>
        <h2 className={styles.commentsTitle}>Комментарии ({comments.length})</h2>
        
        {isAuthenticated && (
          <CommentForm 
            onSubmit={handleAddComment}
            className={styles.commentForm}
          />
        )}

        <CommentList 
          comments={comments} 
          currentUserId={user?.id}
          onDelete={handleDeleteComment}
        />
      </div>
    </div>
  );
};

export default PostDetails;