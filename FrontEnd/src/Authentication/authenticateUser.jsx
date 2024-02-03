import axios from "axios";

const authenticateUser = async (username, password) => {
  const credentials = `${username}:${password}`;
  const base64Credentials = btoa(credentials);

  try {
    const response = await axios.post(
      "http://localhost:8080/api/authenticate",
      {},
      {
        headers: {
          Authorization: `Basic ${base64Credentials}`,
        },
      }
    );

    // Assuming the response structure is similar to what you provided
    const { token, email, userId, firstName, lastName, roles } = response.data;

    // You can store the token in a secure way (e.g., in a state variable or local storage)
    // For example, using state:
    // setToken(token);

    // Return user information or token as needed
    return { token, email, userId, firstName, lastName, roles };
  } catch (error) {
    console.error("Authentication failed:", error);
    // Handle authentication failure, e.g., show an error message to the user
    throw error;
  }
};

export default authenticateUser;
