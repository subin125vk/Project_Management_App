import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";  // Remove useNavigate import
import { getProjects, createProject, deleteProject } from "../api/projects";
import axios from 'axios';

const API_BASE = process.env.REACT_APP_API_BASE_URL;

const api = axios.create({
  baseURL: API_BASE,
  headers: {
    'Content-Type': 'application/json',
  },
});

const ProjectsPage = () => {
  const [projects, setProjects] = useState([]);
  const [errorMessage, setErrorMessage] = useState("");
  const [projectTitle, setProjectTitle] = useState("");

  useEffect(() => {
    fetchProjects();
  }, []);

  const fetchProjects = async () => {
    try {
      const { data } = await getProjects();
      setProjects(data);
      setErrorMessage("");
    } catch (err) {
      setErrorMessage("Failed to fetch projects.");
    }
  };

  const handleCreateProject = async (e) => {
    e.preventDefault();
    try {
      await createProject({ title: projectTitle });
      setProjectTitle("");
      fetchProjects();
    } catch (err) {
      setErrorMessage("Failed to create project.");
    }
  };

  const handleDeleteProject = async (id) => {
    if (window.confirm("Are you sure you want to delete this project?")) {
      try {
        await deleteProject(id);
        fetchProjects();
      } catch (err) {
        setErrorMessage("Failed to delete project.");
      }
    }
  };

  const handleLogout = async () => {
    try {
      await api.post('/logout', null, {
        withCredentials: true, 
      });

      localStorage.removeItem("token");
      window.location.href = API_BASE; 
    } catch (err) {
      console.error("Logout failed", err);
    }
  };

  return (
    <div>
      <h1>Projects</h1>

      <button className="logout-button" onClick={handleLogout}>
        Logout
      </button>

      <form onSubmit={handleCreateProject}>
        <input
          type="text"
          value={projectTitle}
          onChange={(e) => setProjectTitle(e.target.value)}
          placeholder="New Project Title"
        />
        <button type="submit">Create Project</button>
      </form>

      {errorMessage && <p>{errorMessage}</p>}

      <ul>
        {projects.map((project) => (
          <li key={project.id}>
            <Link to={`/projects/${project.id}`}>{project.title}</Link>
            <button onClick={() => handleDeleteProject(project.id)}>
              Delete
            </button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default ProjectsPage;
