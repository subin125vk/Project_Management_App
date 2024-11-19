package com.todo_app.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo_app.config.MarkdownGenerator;
import com.todo_app.entity.Project;
import com.todo_app.entity.Todo;
import com.todo_app.repository.ProjectRepository;
import com.todo_app.repository.TodoRepository;

@Service
public class TodoService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private MarkdownGenerator markdownGenerator;

    public Todo addTodo(Long projectId, Todo todo) {
        Optional<Project> projectOpt = projectRepository.findById(projectId);
        if (projectOpt.isEmpty()) {
            throw new IllegalArgumentException("Project not found with ID: " + projectId);
        }
        Project project = projectOpt.get();
        todo.setProject(project); 
        return todoRepository.save(todo);
    }
   
    public Todo updateTodo(Long todoId,String description,Boolean isCompleted) {
        Optional<Todo> todoOpt = todoRepository.findById(todoId);
        if (todoOpt.isEmpty()) {
            throw new IllegalArgumentException("Todo not found with ID: " + todoId);
        }
        Todo todo = todoOpt.get();
        todo.setDescription(description);
        todo.setCompleted(isCompleted);
        return todoRepository.save(todo);
    }

    public void deleteTodoById(Long todoId) {
        if (!todoRepository.existsById(todoId)) {
            throw new IllegalArgumentException("Todo not found with ID: " + todoId);
        }
        todoRepository.deleteById(todoId);
    }

    public String exportToGist(Long projectId) throws IOException {
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new RuntimeException("Project not found"));

        String markdown = markdownGenerator.generateMarkdown(project);
        return markdownGenerator.uploadToGitHub(markdown, project.getTitle());
    }
}
