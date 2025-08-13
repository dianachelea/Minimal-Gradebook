import React, { useState } from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import Header from "./components/Header";
import LoginPage from "./pages/LoginPage";
import WelcomePage from "./pages/WelcomePage";
import StudentsPage from "./pages/StudentsPage";
import StudentDetailsPage from "./pages/StudentDetailsPage";
import AddStudentPage from "./pages/AddStudentPage";
import EditStudentPage from "./pages/EditStudentPage";
import DisciplinePage from "./pages/DisciplinePage";
import DisciplinaDetailsPage from "./pages/DisciplinaDetailsPage";
import AddDisciplinaPage from "./pages/AddDisciplinaPage";
import EditDisciplinaPage from "./pages/EditDisciplinaPage";

const App = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [role, setRole] = useState(""); 
  const [username, setUsername] = useState("");

  const handleLogin = (userRole, userName) => {
    setIsLoggedIn(true);
    setRole(userRole);
    setUsername(userName);
  };

  const handleLogout = () => {
    setIsLoggedIn(false);
    setRole("");
    setUsername("");
  };

  const PrivateRoute = ({ children, roles }) => {
    if (!isLoggedIn) {
      return <Navigate to="/login" />;
    }
    if (roles && !roles.includes(role)) {
      return <Navigate to="/welcome" />;
    }
    return children;
  };

  return (
    <Router>
      <Header isLoggedIn={isLoggedIn} onLogout={handleLogout} />
      <Routes>
        <Route
          path="/login"
          element={
            isLoggedIn ? (
              <Navigate to="/welcome" />
            ) : (
              <LoginPage onLogin={handleLogin} />
            )
          }
        />

        <Route
          path="/welcome"
          element={
            <PrivateRoute>
              <WelcomePage role={role} username={username} />
            </PrivateRoute>
          }
        />

        <Route
          path="/students"
          element={
            <PrivateRoute>
              <StudentsPage role={role} />
            </PrivateRoute>
          }
        />
        <Route
          path="/students/:id"
          element={
            <PrivateRoute>
              <StudentDetailsPage />
            </PrivateRoute>
          }
        />
        <Route
          path="/students/add"
          element={
            <PrivateRoute roles={["profesor"]}>
              <AddStudentPage />
            </PrivateRoute>
          }
        />
        <Route
          path="/students/edit/:id"
          element={
            <PrivateRoute roles={["profesor"]}>
              <EditStudentPage />
            </PrivateRoute>
          }
        />

        <Route
          path="/disciplines"
          element={
            <PrivateRoute>
              <DisciplinePage role={role} />
            </PrivateRoute>
          }
        />
        <Route
          path="/disciplines/details/:id"
          element={
            <PrivateRoute>
              <DisciplinaDetailsPage />
            </PrivateRoute>
          }
        />

        <Route
          path="/"
          element={<Navigate to={isLoggedIn ? "/welcome" : "/login"} />}
        />
        <Route
        path="/disciplines/add"
        element={
          <PrivateRoute roles={["profesor"]}>
            <AddDisciplinaPage />
          </PrivateRoute>
        }
      />
      <Route
        path="/disciplines/edit/:id"
        element={
          <PrivateRoute roles={["profesor"]}>
            <EditDisciplinaPage />
          </PrivateRoute>
        }
      />
      </Routes>
      
    </Router>
  );
};

export default App;
