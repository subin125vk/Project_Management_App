<<<<<<< HEAD
import React from 'react';  
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import ProjectsPage from "./pages/ProjectsPage";
import ProjectPage from "./pages/ProjectPage";
=======
import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import ProjectsPage from "./pages/ProjectsPage";
import ProjectPage from "./pages/ProjectPage";
import Login from "./pages/Login";

// ProtectedRoute to guard private routes
const ProtectedRoute = ({ element: Component, ...rest }) => {
    const isAuthenticated = !!localStorage.getItem("token"); // Check if token exists
    return isAuthenticated ? Component : <Navigate to="/login" />;
};
>>>>>>> 70ee6e2 (Auth error correction)

function App() {
    return (
        <Router>
            <Routes>
<<<<<<< HEAD
                <Route path="/" element={<ProjectsPage />} />
                <Route path="/projects/:id" element={<ProjectPage />} />
=======
                <Route path="/login" element={<Login />} />
                <Route
                    path="/"
                    element={<ProtectedRoute element={<ProjectsPage />} />}
                />
                <Route
                    path="/projects/:id"
                    element={<ProtectedRoute element={<ProjectPage />} />}
                />
>>>>>>> 70ee6e2 (Auth error correction)
            </Routes>
        </Router>
    );
}

export default App;
