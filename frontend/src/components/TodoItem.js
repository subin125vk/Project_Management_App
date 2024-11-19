import React, { useState } from "react";

const TodoItem = ({ todo, onUpdate, onDelete }) => {
    const [description, setDescription] = useState(todo.description);
    const [completed, setCompleted] = useState(todo.completed);

    const handleUpdate = () => {
        onUpdate(todo.id, { description, completed });
    };

    return (
        <li>
            <input
                type="text"
                value={description}
                onChange={(e) => setDescription(e.target.value)}
            />
            <input
                type="checkbox"
                checked={completed}
                onChange={(e) => setCompleted(e.target.checked)}
            />
            <button onClick={handleUpdate}>Update</button>
            <button onClick={() => onDelete(todo.id)}>Delete</button>
        </li>
    );
};

export default TodoItem;
