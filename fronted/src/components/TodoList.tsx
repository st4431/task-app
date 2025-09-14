import type { Todo } from '../App';
import './TodoList.css';

interface TodoListProps {
  todos: Todo[];
  onDelete: (id: number) => void;
  onUpdate: (updatedTodo: Todo) => void;
}

export function TodoList({ todos, onDelete, onUpdate }: TodoListProps) {
  return (
    <ul>
      {todos.map((todo) => (
        <li key={todo.id}>
          <input type="checkbox" 
            checked={todo.taskStatus==='COMPLETED'}
            onChange={() => onUpdate(todo)} 
          />
          <span className={todo.taskStatus === 'COMPLETED' ? 'done' : ''}>
            {todo.title}
          </span>
          <button onClick={() => onDelete(todo.id)}>削除</button>
        </li>
      ))}
    </ul>
  )
}