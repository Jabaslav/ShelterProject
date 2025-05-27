import { useState, useEffect } from 'react';
import { postsApi } from '../../api/posts';
import PostCard from './PostCard';
import PostCreate from './PostCreate';
import Loader from '../ui/Loader';
// import styles from './PostFeed.module.css';

const PostFeed = ({ showCreateForm = true }) => {
  const [posts, setPosts] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [page, setPage] = useState(1);
  const [hasMore, setHasMore] = useState(true);

  const fetchPosts = async (pageNum = 1) => {
    try {
      const data = await postsApi.getPosts(pageNum);
      setPosts(prev => pageNum === 1 ? data : [...prev, ...data]);
      setHasMore(data.length > 0);
    } catch (error) {
      console.error('Error fetching posts:', error);
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchPosts();
  }, []);

  const handleLoadMore = () => {
    const nextPage = page + 1;
    setPage(nextPage);
    fetchPosts(nextPage);
  };

  const handlePostCreated = (newPost) => {
    setPosts(prev => [newPost, ...prev]);
  };

  if (isLoading && page === 1) {
    return <Loader />;
  }

  return (
    <div className={styles.postFeed}>
      {showCreateForm && (
        <PostCreate onPostCreated={handlePostCreated} />
      )}

      <div className={styles.postsList}>
        {posts.length > 0 ? (
          posts.map(post => (
            <PostCard 
              key={post.id} 
              post={post} 
              onUpdate={() => fetchPosts(1)} // Обновляем при изменении
            />
          ))
        ) : (
          <p className={styles.noPosts}>Пока нет постов</p>
        )}
      </div>

      {hasMore && posts.length > 0 && (
        <div className={styles.loadMore}>
          <Button 
            onClick={handleLoadMore}
            variant="secondary"
            disabled={isLoading}
          >
            {isLoading ? 'Загрузка...' : 'Показать еще'}
          </Button>
        </div>
      )}
    </div>
  );
};

export default PostFeed;