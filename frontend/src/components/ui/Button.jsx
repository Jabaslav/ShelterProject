import { useNavigate} from 'react-router-dom'
import PropTypes from 'prop-types';
import styles from './Button.module.css';

const Button = ({
  children,
  to,
  onClick,
  type = 'button',
  variant = 'primary',
  size = 'medium',
  disabled = false,
  fullWidth = false,
  loading = false,
  icon,
  className = '',
  ...props
}) => {
  const variantClass = {
    primary: styles.primary,
    secondary: styles.secondary,
    text: styles.text,
    danger: styles.danger,
    icon: styles.icon,
  }[variant];

  const sizeClass = {
    small: styles.small,
    medium: styles.medium,
    large: styles.large,
  }[size];

  const navigate = useNavigate();

  const handleClick = (e) => {
      if (onClick)
      {
          onClick(e);
      }
      if (to)
      {
          navigate(to);
      }
  };

  return (
    <button
      type={type}
      onClick={handleClick}
      disabled={disabled || loading}
      className={`
        ${styles.button} 
        ${variantClass} 
        ${sizeClass} 
        ${fullWidth ? styles.fullWidth : ''}
        ${loading ? styles.loading : ''}
        ${className}
      `}
      {...props}
    >
      {loading ? (
        <span className={styles.spinner} aria-label="Загрузка" />
      ) : (
        <>
          {icon && <span className={styles.icon}>{icon}</span>}
          {children}
        </>
      )}
    </button>
  );
};

Button.propTypes = {
  children: PropTypes.node,
  onClick: PropTypes.func,
  type: PropTypes.oneOf(['button', 'submit', 'reset']),
  variant: PropTypes.oneOf(['primary', 'secondary', 'text', 'danger', 'icon']),
  size: PropTypes.oneOf(['small', 'medium', 'large']),
  disabled: PropTypes.bool,
  fullWidth: PropTypes.bool,
  loading: PropTypes.bool,
  icon: PropTypes.node,
  className: PropTypes.string,
};

export default Button;