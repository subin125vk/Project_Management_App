package com.todo_app.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.todo_app.entity.Project;
import com.todo_app.entity.Todo;
import com.todo_app.service.ProjectService;
import com.todo_app.service.TodoService;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TodoService todoService;

       @GetMapping
        public List<Project> listProjects() {
            return projectService.getAllProjects();
        }
    
        @GetMapping("/{projectId}")
        public Project viewProject(@PathVariable Long projectId) {
            return projectService.getProjectById(projectId);
        }
    
        @PostMapping
        public Project createProject(@RequestBody Project project) {
            return projectService.createProject(project);
        }
    
        @PutMapping("/{projectId}")
        public Project updateProject(@PathVariable Long projectId, @RequestBody Project updatedProject) {
            return projectService.updateProject(projectId, updatedProject.getTitle());
        }
    
        @DeleteMapping("/{projectId}")
        public void deleteProject(@PathVariable Long projectId) {
            projectService.deleteProject(projectId);
        }
    
        @PostMapping("/{projectId}/todos")
        public Todo addTodo(@PathVariable Long projectId, @RequestBody Todo todo) {
            return todoService.addTodo(projectId, todo);
        }
    
        @PutMapping("/todos/{todoId}")
        public Todo updateTodo(@PathVariable Long todoId, @RequestBody Todo updatedTodo) {
            return todoService.updateTodo(todoId, updatedTodo.getDescription(), updatedTodo.isCompleted());
        }
    
        @DeleteMapping("/{projectId}/todos/{todoId}")
        public void deleteTodo(@PathVariable Long projectId, @PathVariable Long todoId) {
            todoService.deleteTodoById(todoId);
        }
    
        @PostMapping("/{projectId}/export")
        public String exportToGist(@PathVariable Long projectId) throws IOException {
            return todoService.exportToGist(projectId);
        }
    }
    

