
import React from 'react';
import { Link, useNavigate,useLocation } from 'react-router-dom';

const Navbar = ({ isAuthenticated, accessToken, roles }) => {

  const navigate = useNavigate();
  const location = useLocation();

  const handleAdminPanelClick = () => {
    // Redirect to the Admin Panel page
    console.log("admin panel button clicked");
    console.log(accessToken);
    navigate('/admin-panel', { state: { accessToken } });
  };
  
  // Check if the user is on the "Admin Panel" page
  const isOnAdminPanelPage = location.pathname === '/admin-panel';

  return (
    <nav className="bg-gray-800 p-4">
      <div className="container mx-auto flex justify-between items-center">
        <div className="text-white text-lg font-bold">My App</div>
        <ul className="flex space-x-4">
          <li><Link to="/" className="text-white hover:text-gray-300">Home</Link></li>
          {!isAuthenticated && (
            <>
              <li><Link to="/login" className="text-white hover:text-gray-300">Login</Link></li>
              <li><Link to="/registration" className="text-white hover:text-gray-300">Register</Link></li>
            </>
          )}
        </ul>
        
        {isAuthenticated && (
          <button
            // onClick={handleLogout} 
            className="bg-red-500 text-white py-2 px-4 rounded-md hover:bg-red-600 focus:outline-none focus:shadow-outline-red">
            Logout
          </button>
        )}
        {isAuthenticated && roles.some(role => role.roleName === 'ROLE_ADMIN') && !isOnAdminPanelPage &&(
          <button
            onClick={handleAdminPanelClick} 
            className="bg-blue-500 text-white py-2 px-4 rounded-md hover:bg-blue-600 focus:outline-none focus:shadow-outline-blue mr-4">
            Admin-Panel
          </button>
        )}
      </div>
    </nav>
  );
};

export default Navbar;
