import { useState } from "react";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Dashboard from "./pages/Dashboard";
import "./index.css";

function App() {
  const [page, setPage] = useState("login");

  const loginSuccess = () => setPage("dashboard");
  const logout = () => {
    localStorage.removeItem("token");
    setPage("login");
  };

  if (page === "register")
    return <Register onSwitch={() => setPage("login")} />;

  if (page === "dashboard")
    return <Dashboard onLogout={logout} />;

  return (
    <Login
      onLogin={loginSuccess}
      onRegister={() => setPage("register")}
    />
  );
}

export default App;
