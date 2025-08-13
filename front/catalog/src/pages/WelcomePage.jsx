import React from "react";
import { useNavigate } from "react-router-dom";

const WelcomePage = ({ role, username }) => {
  const navigate = useNavigate();

  return (
    <div style={{ textAlign: "center", marginTop: "50px" }}>
      <h1>Welcome back, {role} {username}!</h1>
      <div style={{ marginTop: "20px" }}>
        <button
          onClick={() => navigate("/students")}
          style={{
            fontSize: "18px",
            padding: "15px 30px",
            margin: "10px",
            backgroundColor: "#4CAF50",
            color: "white",
            border: "none",
            borderRadius: "5px",
            cursor: "pointer",
          }}
        >
          Students
        </button>
        <button
          onClick={() => navigate("/disciplines")}
          style={{
            fontSize: "18px",
            padding: "15px 30px",
            margin: "10px",
            backgroundColor: "#2196F3",
            color: "white",
            border: "none",
            borderRadius: "5px",
            cursor: "pointer",
          }}
        >
          Lectures
        </button>
      </div>
    </div>
  );
};

export default WelcomePage;
