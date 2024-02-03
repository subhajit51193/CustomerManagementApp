import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const AdminPanel = ({ isAuthenticated, accessToken, roles }) => {
  const [customers, setCustomers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [selectedCustomer, setSelectedCustomer] = useState(null);
  const [showDetailsModal, setShowDetailsModal] = useState(false);
  const [isEditing, setIsEditing] = useState(false);
  const [updatedCustomer, setUpdatedCustomer] = useState({
    id: "",
    firstName: "",
    lastName: "",
    address: "",
    city: "",
    phone: "",
    state: "",
    street: "",
  });
  const navigate = useNavigate();

  useEffect(() => {
    // Check if the user is authenticated and has the ROLE_ADMIN
    if (
      !isAuthenticated ||
      !roles.some((role) => role.roleName === "ROLE_ADMIN")
    ) {
      // Redirect to the home page or display an access denied message
      navigate("/");
    } else {
      // Fetch customer data if the user is authenticated and has the required role
      const fetchAllCustomers = async () => {
        try {
          const response = await axios.get(
            "http://localhost:8080/api/admin/allCustomers",
            {
              headers: {
                Authorization: `Bearer ${accessToken}`,
              },
            }
          );
          setCustomers(response.data);
        } catch (error) {
          console.error("Error fetching customers:", error);
        } finally {
          setLoading(false);
        }
      };

      fetchAllCustomers();
    }
  }, [isAuthenticated, roles, navigate]);

  const handleCheckDetails = async (customerId) => {
    try {
      const response = await axios.get(
        `http://localhost:8080/api/admin/${customerId}`,
        {
          // method: 'GET',
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        }
      );
      console.log("Check response:", response.data);
      setSelectedCustomer(response.data);
      setShowDetailsModal(true);
    } catch (error) {
      console.error("Error checking task:", error);
    }
    console.log(`Checking details for customer with ID: ${customerId}`);
  };
  //show customer data popup
  const handleCloseDetailsModal = () => {
    setShowDetailsModal(false);
    setSelectedCustomer(null);
  };

  const handleEditDetails = (customerId) => {
    // Set the selected customer details to the state for editing
    const selected = customers.find((customer) => customer.id === customerId);
    setUpdatedCustomer({
      id: selected.id,
      firstName: selected.firstName,
      lastName: selected.lastName,
      address: selected.address,
      city: selected.city,
      phone: selected.phone,
      state: selected.state,
      street: selected.street,
    });
    setIsEditing(true);
  };

  const handleUpdateDetails = async () => {
    // Implement logic to update customer details using the updatedCustomer state
    try {
      const response = await axios.patch(
        `http://localhost:8080/api/admin/update/${updatedCustomer.id}`,
        updatedCustomer,
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        }
      );
      console.log("Update response:", response.data);
      // Update the customers list with the updated details
      setCustomers((prevCustomers) =>
        prevCustomers.map((customer) =>
          customer.id === updatedCustomer.id ? updatedCustomer : customer
        )
      );
      setIsEditing(false); // Reset isEditing after updating details
    } catch (error) {
      console.error("Error updating customer details:", error);
    }
  };

  const handleDeleteCustomer = (customerId) => {
    // Add logic to delete the specific customer
    console.log(`Deleting customer with ID: ${customerId}`);
  };

  return (
    <div className="container mx-auto p-4">
      <h1 className="text-2xl font-bold mb-4">Admin Panel - All Customers</h1>
      {loading ? (
        <p>Loading...</p>
      ) : (
        <div>
          {customers.length === 0 ? (
            <p>No customers found.</p>
          ) : (
            <ul>
              {customers.map((customer) => (
                <li key={customer.id}>
                  <h2 className="text-xl font-bold mb-2">
                    {customer.firstName} {customer.lastName}
                  </h2>
                  <ul>
                    {/* Render roles if needed */}
                    {customer.roles && (
                      <li>
                        <span className="font-semibold">Roles:</span>{" "}
                        {customer.roles.map((role) => (
                          <span key={role.roleId}>--{role.roleName}</span>
                        ))}
                      </li>
                    )}
                  </ul>
                  <div className="my-4">
                    <button
                      onClick={() => handleCheckDetails(customer.id)}
                      className="bg-green-500 text-white py-2 px-4 rounded-md hover:bg-green-600 focus:outline-none focus:shadow-outline-green mr-2"
                    >
                      Check
                    </button>
                    <button
                      onClick={() => handleEditDetails(customer.id)}
                      className="bg-blue-500 text-white py-2 px-4 rounded-md hover:bg-blue-600 focus:outline-none focus:shadow-outline-blue mr-2"
                    >
                      Edit
                    </button>
                    <button
                      onClick={() => handleDeleteCustomer(customer.id)}
                      className="bg-red-500 text-white py-2 px-4 rounded-md hover:bg-red-600 focus:outline-none focus:shadow-outline-red"
                    >
                      Delete
                    </button>
                  </div>

                  {/* Edit  */}
                  {isEditing && updatedCustomer.id === customer.id && (
                    <div>
                      {/* Editable form */}
                      <label htmlFor="firstName">First Name:</label>
                      <input
                        type="text"
                        id="firstName"
                        value={updatedCustomer.firstName}
                        onChange={(e) =>
                          setUpdatedCustomer({
                            ...updatedCustomer,
                            firstName: e.target.value,
                          })
                        }
                      />
                      <label htmlFor="lastName">Last Name:</label>
                      <input
                        type="text"
                        id="lastName"
                        value={updatedCustomer.lastName}
                        onChange={(e) =>
                          setUpdatedCustomer({
                            ...updatedCustomer,
                            lastName: e.target.value,
                          })
                        }
                      />
                      <label htmlFor="street">Street:</label>
                      <input
                        type="text"
                        id="street"
                        value={updatedCustomer.street}
                        onChange={(e) =>
                          setUpdatedCustomer({
                            ...updatedCustomer,
                            street: e.target.value,
                          })
                        }
                      />
                      <label htmlFor="address">Address:</label>
                      <input
                        type="text"
                        id="address"
                        value={updatedCustomer.address}
                        onChange={(e) =>
                          setUpdatedCustomer({
                            ...updatedCustomer,
                            address: e.target.value,
                          })
                        }
                      />
                      <label htmlFor="state">State:</label>
                      <input
                        type="text"
                        id="state"
                        value={updatedCustomer.state}
                        onChange={(e) =>
                          setUpdatedCustomer({
                            ...updatedCustomer,
                            state: e.target.value,
                          })
                        }
                      />
                      <label htmlFor="phone">Phone:</label>
                      <input
                        type="text"
                        id="phone"
                        value={updatedCustomer.phone}
                        onChange={(e) =>
                          setUpdatedCustomer({
                            ...updatedCustomer,
                            phone: e.target.value,
                          })
                        }
                      />

                      <button
                        onClick={handleUpdateDetails}
                        className="bg-green-500 text-white py-2 px-4 rounded-md hover:bg-green-600 focus:outline-none focus:shadow-outline-green mt-2"
                      >
                        Save Changes
                      </button>
                      <button
                        onClick={() => setIsEditing(false)}
                        className="bg-gray-500 text-white py-2 px-4 rounded-md hover:bg-gray-600 focus:outline-none focus:shadow-outline-gray"
                      >
                        Close
                      </button>
                    </div>
                  )}

                  <hr className="my-4" />
                </li>
              ))}
            </ul>
          )}
        </div>
      )}
      {/* Display the selected customer data modal */}
      {showDetailsModal && (
        <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
          <div className="bg-white p-4 max-w-md">
            <h2 className="text-xl font-bold mb-2">
              Selected Customer Details
            </h2>
            {selectedCustomer && (
              <ul>
                <li>
                  <span className="font-semibold">ID:</span>{" "}
                  {selectedCustomer.id}
                </li>
                <li>
                  <span className="font-semibold">First Name:</span>{" "}
                  {selectedCustomer.firstName}
                </li>
                <li>
                  <span className="font-semibold">Last Name:</span>{" "}
                  {selectedCustomer.lastName}
                </li>
                <li>
                  <span className="font-semibold">Email:</span>{" "}
                  {selectedCustomer.email}
                </li>
                <li>
                  <span className="font-semibold">Street:</span>{" "}
                  {selectedCustomer.street}
                </li>
                <li>
                  <span className="font-semibold">Address:</span>{" "}
                  {selectedCustomer.address}
                </li>
                <li>
                  <span className="font-semibold">City:</span>{" "}
                  {selectedCustomer.city}
                </li>
                <li>
                  <span className="font-semibold">State:</span>{" "}
                  {selectedCustomer.state}
                </li>
                <li>
                  <span className="font-semibold">Phone:</span>{" "}
                  {selectedCustomer.phone}
                </li>
              </ul>
            )}
            <button
              onClick={handleCloseDetailsModal}
              className="bg-blue-500 text-white py-2 px-4 rounded-md hover:bg-blue-600 focus:outline-none focus:shadow-outline-blue mt-4"
            >
              Close
            </button>
          </div>
        </div>
      )}
    </div>
  );
};

export default AdminPanel;
