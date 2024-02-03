import { useState } from "react";
import "./App.css";
import RegistrationForm from "./RegistrationForm/RegistrationForm";
import LoginForm from "./Authentication/LoginForm";
import { BrowserRouter as Router, Route, Routes,useNavigate } from "react-router-dom";
import Home from "./Home/Home";
import Navbar from "./Navbar/Navbar";
import AdminPanel from "./Admin/AdminPanel";

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [userName, setUserName] = useState("");
  const [accessToken, setAccessToken] = useState(null);
  const [roles, setRoles] = useState([]);

  return (
    <div>
      {/* Other components or content can go here */}
      <Navbar isAuthenticated={isAuthenticated} accessToken={accessToken} roles={roles}/>
      <Routes>
        <Route path="/login" element={<LoginForm setIsAuthenticated={setIsAuthenticated} setUserName={setUserName} setAccessToken={setAccessToken} setRoles={setRoles}/>} />
        <Route path="/registration" element={<RegistrationForm setIsAuthenticated={setIsAuthenticated} setUserName={setUserName} setAccessToken={setAccessToken} setRoles={setRoles}/>} />
        <Route path="/" element={<Home isAuthenticated={isAuthenticated} userName={userName} accessToken={accessToken} setRoles={setRoles}/> } />
        <Route path="/admin-panel" element={<AdminPanel isAuthenticated={isAuthenticated} accessToken={accessToken} roles={roles} />} /> 
      </Routes>
    </div>
  );
}

export default App;
