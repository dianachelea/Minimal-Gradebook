import React from "react";
import { Link, useNavigate } from "react-router-dom";

const Header = ({ isLoggedIn, onLogout }) => {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem("token");
    onLogout();
    navigate("/login");
  };

  return (
    <header style={{ display: "flex", justifyContent: "space-between", padding: "10px 20px", backgroundColor: "#333", color: "#fff" }}>
      <div style={{ fontWeight: "bold" }}>Catalog</div>
      <nav>
        <Link to="/" style={{ margin: "0 10px", color: "#fff", textDecoration: "none" }}>Home</Link>
        {isLoggedIn ? (
          <span
            onClick={handleLogout}
            style={{ margin: "0 10px", color: "#fff", cursor: "pointer", textDecoration: "none" }}
          >
            Logout
          </span>
        ) : (
          <Link to="/login" style={{ margin: "0 10px", color: "#fff", textDecoration: "none" }}>
            Login
          </Link>
        )}
      </nav>
    </header>
  );
};

export default Header;
