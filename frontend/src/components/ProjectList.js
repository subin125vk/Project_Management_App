import React from "react";
import { Link } from "react-router-dom";

const ProjectList = ({ projects, onDeleteProject }) => (
    <ul>
        {projects.map((project) => (
            <li key={project.id}>
                <Link to={`/projects/${project.id}`}>{project.title}</Link>
                <button onClick={() => onDeleteProject(project.id)}>Delete</button>
            </li>
        ))}
    </ul>
);

export default ProjectList;
