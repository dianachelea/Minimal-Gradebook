import axios from "axios";

const authService = {
  login: async (username, password) => {
    const response = await axios.post("http://localhost:8080/api/gradebook/auth/login", {
      username,
      password,
    });

    return response.data;
  },
};

export default authService;
