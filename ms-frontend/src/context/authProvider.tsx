import { User } from '../types/userType';
import { AuthContext } from './authContext';
import { useLocation } from 'react-router-dom';
import { LoginType } from '../types/loginType';
import { useContext, useEffect, useState } from 'react';
import { getUserByEmail, login } from '../api/authService';

export const AuthProvider = ({ children }: { children: React.ReactNode }) => {
  const location = useLocation();
  const [user, setUser] = useState<User | null>(null);
  const [token, setToken] = useState<string | null>(null);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const loadStorageData = async () => {
      const storageUser = localStorage.getItem("user");
      const storageToken = localStorage.getItem("token");

      if (storageUser && storageToken) {
        setUser(JSON.parse(storageUser));
        setToken(storageToken);
      }
    }
    loadStorageData();
  }, [location.key]);

  const navigateBasedOnUserRole = (user: User) => {
    switch (user.role) {
      case 'ADMIN':
        window.location.href = '/admin';
        break;
      case 'MANAGER':
        window.location.href = '/checkin';
        break;
      case 'USER':
        if (localStorage.getItem('checkin') !== null && localStorage.getItem('group') !== null) 
          window.location.href = '/checkout';
        else
          window.location.href = '/';
        break;
      default:
        window.location.href = '/login';
    }
  };

  const Login = async ({ email, password }: LoginType) => {
    try {
      const res = await login({ email, password });
  
      const user = await getUserByEmail(res.token, email);
      if (!user) throw new Error('User not found');
      setUser(user);
  
      localStorage.setItem("user", JSON.stringify(user));
      localStorage.setItem("token", res.token);

      navigateBasedOnUserRole(user);
    } catch (error) {
      setError('Erro ao fazer login. Verifique suas credenciais.');
    }
  };

  const Logout = () => {
    localStorage.clear();
    setUser(null);
    setToken(null);
    window.location.href = '/';
  };

  return (
    <AuthContext.Provider
      value={{ signed: !!user, user, error, setUser, token, Login, Logout }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export const ContextProvider = () => {
  return useContext(AuthContext);
};