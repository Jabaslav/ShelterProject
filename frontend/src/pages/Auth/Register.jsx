import { useState } from 'react';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';
import Input from '../../components/ui/Input';
import Button from '../../components/ui/Button';
import styles from './Auth.module.css';

const Register = () => {
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    password: '',
    confirmPassword: ''
  });
  const [isLoading, setIsLoading] = useState(false);
  const { register } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();

  const from = location.state?.from?.pathname || '/';

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (formData.password !== formData.confirmPassword) {
      alert('Пароли не совпадают');
      return;
    }

    setIsLoading(true);
    try {
      await register({
        name: formData.name,
        email: formData.email,
        password: formData.password
      });
      navigate(from, { replace: true });
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className={styles.authContainer}>
      <h1>Регистрация</h1>
      <form onSubmit={handleSubmit} className={styles.authForm}>
        <Input
          name="name"
          label="Имя"
          value={formData.name}
          onChange={handleChange}
          required
        />
        <Input
          type="email"
          name="email"
          label="Email"
          value={formData.email}
          onChange={handleChange}
          required
        />
        <Input
          type="password"
          name="password"
          label="Пароль"
          value={formData.password}
          onChange={handleChange}
          required
          minLength={6}
        />
        <Input
          type="password"
          name="confirmPassword"
          label="Подтвердите пароль"
          value={formData.confirmPassword}
          onChange={handleChange}
          required
        />
        <Button type="submit" loading={isLoading} fullWidth>
          Зарегистрироваться
        </Button>
      </form>
      <div className={styles.authFooter}>
        <span>Уже есть аккаунт?</span>
        <Link to="/login" state={{ from: location.state?.from }}>
          Войти
        </Link>
      </div>
    </div>
  );
};

export default Register;