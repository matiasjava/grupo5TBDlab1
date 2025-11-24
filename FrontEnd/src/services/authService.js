import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8090/api/auth",
});

export const login = (email, password) => {
  return api.post("/login", { email, password });
};

export const register = (nombre, email, password, biografia) => {
  return api.post("/register", {nombre,email,password,biografia});
};