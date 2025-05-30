import { Navigate } from 'react-router-dom';

const PrivateRoute = ({ children }) => {
  const isAuth = !!localStorage.getItem('accessToken');
  return isAuth ? children : <Navigate to="/login" />;
};