// MainRoutes.js
import { Routes, Route } from 'react-router-dom';
import { Protect } from './protect';
import { Public } from './public';
import { Navigate } from 'react-router-dom';

export const MainRoutes = () => {
  const token = localStorage.getItem("token");
  
  return (
    <Routes>
      {token ? (
        <Route path="/*" element={<Protect />} />
      ) : (
        <Route path="/*" element={<Public />} />
      )}
      <Route path="*" element={<Navigate to={token ? "/" : "/login"} />} />
    </Routes>
  );
};