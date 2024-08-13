import { Routes, Route } from 'react-router-dom';
import { Layout } from '../components/layout';
import { Checkout } from '../components/checkout';
import { UserDashboard } from '../dashboard/user';

export const Protect = () => {
  return (
    <Routes>
      <Route path="/" element={<Layout />}>
        <Route path="checkout" element={<Checkout />} />
        <Route path="dashboard" element={<UserDashboard />} />
        <Route path="*" element={<h1>Page Not Found</h1>} />
      </Route>
    </Routes>
  );
};
