import { Navigate, Route, Routes } from "react-router-dom";
import ProtectedRoute from "./auth/ProtectedRoute.jsx";
import AdminLayout from "./layout/AdminLayout.jsx";
import Dashboard from "./pages/Dashboard.jsx";
import Layanan from "./pages/Layanan.jsx";
import Login from "./pages/Login.jsx";
import Orders from "./pages/Orders.jsx";

function ProtectedPage({ children }) {
  return (
    <ProtectedRoute>
      <AdminLayout>{children}</AdminLayout>
    </ProtectedRoute>
  );
}

function App() {
  return (
    <Routes>
      <Route path="/login" element={<Login />} />
      <Route path="/" element={<Navigate to="/dashboard" replace />} />
      <Route path="/dashboard" element={<ProtectedPage><Dashboard /></ProtectedPage>} />
      <Route path="/layanan" element={<ProtectedPage><Layanan /></ProtectedPage>} />
      <Route path="/orders" element={<ProtectedPage><Orders /></ProtectedPage>} />
    </Routes>
  );
}

export default App;
