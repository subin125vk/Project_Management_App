import React, { useState, useEffect, useCallback } from "react";
import { useParams } from "react-router-dom";
import {
  getProjectById,
  addTodo,
  updateTodo,
  deleteTodo,
  updateProject,
  exportProjectToGist,
} from "../api/projects";

const ProjectPage = () => {
  const { id } = useParams();
  const [project, setProject] = useState(null);
  const [todoDescription, setTodoDescription] = useState("");
  const [editingTodoId, setEditingTodoId] = useState(null);
  const [editedTodo, setEditedTodo] = useState({});
  const [status, setStatus] = useState(null);
  const [error, setError] = useState(null);
  const [editingTitle, setEditingTitle] = useState(false);
  const [newTitle, setNewTitle] = useState("");

  // Memoize fetchProject to avoid unnecessary re-renders
  const fetchProject = useCallback(async () => {
    try {
      const { data } = await getProjectById(id);
      setProject(data);
      setNewTitle(data.title);
    } catch (error) {
      console.error("Failed to fetch project:", error);
    }
  }, [id]); // The function depends on `id`, so it's included in the dependency array

  useEffect(() => {
    fetchProject();
  }, [fetchProject]); // Now `fetchProject` is in the dependency array

  const handleAddTodo = async (e) => {
    e.preventDefault();
    if (!todoDescription.trim()) return;
    try {
      await addTodo(id, { description: todoDescription });
      setTodoDescription("");
      fetchProject();
    } catch (error) {
      console.error("Failed to add todo:", error);
    }
  };

  const handleStartEditTodo = (todo) => {
    setEditingTodoId(todo.id);
    setEditedTodo({
      description: todo.description,
      completed: todo.completed,
    });
  };

  const handleSaveTodo = async (todoId) => {
    if (editedTodo.completed === false) {
      alert("Todo must be marked as 'Completed' before saving.");
      return;
    }

    try {
      await updateTodo(todoId, editedTodo);
      setEditingTodoId(null);
      fetchProject();
    } catch (error) {
      console.error("Failed to update todo:", error);
    }
  };

  const handleDeleteTodo = async (todoId) => {
    try {
      await deleteTodo(id, todoId);
      fetchProject();
    } catch (error) {
      console.error("Failed to delete todo:", error);
    }
  };

  const handleExportToGist = async () => {
    try {
      const response = await exportProjectToGist(id);
      setStatus(`Gist created successfully! View it here: ${response.data}`);
    } catch (error) {
      setError("Failed to export project to Gist.");
    }
  };

  const handleUpdateProjectTitle = async () => {
    try {
      await updateProject(id, { title: newTitle });
      setEditingTitle(false);
      fetchProject();
    } catch (error) {
      console.error("Failed to update project title:", error);
    }
  };

  return (
    <div className="project-container">
      {project && (
        <>
          <div className="project-header">
            {editingTitle ? (
              <div className="title-edit">
                <input
                  type="text"
                  value={newTitle}
                  onChange={(e) => setNewTitle(e.target.value)}
                />
                <button onClick={handleUpdateProjectTitle}>Save</button>
                <button onClick={() => setEditingTitle(false)}>Cancel</button>
              </div>
            ) : (
              <h2 onClick={() => setEditingTitle(true)}>{project.title}</h2>
            )}
            <button onClick={handleExportToGist}>Export to Gist</button>
          </div>
          <form onSubmit={handleAddTodo} className="add-todo-form">
            <input
              type="text"
              value={todoDescription}
              onChange={(e) => setTodoDescription(e.target.value)}
              placeholder="Add a new todo"
            />
            <button type="submit">Add Todo</button>
          </form>
          <table className="todo-table">
            <thead>
              <tr>
                <th>Todo ID</th>
                <th>Description</th>
                <th>Status</th>
                <th>Created Date</th>
                <th>Updated Date</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {project.todos.map((todo) => (
                <tr key={todo.id}>
                  <td>{todo.id}</td>
                  <td>
                    {editingTodoId === todo.id ? (
                      <input
                        type="text"
                        value={editedTodo.description}
                        onChange={(e) =>
                          setEditedTodo({ ...editedTodo, description: e.target.value })
                        }
                      />
                    ) : (
                      todo.description
                    )}
                  </td>
                  <td>
                    {editingTodoId === todo.id ? (
                      <select
                        value={editedTodo.completed ? "COMPLETED" : "PENDING"}
                        onChange={(e) =>
                          setEditedTodo({
                            ...editedTodo,
                            completed: e.target.value === "COMPLETED",
                          })
                        }
                      >
                        <option value="PENDING">Pending</option>
                        <option value="COMPLETED">Completed</option>
                      </select>
                    ) : (
                      todo.completed ? "Completed" : "Pending"
                    )}
                  </td>
                  <td>{todo.createdDate}</td>
                  <td>{todo.updatedDate}</td>
                  <td>
                    {editingTodoId === todo.id ? (
                      <>
                        <button
                          onClick={() => handleSaveTodo(todo.id)}
                          disabled={editedTodo.completed === false}
                        >
                          Save
                        </button>
                        <button onClick={() => setEditingTodoId(null)}>Cancel</button>
                      </>
                    ) : (
                      <>
                        <button onClick={() => handleStartEditTodo(todo)}>Edit</button>
                        <button onClick={() => handleDeleteTodo(todo.id)}>Delete</button>
                      </>
                    )}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
          {status && <p>{status}</p>}
          {error && <p>{error}</p>}
        </>
      )}
    </div>
  );
};

export default ProjectPage;
