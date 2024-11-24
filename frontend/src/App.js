import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import ProjectsPage from "./pages/ProjectsPage";
import ProjectPage from "./pages/ProjectPage";
import Login from "./pages/Login";

const ProtectedRoute = ({ element: Component, ...rest }) => {
    const isAuthenticated = !!localStorage.getItem("token"); 
    return isAuthenticated ? Component : <Navigate to="/login" />;
};

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/login" element={<Login />} />
                <Route
                    path="/"
                    element={<ProtectedRoute element={<ProjectsPage />} />}
                />
                <Route
                    path="/projects/:id"
                    element={<ProtectedRoute element={<ProjectPage />} />}
                />
            </Routes>
        </Router>
    );
}

export default App;
