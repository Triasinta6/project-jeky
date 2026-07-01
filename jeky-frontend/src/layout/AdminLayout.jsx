import { NavLink, useNavigate } from "react-router-dom";
import { useAuth } from "../auth/AuthContext";

function AdminLayout({ children }) {
  const navigate = useNavigate();
  const { user, logout } = useAuth();
  const initial = user?.username ? user.username.charAt(0).toUpperCase() : "A";

  function handleLogout() {
    logout();
    navigate("/login");
  }

  return (
    <div className="admin-shell">
      <aside className="sidebar">
        <div className="sidebar-brand">
          <span>Jeky 2026</span>
        </div>

        <div className="profile-box">
          <div className="profile-photo">{initial}</div>
          <div>
            <p className="profile-name">{user?.username || "Admin"}</p>
            <p className="profile-role">{user?.role || "Administrator"}</p>
          </div>
        </div>

        <nav className="sidebar-nav">
          <NavLink to="/dashboard">Home</NavLink>
          <NavLink to="/layanan">Layanan</NavLink>
          <NavLink to="/orders">Orders</NavLink>
          <button type="button" className="sidebar-logout" onClick={handleLogout}>
            Logout
          </button>
        </nav>
      </aside>

      <main className="main">{children}</main>
    </div>
  );
}

export default AdminLayout;
