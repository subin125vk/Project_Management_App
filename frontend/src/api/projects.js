<<<<<<< HEAD
import axios from 'axios';
=======
import axios from "axios";
>>>>>>> 70ee6e2 (Auth error correction)

const API_BASE = process.env.REACT_APP_API_BASE_URL;

const api = axios.create({
<<<<<<< HEAD
  baseURL: API_BASE,
  headers: {
    'Content-Type': 'application/json',
  },
});


export const getProjects = () => api.get('/projects');
export const getProjectById = (projectId) => api.get(`/projects/${projectId}`);
export const createProject = (project) => api.post('/projects', project);
export const updateProject = (projectId, updatedProject) => api.put(`/projects/${projectId}`, updatedProject); 
export const deleteProject = (projectId) => api.delete(`/projects/${projectId}`);
export const addTodo = (projectId, todo) => api.post(`/projects/${projectId}/todos`, todo);
export const updateTodo = (todoId, todo) => api.put(`/projects/todos/${todoId}`, todo);
export const deleteTodo = (projectId, todoId) => api.delete(`/projects/${projectId}/todos/${todoId}`);
export const exportProjectToGist = (projectId) => api.post(`/projects/${projectId}/export`);
=======
    baseURL: API_BASE,
    headers: {
        "Content-Type": "application/json",
    },
});

api.interceptors.request.use((config) => {
    const token = localStorage.getItem("token");
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

export const getProjects = () => api.get("/projects");
export const getProjectById = (projectId) => api.get(`/projects/${projectId}`);
export const createProject = (project) => api.post("/projects", project);
export const updateProject = (projectId, updatedProject) =>
    api.put(`/projects/${projectId}`, updatedProject);
export const deleteProject = (projectId) => api.delete(`/projects/${projectId}`);
export const addTodo = (projectId, todo) =>
    api.post(`/projects/${projectId}/todos`, todo);
export const updateTodo = (todoId, todo) =>
    api.put(`/projects/todos/${todoId}`, todo);
export const deleteTodo = (projectId, todoId) =>
    api.delete(`/projects/${projectId}/todos/${todoId}`);
export const exportProjectToGist = (projectId) =>
    api.post(`/projects/${projectId}/export`);
>>>>>>> 70ee6e2 (Auth error correction)
