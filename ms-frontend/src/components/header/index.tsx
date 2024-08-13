import { FaCarAlt, FaClock } from 'react-icons/fa';
import { Container, Logo, NavLink, NavLinks } from './style';
import { Link } from 'react-router-dom';
import { useState, useEffect, useContext } from 'react';
import { AuthContext } from '../../context/authContext';

export function Header() {
  const { Logout } = useContext(AuthContext);
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem('token');
    setIsLoggedIn(!!token);
  }, []);

  const handleLogout = () => {
    Logout();
  }

  return (
    <Container>
      <Logo>
        <FaCarAlt size={40} />
        {' '}
        <Link to="/">Rental Car</Link>
      </Logo>
      <NavLinks>
        <NavLink href="#">ATENDIMENTO 24H<FaClock /></NavLink>
        <NavLink href="#">WHATSAPP</NavLink>
        {isLoggedIn ?
          <NavLink href="#" onClick={handleLogout}>LOGOUT</NavLink>
        : (
          <NavLink href="/login"><strong>LOGIN</strong></NavLink>
        )}
      </NavLinks>
    </Container>
  );
}