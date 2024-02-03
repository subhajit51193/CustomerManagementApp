import React, { useState } from "react";
import authenticateUser from "./authenticateUser";
import { Link, useNavigate } from 'react-router-dom';

const LoginForm = ({ setIsAuthenticated, setUserName, setAccessToken,setRoles }) => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();

    try {
      const userData = await authenticateUser(username, password);

      const accessToken = userData.token;
      console.log("Token"+accessToken);

      setAccessToken(accessToken);
      setIsAuthenticated(true);
      setUserName(userData.firstName); 
      setRoles(userData.roles)
      navigate('/')

      // Do something with the user data, e.g., store it in state or context
      console.log("User authenticated:", userData);

      // Redirect to a secure page or perform other actions based on successful authentication
    } catch (error) {
      // Handle authentication error
      console.error("Authentication error:", error);
      // Show an error message to the user, etc.
    }
  };

  return (
    <form
      onSubmit={handleLogin}
      className="max-w-md mx-auto my-8 p-6 bg-white rounded-md shadow-md"
    >
      <h2 className="text-2xl font-bold mb-8">Login Form</h2>
      <div className="mb-4">
        <label
          htmlFor="username"
          className="block text-gray-700 text-sm font-bold mb-2"
        >
          Username:
        </label>
        <input
          type="text"
          id="username"
          className="w-full py-2 px-4 border border-gray-300 rounded-md"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
      </div>
      <div className="mb-4">
        <label
          htmlFor="password"
          className="block text-gray-700 text-sm font-bold mb-2"
        >
          Password:
        </label>
        <input
          type="password"
          id="password"
          className="w-full py-2 px-4 border border-gray-300 rounded-md"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
      </div>
      <button
        type="submit"
        className="w-full py-2 px-4 bg-blue-500 text-white rounded-md hover:bg-blue-600 focus:outline-none focus:shadow-outline-blue"
      >
        Login
      </button>
      <div className="mt-4">
        <p className="text-sm text-gray-600">
          Don't have an account?{' '}
          <Link to="/registration" className="text-blue-500 hover:underline">
            Register
          </Link>
        </p>
      </div>
    </form>
  );
};

export default LoginForm;
