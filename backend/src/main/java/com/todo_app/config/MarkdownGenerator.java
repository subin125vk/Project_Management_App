package com.todo_app.config;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.todo_app.entity.Project;
import com.todo_app.entity.Todo;

@Component
public class MarkdownGenerator {

    @Value("${github.api.url}")
    private String githubApiUrl;

    @Value("${github.token}")
    private String githubToken;

    public String generateMarkdown(Project project) {
        List<Todo> pendingTodos = project.getTodos()
            .stream()
            .filter(todo -> !todo.isCompleted())
            .collect(Collectors.toList());

        List<Todo> completedTodos = project.getTodos()
            .stream()
            .filter(Todo::isCompleted)
            .collect(Collectors.toList());

        StringBuilder markdown = new StringBuilder();

        markdown.append("# ").append(project.getTitle()).append("\n\n");

        markdown.append("**Summary**: ")
            .append(completedTodos.size())
            .append(" / ")
            .append(project.getTodos().size())
            .append(" completed.\n\n");

        markdown.append("## Pending Todos\n");
        if (pendingTodos.isEmpty()) {
            markdown.append("_No pending todos._\n");
        } else {
            for (Todo todo : pendingTodos) {
                markdown.append("- [ ] ").append(todo.getDescription())
                    .append(" (Created: ").append(todo.getCreatedDate()).append(")\n");
            }
        }
        markdown.append("\n");

        markdown.append("## Completed Todos\n");
        if (completedTodos.isEmpty()) {
            markdown.append("_No completed todos._\n");
        } else {
            for (Todo todo : completedTodos) {
                markdown.append("- [x] ").append(todo.getDescription())
                    .append(" (Completed: ").append(todo.getUpdatedDate()).append(")\n");
            }
        }

        return markdown.toString();
    }

    public String saveMarkdownLocally(String markdownContent, String projectTitle) throws IOException {
        String fileName = projectTitle + "_summary.md";
        File file = new File(fileName);

        if (!file.exists()) {
            file.createNewFile();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(markdownContent);
        }

        System.out.println("Markdown file saved locally as: " + fileName);
        return fileName; 
    }

    public String uploadToGitHub(String markdownContent, String fileName) throws IOException {
        
        saveMarkdownLocally(markdownContent,fileName);

        Map<String, Object> payload = new HashMap<>();
        payload.put("description", "Project Summary Gist");
        payload.put("public", false);

        Map<String, Map<String, String>> files = new HashMap<>();
        Map<String, String> fileContent = new HashMap<>();
        fileContent.put("content", markdownContent);
        files.put(fileName, fileContent);

        payload.put("files", files);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(githubToken); 

        try {
            String response = restTemplate.postForObject(
                githubApiUrl,
                new HttpEntity<>(payload, headers),
                String.class
            );
            System.out.println("Gist uploaded successfully to GitHub!");
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload gist to GitHub: " + e.getMessage(), e);
        }
    }
}
