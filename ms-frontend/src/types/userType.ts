export type User = {
  id: string;
  fullName: string;
  username: string;
  email: string;
  role: string;
};

export type UserInput = {
  fullName: string;
  username: string;
  email: string;
  password: string;
  role: string;
};