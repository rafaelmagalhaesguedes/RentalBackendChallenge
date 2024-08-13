import { Routes, Route } from 'react-router-dom';
import { LoginPage } from '../login';
import { Home } from '../home';
import { CarGroup } from '../components/carGroup';
import { Layout } from '../components/layout';
import { Additional } from '../components/additional';
import { RegisterPage } from '../components/register';
import { Success } from '../components/payment/success';
import { Cancel } from '../components/payment/cancel';

export const Public = () => {
  return (
    <Routes>
      <Route index element={<Home />} />
      <Route path="/" element={<Layout />}>
        <Route path="reservation/group" element={<CarGroup />} />
        <Route path="reservation/additional" element={<Additional />} />
        <Route path="login" element={<LoginPage />} />
        <Route path="register" element={<RegisterPage />} />
        <Route path="success" element={<Success />} />
        <Route path="cancel" element={<Cancel />} />
        <Route path="*" element={<h1>Page Not Found</h1>} />
      </Route>
    </Routes>
  );
};
