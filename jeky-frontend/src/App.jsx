import { NavLink, Route, Routes } from "react-router-dom";
import Dashboard from "./pages/Dashboard.jsx";
import Layanan from "./pages/Layanan.jsx";
import Orders from "./pages/Orders.jsx";

function App() {
  return (
    <div className="admin-shell">
      <aside className="sidebar">
        <div className="sidebar-brand">
          <div className="brand-logo">J</div>
          <span>Jeky 2026</span>
        </div>

        <div className="profile-box">
          <div className="profile-photo">A</div>
          <div>
            <p className="profile-name">Admin</p>
            <p className="profile-role">Administrator</p>
          </div>
        </div>

        <nav className="sidebar-nav">
          <NavLink to="/" end>Home</NavLink>
          <NavLink to="/layanan">Layanan</NavLink>
          <NavLink to="/orders">Orders</NavLink>
        </nav>
      </aside>

      <main className="main">
        <Routes>
          <Route path="/" element={<Dashboard />} />
          <Route path="/layanan" element={<Layanan />} />
          <Route path="/orders" element={<Orders />} />
        </Routes>
      </main>
    </div>
  );
}

export default App;
