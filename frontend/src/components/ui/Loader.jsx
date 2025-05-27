import PropTypes from 'prop-types';
import styles from './Loader.module.css';

const Loader = ({ size = 'medium', color = 'primary', fullPage = false }) => {
  const sizeClass = {
    small: styles.small,
    medium: styles.medium,
    large: styles.large,
  }[size];

  const colorClass = {
    primary: styles.primary,
    secondary: styles.secondary,
    white: styles.white,
  }[color];

  if (fullPage) {
    return (
      <div className={styles.fullPageOverlay}>
        <div className={`${styles.loader} ${sizeClass} ${colorClass}`} />
      </div>
    );
  }

  return <div className={`${styles.loader} ${sizeClass} ${colorClass}`} />;
};

Loader.propTypes = {
  size: PropTypes.oneOf(['small', 'medium', 'large']),
  color: PropTypes.oneOf(['primary', 'secondary', 'white']),
  fullPage: PropTypes.bool,
};

export default Loader;