package com.todo_app.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo_app.entity.Project;
import com.todo_app.repository.ProjectRepository;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project createProject(Project project) {
         if (project.getCreatedDate() == null) {
            project.setCreatedDate(LocalDateTime.now());
        }
         return projectRepository.save(project);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProjectById(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found!"));
    }

    public Project updateProject(Long projectId, String newTitle) {
        Project project = getProjectById(projectId);
        project.setTitle(newTitle);
        return projectRepository.save(project);
    }

    public void deleteProject(Long projectId) {
        Project project = getProjectById(projectId);
        projectRepository.delete(project);
    }
}
