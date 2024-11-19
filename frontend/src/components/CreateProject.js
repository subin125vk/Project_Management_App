import React, { useState } from "react";
import { createProject } from "../api/projects"; 

const CreateProject = () => {
    const [projectTitle, setProjectTitle] = useState("");
    const [message, setMessage] = useState({ text: "", type: "" });

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!projectTitle.trim()) {
            setMessage({ text: "Project title cannot be empty!", type: "error" });
            return;
        }

        try {
            const { data } = await createProject({ title: projectTitle });
            setMessage({ text: `Project "${data.title}" created successfully!`, type: "success" });
            setProjectTitle("");
        } catch (error) {
            setMessage({ text: "Failed to create project. Please try again.", type: "error" });
        }
    };

    return (
        <div className="create-project">
            <h1>Create a New Project</h1>
            <form onSubmit={handleSubmit}>
                <input
                    type="text"
                    value={projectTitle}
                    onChange={(e) => setProjectTitle(e.target.value)}
                    placeholder="Enter project title"
                />
                <button type="submit">Create Project</button>
            </form>
            {message.text && <p className={message.type}>{message.text}</p>}
        </div>
    );
};

export default CreateProject;
