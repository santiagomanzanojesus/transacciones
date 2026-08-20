import axios from "axios";

const API_BASE_URL = "http://localhost:8080";

export const login = (username, password) => {
  const response = axios.post(`${API_BASE_URL}/login`, {
    username,
    password,
  });
  if (response.status === 200) {
    localStorage.setItem("token", response.data.token);

    localStorage.setItem("user", JSON.stringify({ username }));
    navigate("/");
  }
  return response;
};

export const crearTransaccion = async (payload) => {
  const token = localStorage.getItem("token");
  if (!token) {
    throw new Error("No hay token de autenticación");
  }
  return axios.post(`${API_BASE_URL}/api/transacciones`, payload, {
    headers: {
      Authorization: `Bearer ${token}`,
      "Content-Type": "application/json",
    },
  });
};

export const obtenerTransacciones = (page, limit, search, sortBy, sortDir) => {
  const params = {
    page,
    limit,
    sortBy,
    sortDir,
  };
  if (search) params.search = search;
  return axios.get(`${API_BASE_URL}/api/transacciones`, { params });
};
