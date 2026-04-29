import { useState } from "react";
import api from "../api/api";

export default function Register({ onSwitch }) {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleRegister = async () => {
    try {
      await api.post("/auth/register", { name, email, password });
      alert("Registration successful. Please login.");
      onSwitch();
    } catch {
      alert("Registration failed");
    }
  };

  return (
    <div className="container">
      <h2>MiniDrive Register</h2>

      <input
        placeholder="Name"
        value={name}
        onChange={(e) => setName(e.target.value)}
      />

      <input
        placeholder="Email"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
      />

      <input
        type="password"
        placeholder="Password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />

      <button onClick={handleRegister}>Register</button>

      <div className="link" onClick={onSwitch}>
        Already have an account? Login
      </div>
    </div>
  );
}
