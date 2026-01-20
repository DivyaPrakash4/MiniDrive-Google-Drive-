import { useState } from "react";
import api from "../api/api";

export default function Login({ onLogin, onRegister }) {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

    const handleLogin = async () => {
    try {
        const res = await api.post(
        "/auth/login",
        {
            email,
            password,
        },
        {
            headers: {
            "Content-Type": "application/json",
            },
        }
        );

        localStorage.setItem("token", res.data);
        onLogin();
    } catch (err) {
        alert("Login failed");
        console.error(err);
    }
    };


  return (
    <div className="container">
      <h2>MiniDrive Login</h2>

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

      <button onClick={handleLogin}>Login</button>

      <div className="link" onClick={onRegister}>
        New user? Create account
      </div>
    </div>
  );
}
