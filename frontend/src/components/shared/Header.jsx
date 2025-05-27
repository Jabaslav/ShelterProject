import { Link, useLocation } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';
import Button from '../ui/Button';
import styles from './Header.module.css';

const Header = () => {
  const { isAuthenticated, user, logout } = useAuth();
  const location = useLocation();

  return (
    <header className={styles.header}>
      <div className={styles.logoContainer}>
        <Link to="/" className={styles.logo}>
          <img src="/logo.svg" alt="Pet Shelter" className={styles.logoImage} />
          <span>PetShelter</span>
        </Link>
      </div>

      <nav className={styles.nav}>
        <ul className={styles.navList}>
          <li className={styles.navItem}>
            <Link 
              to="/" 
              className={`${styles.navLink} ${location.pathname === '/' ? styles.active : ''}`}
            >
              Главная
            </Link>
          </li>
          <li className={styles.navItem}>
            <Link 
              to="/pets" 
              className={`${styles.navLink} ${location.pathname.startsWith('/pets') ? styles.active : ''}`}
            >
              Животные
            </Link>
          </li>
          <li className={styles.navItem}>
            <Link 
              to="/shelters" 
              className={`${styles.navLink} ${location.pathname.startsWith('/shelters') ? styles.active : ''}`}
            >
              Приюты
            </Link>
          </li>
        </ul>
      </nav>

      <div className={styles.authSection}>
        {isAuthenticated ? (
          <>
            <Link to="/profile" className={styles.profileLink}>
              <img 
                src={user?.avatar || '/default-avatar.png'} 
                alt={user?.name} 
                className={styles.avatar}
              />
              <span>{user?.name}</span>
            </Link>
            <Button 
              variant="text" 
              onClick={logout}
              className={styles.logoutButton}
            >
              Выйти
            </Button>
          </>
        ) : (
          <>
            <Button 
              variant="text" 
              as={Link} 
              to="/login" 
              state={{ from: location }}
              className={styles.authButton}
            >
              Войти
            </Button>
            <Button 
              as={Link} 
              to="/register" 
              state={{ from: location }}
              className={styles.authButton}
            >
              Регистрация
            </Button>
          </>
        )}
      </div>
    </header>
  );
};

export default Header;