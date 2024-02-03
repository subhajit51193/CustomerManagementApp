import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const Home = ({ isAuthenticated, userName, accessToken, setRoles }) => {
  const [customerDetails, setCustomerDetails] = useState(null);
  const [loading, setLoading] = useState(true);
  const [isEditing, setIsEditing] = useState(false);
  const [updatedDetails, setUpdatedDetails] = useState({
    firstName: "",
    lastName: "",
    address: "",
    city: "",
    phone: "",
    state: "",
  });
  const navigate = useNavigate();

  const fetchCustomerDetails = async () => {
    try {
      const response = await axios.get(
        "http://localhost:8080/api/customer/myDetails",
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        }
      );
      setCustomerDetails(response.data);
      setRoles(response.data.roles);
      setUpdatedDetails({
        firstName: response.data.firstName,
        lastName: response.data.lastName,
        address: response.data.address,
        city: response.data.city,
        phone: response.data.phone,
        state: response.data.state,
      });
    } catch (error) {
      console.error("Error fetching customer details:", error);
    } finally {
      setLoading(false);
    }
  };

  const handleEditClick = () => {
    setIsEditing(true);
  };

  const handleCancelEdit = () => {
    setIsEditing(false);
    // Reset the form with current details
    setUpdatedDetails({
      firstName: customerDetails.firstName,
      lastName: customerDetails.lastName,
      address: customerDetails.address,
      city: customerDetails.city,
      phone: customerDetails.phone,
      state: customerDetails.state,
    });
  };

  const handleUpdateDetails = async () => {
    try {
      const response = await axios.patch(
        "http://localhost:8080/api/customer/update",
        updatedDetails,
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
            "Content-Type": "application/json",
          },
        }
      );
      if (response.status >= 200 && response.status <= 300) {
        console.log("Details updated successfully", response.data);
        // Fetch updated customer details
        fetchCustomerDetails();
        // Exit edit mode
        setIsEditing(false);
      } else {
        console.error(
          "Details update failed:",
          response.status,
          response.statusText
        );
      }
    } catch (error) {
      console.error("Error updating details:", error);
    }
  };
  useEffect(() => {
    // Check if the user is authenticated before fetching customerDetails
    if (isAuthenticated) {
      fetchCustomerDetails();
    } else {
      setLoading(false);
    }
  }, [isAuthenticated, accessToken]);

  const handleRefresh = () => {
    // Add logic here to refresh the tasks
    fetchCustomerDetails();
  };
  

  return (
    <div className="container mx-auto p-4">
      <h1 className="text-2xl font-bold mb-4">
        {isAuthenticated ? `Welcome, ${userName}!` : "Welcome!"}
      </h1>
      {customerDetails && (
        <div>
          <h2 className="text-xl font-bold mb-2">Your Customer Details:</h2>
          <ul>
            <li>
              <span className="font-semibold">ID:</span> {customerDetails.id}
            </li>
            <li>
              <span className="font-semibold">First Name:</span>{" "}
              {customerDetails.firstName}
            </li>
            <li>
              <span className="font-semibold">Last Name:</span>{" "}
              {customerDetails.lastName}
            </li>
            <li>
              <span className="font-semibold">Street:</span>{" "}
              {customerDetails.street}
            </li>
            <li>
              <span className="font-semibold">Address:</span>{" "}
              {customerDetails.address}
            </li>
            <li>
              <span className="font-semibold">City:</span>{" "}
              {customerDetails.city}
            </li>
            <li>
              <span className="font-semibold">State:</span>{" "}
              {customerDetails.state}
            </li>
            <li>
              <span className="font-semibold">Phone:</span>{" "}
              {customerDetails.phone}
            </li>
            <li>
              <span className="font-semibold">Email:</span>{" "}
              {customerDetails.email}
            </li>
            {/* Render roles if needed */}
            {customerDetails.roles && (
              <li>
                <span className="font-semibold">Roles:</span>{" "}
                {customerDetails.roles.map((role) => (
                  <span key={role.roleId}>{role.roleName}</span>
                ))}
              </li>
            )}
          </ul>

          {isAuthenticated && !isEditing && (
            <button
              onClick={handleEditClick}
              className="bg-blue-500 text-white px-4 py-2 mt-4 rounded"
            >
              Edit Details
            </button>
          )}
          
          {isAuthenticated && isEditing && (
            <div className="mt-4">
              <h2 className="text-xl font-bold mb-2">Update Your Details:</h2>
              <form>
                {/* Add input fields for editable details */}
                {/* For simplicity, I'm using a generic onChange handler for all fields */}
                <div className="mb-4">
                  <label className="block text-gray-600 text-sm font-semibold mb-2">
                    First Name
                  </label>
                  <input
                    type="text"
                    name="firstName"
                    value={updatedDetails.firstName}
                    onChange={(e) =>
                      setUpdatedDetails({
                        ...updatedDetails,
                        firstName: e.target.value,
                      })
                    }
                    className="border rounded w-full py-2 px-3"
                  />
                </div>
                <div className="mb-4">
                  <label className="block text-gray-600 text-sm font-semibold mb-2">
                    Last Name
                  </label>
                  <input
                    type="text"
                    name="lastName"
                    value={updatedDetails.lastName}
                    onChange={(e) =>
                      setUpdatedDetails({
                        ...updatedDetails,
                        lastName: e.target.value,
                      })
                    }
                    className="border rounded w-full py-2 px-3"
                  />
                </div>
                <div className="mb-4">
                  <label className="block text-gray-600 text-sm font-semibold mb-2">
                    Address
                  </label>
                  <input
                    type="text"
                    name="address"
                    value={updatedDetails.address}
                    onChange={(e) =>
                      setUpdatedDetails({
                        ...updatedDetails,
                        address: e.target.value,
                      })
                    }
                    className="border rounded w-full py-2 px-3"
                  />
                </div>
                <div className="mb-4">
                  <label className="block text-gray-600 text-sm font-semibold mb-2">
                    City
                  </label>
                  <input
                    type="text"
                    name="city"
                    value={updatedDetails.city}
                    onChange={(e) =>
                      setUpdatedDetails({
                        ...updatedDetails,
                        city: e.target.value,
                      })
                    }
                    className="border rounded w-full py-2 px-3"
                  />
                </div>
                <div className="mb-4">
                  <label className="block text-gray-600 text-sm font-semibold mb-2">
                    Phone
                  </label>
                  <input
                    type="text"
                    name="phone"
                    value={updatedDetails.phone}
                    onChange={(e) =>
                      setUpdatedDetails({
                        ...updatedDetails,
                        phone: e.target.value,
                      })
                    }
                    className="border rounded w-full py-2 px-3"
                  />
                </div>
                <div className="mb-4">
                  <label className="block text-gray-600 text-sm font-semibold mb-2">
                    State
                  </label>
                  <input
                    type="text"
                    name="state"
                    value={updatedDetails.state}
                    onChange={(e) =>
                      setUpdatedDetails({
                        ...updatedDetails,
                        state: e.target.value,
                      })
                    }
                    className="border rounded w-full py-2 px-3"
                  />
                </div>

                <div className="flex">
                  <button
                    type="button"
                    onClick={handleUpdateDetails}
                    className="bg-green-500 text-white px-4 py-2 rounded mr-2"
                  >
                    Update
                  </button>
                  <button
                    type="button"
                    onClick={handleCancelEdit}
                    className="bg-gray-500 text-white px-4 py-2 rounded"
                  >
                    Cancel
                  </button>
                </div>
              </form>
            </div>
          )}
        </div>
      )}

      {!isAuthenticated && !loading && (
        <p className="text-gray-600">
          This is the home page content accessible to all users. Login to check
          your tasks.
        </p>
      )}
      {!isAuthenticated && !loading && (
        <div className="text-center mt-8">
          <p className="text-2xl font-bold text-gray-800 mb-4">
            Welcome to Customer Management App!
          </p>
          <p className="text-lg text-gray-600 mb-6">
            Organize your data efficiently and stay productive with our Customer
            management app. Sign in to manage your data and unlock additional
            features.
          </p>
        </div>
      )}
    </div>
  );
};

export default Home;
