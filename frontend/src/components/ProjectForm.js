import React, { useState } from "react";
import { createProject, updateProject } from "../api/projects";

const ProjectForm = ({ project = {}, onSuccess }) => {
    const [title, setTitle] = useState(project.title || "");
    const [errorMessage, setErrorMessage] = useState("");
    const [successMessage, setSuccessMessage] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!title.trim()) {
            setErrorMessage("Project title cannot be empty!");
            return;
        }

        const action = project.id ? updateProject : createProject;
        const payload = { title };

        try {
            const response = await action(project.id, payload);
            setSuccessMessage(`Project "${response.data.title}" ${project.id ? 'updated' : 'created'} successfully!`);
            setErrorMessage("");
            onSuccess();
        } catch (error) {
            setErrorMessage("Failed to submit project. Please try again.");
            setSuccessMessage("");
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <h1>{project.id ? "Edit" : "Create"} Project</h1>
            <input
                className="input-field"
                type="text"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                placeholder="Enter project title"
            />
            <button type="submit">{project.id ? "Update" : "Create"} Project</button>
            {errorMessage && <p className="error">{errorMessage}</p>}
            {successMessage && <p className="success">{successMessage}</p>}
        </form>
    );
};

export default ProjectForm;
