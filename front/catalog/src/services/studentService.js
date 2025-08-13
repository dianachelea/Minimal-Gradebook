import axios from "axios";

const API_URL = "http://localhost:8080/api/gradebook/students";

const studentService = {
  getAll: async (page = 1, size = 5) => {
    try {
      const validPage = page > 0 ? page : 1;
      const validSize = size > 0 ? size : 5;

      const response = await axios.get(API_URL, {
        params: { page: validPage, students_per_page: validSize },
        headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
      });
      return {
        students: response.data._embedded?.students || [],
        totalElements: response.data.page?.totalElements || 0,
        totalPages: response.data.page?.totalPages || 1,
        currentPage: response.data.page?.number || 1,
      };
    } catch (error) {
      console.error("Error fetching students:", error.response?.data || error.message);
      throw error;
    }
  },
  getById: async (id) => {
    const response = await axios.get(`${API_URL}/${id}`, {
      headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
    });
    return response.data;
  },
  add: async (student) => {
    const response = await axios.post(API_URL, student, {
      headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
    });
    return response.data;
  },
  update: async (id, student) => {
    const response = await axios.put(`${API_URL}/${id}`, student, {
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

export default studentService;
