package com.todo_app.controller;

import com.todo_app.entity.Project;
import com.todo_app.entity.Todo;
import com.todo_app.service.ProjectService;
import com.todo_app.service.TodoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ProjectService projectService;

    @Mock
    private TodoService todoService;

    @InjectMocks
    private ProjectController projectController;

    private Project project;
    private Todo todo;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(projectController).build();

        project = new Project();
        project.setId(1L);
        project.setTitle("Test Project");
        project.setCreatedDate(LocalDateTime.now());

        todo = new Todo();
        todo.setId(1L);
        todo.setDescription("Test Todo");
        todo.setCompleted(false);
        todo.setCreatedDate(LocalDateTime.now());
        todo.setUpdatedDate(LocalDateTime.now());
        todo.setProject(project);
    }

    @Test
    public void testListProjects() throws Exception {
        when(projectService.getAllProjects()).thenReturn(Arrays.asList(project));

        mockMvc.perform(get("/projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Project"));

        verify(projectService, times(1)).getAllProjects();
    }

    @Test
    public void testViewProject() throws Exception {
        when(projectService.getProjectById(1L)).thenReturn(project);

        mockMvc.perform(get("/projects/{projectId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Project"));

        verify(projectService, times(1)).getProjectById(1L);
    }

    @Test
    public void testCreateProject() throws Exception {
        when(projectService.createProject(any(Project.class))).thenReturn(project);

        mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Test Project\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Project"));

        verify(projectService, times(1)).createProject(any(Project.class));
    }

    @Test
    public void testUpdateProject() throws Exception {
        when(projectService.updateProject(eq(1L), anyString())).thenReturn(project);

        mockMvc.perform(put("/projects/{projectId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Updated Project\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Project"));

        verify(projectService, times(1)).updateProject(eq(1L), anyString());
    }

    @Test
    public void testDeleteProject() throws Exception {
        mockMvc.perform(delete("/projects/{projectId}", 1L))
                .andExpect(status().isOk());

        verify(projectService, times(1)).deleteProject(1L);
    }

    @Test
    public void testAddTodo() throws Exception {
        when(todoService.addTodo(eq(1L), any(Todo.class))).thenReturn(todo);

        mockMvc.perform(post("/projects/{projectId}/todos", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\": \"Test Todo\", \"completed\": false}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Test Todo"));

        verify(todoService, times(1)).addTodo(eq(1L), any(Todo.class));
    }

    @Test
    public void testUpdateTodo() throws Exception {
        when(todoService.updateTodo(eq(1L), anyString(), eq(false))).thenReturn(todo);

        mockMvc.perform(put("/projects/todos/{todoId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\": \"Updated Todo\", \"completed\": false}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Test Todo"));

        verify(todoService, times(1)).updateTodo(eq(1L), anyString(), eq(false));
    }

    @Test
    public void testDeleteTodo() throws Exception {
        mockMvc.perform(delete("/projects/{projectId}/todos/{todoId}", 1L, 1L))
                .andExpect(status().isOk());

        verify(todoService, times(1)).deleteTodoById(1L);
    }

    @Test
    public void testExportToGist() throws Exception {
        when(todoService.exportToGist(1L)).thenReturn("Export Successful");

        mockMvc.perform(post("/projects/{projectId}/export", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Export Successful"));

        verify(todoService, times(1)).exportToGist(1L);
    }
}
