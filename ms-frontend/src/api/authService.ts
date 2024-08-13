import { LoginType } from '../types/loginType';
import { User, UserInput } from '../types/userType';
import api from './api';

export const login = async (data: LoginType) => {
  const response = await api.post<{ token: string }>('/auth/login', data);
  return response.data;
};

export const register = async (data: UserInput) => {
  const response = await api.post<UserInput>('/persons', data);
  return response.data;
}

export const getUserByEmail = async (token: string, email: string) => {
  const response = await api.post<User>(`/persons/email`, { email }, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  return response.data;
};