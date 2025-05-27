import { useEffect, useState } from 'react';
import PostFeed from '../components/posts/PostFeed';
import PetList from '../components/pets/PetList';
import { postsApi } from '../api/posts';
// import { petsApi } from '../api/pets';
import Loader from '../components/ui/Loader';
import styles from './Home.module.css';

const Home = () => {
//   const [featuredPets, setFeaturedPets] = useState([]);
  const [latestPosts, setLatestPosts] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [postsResponse] = await Promise.all([
//           petsApi.getFeatured(),
//           postsApi.getLatest()
        ]);
//         setFeaturedPets(petsResponse);
//         setLatestPosts(postsResponse);
      } catch (error) {
        console.error('Failed to fetch data:', error);
      } finally {
        setIsLoading(false);
      }
    };

    fetchData();
  }, []);

  if (isLoading) return <Loader />;

  return (
    <div className={styles.home}>
      <section className={styles.section}>
        <h2>Питомцы, ищущие дом</h2>
{/*         <PetList pets={featuredPets} /> */}
      </section>
      
      <section className={styles.section}>
        <h2>Последние публикации</h2>
{/*         <PostFeed posts={latestPosts} /> */}
      </section>
    </div>
  );
};

export default Home;