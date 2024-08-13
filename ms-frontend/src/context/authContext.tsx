import { createContext } from 'react';
import { LoginType } from '../types/loginType';
import { User } from '../types/userType';

export type AuthContextType = {
  signed: boolean;
  user: User | null;
  setUser: (user: User | null) => void;
  Login: (LoginType: LoginType) => Promise<void>;
  token: string | null;
  Logout: () => void;
  error: string | null;
}

export const AuthContext = createContext<AuthContextType>({} as AuthContextType);
