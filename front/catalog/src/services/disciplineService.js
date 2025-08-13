import axios from "axios";

const API_URL = "http://localhost:8080/api/gradebook/lectures";

const disciplineService = {
  getAll: async () => {
    const response = await axios.get(API_URL, {
      headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
    });
    return response.data._embedded?.disciplinas || [];
  },
  getById: async (id) => {
    const response = await axios.get(`${API_URL}/${id}`, {
      headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
    });
    return response.data;
  },
  add: async (discipline) => {
    const response = await axios.post(API_URL, discipline, {
      headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
    });
    return response.data;
  },
  update: async (id, discipline) => {
    const response = await axios.put(`${API_URL}/${id}`, discipline, {
      headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
    });
    return response.data;
  },
  delete: async (id) => {
    await axios.delete(`${API_URL}/${id}`, {
      headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
    });
  },
};

export default disciplineService;
