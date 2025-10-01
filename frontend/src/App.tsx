import { useTodos } from './hooks/useTodos'
import './App.css';
import { TodoForm } from './components/TodoForm';
import { TodoList } from './components/TodoList';

function App() {
  const { todos, addTask, deleteTask, updateTaskStatus } = useTodos();
  return (
    <>
      <h1>TODOリスト</h1>
      {/* TodoFormというコンポーネント（メソッド）を呼び出し、
      onAddというProps（引数、左辺）としてaddTodo関数（引数、右辺）を渡します */}
      <TodoForm onAdd={addTask} />

      {/* TodoListというコンポーネント（メソッド）を呼び出す
      todosというProps（引数、左辺）としてtodos（引数、右辺）を渡します */}
      <TodoList todos={todos} onDelete={deleteTask} onUpdate={updateTaskStatus}/>
    </>
  );
}

export default App;