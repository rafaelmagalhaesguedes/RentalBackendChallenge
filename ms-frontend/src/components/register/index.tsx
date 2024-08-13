import React, { useState } from 'react';
import { UserInput } from '../../types/userType';
import { Form, Label, Input, Button, ContainerLogin, Title, Message } from './style';
import { FaEye, FaEyeSlash } from 'react-icons/fa';
import { useNavigate } from 'react-router';
import api from '../../api/api';

export function RegisterPage() {
  const navigate = useNavigate();
  const [isRegistered, setIsRegistered] = useState(false);
  const [messageInfo, setMessageInfo] = useState<{ message: string; success: boolean } | null>(null);
  const [revalidatedPassword, setRevalidatedPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const [showRevalidatedPassword, setShowRevalidatedPassword] = useState(false);
  const [errors, setErrors] = useState({
    fullName: '',
    username: '',
    email: '',
    password: '',
  });

  const [formData, setFormData] = useState<UserInput>({
    fullName: '',
    username: '',
    email: '',
    password: '',
    role: 'USER',
  });

  const validateFormData = (formData: UserInput) => {
    const errors = {
      fullName: '',
      username: '',
      email: '',
      password: '',
      role: '',
    };

    if (!formData.fullName) {
      errors.fullName = 'Nome completo é obrigatório';
    }

    if (!formData.username) {
      errors.username = 'Nome de usuário é obrigatório';
    }

    // Validate Email
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(formData.email)) {
      errors.email = 'Email inválido';
    }

    // Validate Password
    const passwordRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])\S{6,20}$/;
    if (!passwordRegex.test(formData.password)) {
      errors.password = 'A senha deve ter entre 6 e 20 caracteres, incluindo pelo menos uma letra maiúscula, uma letra minúscula e um número';
    }

    return errors;
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;

    setFormData(prevState => ({
      ...prevState,
      [name]: value,
    }));

    setErrors(prevErrors => ({
      ...prevErrors,
      [name]: '',
    }));
  };

  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };

  const toggleRevalidatedPasswordVisibility = () => {
    setShowRevalidatedPassword(!showRevalidatedPassword);
  };

  const handleRevalidatePasswordChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setRevalidatedPassword(e.target.value);
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    const validationErrors = validateFormData(formData);
    setErrors(validationErrors);

    if (Object.values(validationErrors).some(error => error !== '')) {
      setMessageInfo({ message: 'Por favor, corrija os erros antes de enviar.', success: false });
      return;
    }

    if (formData.password !== revalidatedPassword) {
      setMessageInfo({ message: 'As senhas não coincidem.', success: false });
      return;
    }

    try {
      const response = await api.post<UserInput>('/persons', formData);
      if (response.status === 201) {
        setIsRegistered(true);
        setMessageInfo({ message: 'Usuário cadastrado com sucesso!', success: true });
      } else {
        setMessageInfo({ message: 'Falha ao registrar usuário.', success: false });
      }
    } catch (error) {
      console.error('Error:', error);
    }

    setFormData({
      fullName: '',
      username: '',
      email: '',
      password: '',
      role: 'USER',
    });
  };

  const redirectToLogin = () => {
    navigate('/login');
  };

  return (
    <ContainerLogin>
      <Form onSubmit={handleSubmit}>
        <Title>Crie seu cadastro</Title>
        <Label>
          <Input type="text" name="fullName" placeholder="Nome completo" value={formData.fullName} onChange={handleChange} />
          {errors.fullName && <Message success={false}>{errors.fullName}</Message>}
        </Label>
        <Label>
          <Input type="text" name="username" placeholder="Nome do usuário" value={formData.username} onChange={handleChange} />
          {errors.username && <Message success={false}>{errors.username}</Message>}
        </Label>
        <Label>
          <Input type="email" name="email" placeholder="Email" value={formData.email} onChange={handleChange} />
          {errors.email && <Message success={false}>{errors.email}</Message>}
        </Label>
        <Label>
          <Input type={showPassword ? "text" : "password"} name="password" placeholder="Senha" value={formData.password} onChange={handleChange} />
          <span onClick={togglePasswordVisibility}>
            {showPassword ? <FaEye /> : <FaEyeSlash />}
          </span>
          {errors.password && <Message success={false}>{errors.password}</Message>}
        </Label>
        <Label>
          <Input type={showRevalidatedPassword ? "text" : "password"} placeholder="Revalidar Senha" value={revalidatedPassword} onChange={handleRevalidatePasswordChange} />
          <span onClick={toggleRevalidatedPasswordVisibility}>
            {showRevalidatedPassword ? <FaEye /> : <FaEyeSlash />}
          </span>
        </Label>
        {isRegistered ? (
          <Button onClick={redirectToLogin}>Logar</Button>
        ) : (
          <Button type="submit">Cadastrar</Button>
        )}
        {messageInfo && <Message success={messageInfo.success}>{messageInfo.message}</Message>}
      </Form>
    </ContainerLogin>
  );
};
