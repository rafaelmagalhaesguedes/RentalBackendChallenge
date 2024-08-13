import { useEffect, useRef, useState } from 'react';
import { FaEye, FaEyeSlash } from 'react-icons/fa';
import { ContextProvider } from '../context/authProvider';
import { Container, Content, Error, Form, Title, Wrapper } from './style';
import { Link } from 'react-router-dom';

export function LoginPage() {
  const { Login, error } = ContextProvider();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const emailRef = useRef<HTMLInputElement>(null);

  const handleLogin = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    await Login({ email, password });
  };

  useEffect(() => {
    if (emailRef.current) emailRef.current.focus();
  }, []);

  return (
    <Container>
      <Wrapper>
        <Content>
          <Title>Login</Title>
          <Form onSubmit={handleLogin}>
            <label>
              <input
                ref={emailRef}
                type="text"
                name="email"
                placeholder="Email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
              />
            </label>
            <label className="password">
              <input
                type={showPassword ? 'text' : 'password'}
                name="password"
                placeholder="Senha"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />
              {showPassword ? (
                <FaEye className="iconEye" size={20} onClick={() => setShowPassword(false)} />
              ) : (
                <FaEyeSlash className="iconEye" size={20} onClick={() => setShowPassword(true)} />
              )}
            </label>
            <button type="submit">Entrar</button>
            <div>
              <label>
                <Link to="/register"><span>Criar Conta</span></Link>
              </label>
              <label>
                <a className="forgot" href="/forgot-password">Esqueceu sua senha?</a>
              </label>
            </div>
          </Form>
          {error && <Error>{error}</Error>}
        </Content>
      </Wrapper>
    </Container>
  );
}