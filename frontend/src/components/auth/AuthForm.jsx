import { useState } from 'react';
import { authApi } from '../../api/auth';
import Button from '../ui/Button';
import Input from '../ui/Input';

const AuthForm = ({ mode = 'login' }) => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [name, setName] = useState('');
  const [image, setImage] = useState(null);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (mode === 'login') {
        const tokens = await authApi.login(email, password);
        localStorage.setItem('accessToken', tokens.accessToken);
      } else {
        await authApi.register({ email, password, name }, image);
      }
      window.location.reload();
    } catch (error) {
      alert(`Ошибка: ${error.response?.data?.message || error.message}`);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      {mode === 'register' && (
        <>
          <Input label="Имя" value={name} onChange={(e) => setName(e.target.value)} />
          <Input type="file" label="Аватар" onChange={(e) => setImage(e.target.files[0])} />
        </>
      )}
      <Input type="email" label="Email" value={email} onChange={(e) => setEmail(e.target.value)} />
      <Input type="password" label="Пароль" value={password} onChange={(e) => setPassword(e.target.value)} />
      <Button type="submit">{mode === 'login' ? 'Войти' : 'Зарегистрироваться'}</Button>
    </form>
  );
};