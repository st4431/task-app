import { useState, useEffect } from 'react';
import axios from 'axios';
import './App.css';
import { TodoForm } from './components/TodoForm';
import { TodoList } from './components/TodoList';

export interface Todo {
  id: number;
  title: string;
  taskStatus: 'NOT_STARTED' | 'IN_PROGRESS' | 'COMPLETED';
}

function App() {
  // useStateはセッターのようなものである上に、画面の再描画を命令する
  // またジェネリクスによって、todosにはTodo型の配列を指定
  const [todos, setTodos] = useState<Todo[]>([]);
  
  const fetchTasks = async () => {
      try {
        // axiosというAPI通信のライブラリを使用してバックエンドからデータを取得し、responseに格納する
        const response = await axios.get<Todo[]>('http://localhost:8080/api/tasks');
        // response.data でsetStateする
        setTodos(response.data);
      } catch (error) {
        console.log("タスクの取得に失敗しました:", error);
      }
  };

  // 「Reactはstateが変化したら再描画する」ため、ここで useEffect なしで
  // fetchTasksを実行すると、 setTodosが呼び出されて、state が変化し、
  // それにより再描画され、再び fetchTasks が呼び出されて、無限ループになる
  // useEffect はそれを防ぐために使用されるものであり、
  // 依存配列を空にすることで、コンポーネントの初回マウント時のみ実行される
  useEffect(() => {
    // 上で定義した関数をここで実行している
    fetchTasks();
  }, []);

  const addTask = async (title: string) => {
    try {
      const newTodo = {
        title: title,
        taskStatus: 'NOT_STARTED'
      };
      await axios.post('http://localhost:8080/api/tasks', newTodo);
      fetchTasks();
    } catch (error) {
      console.error("タスクの登録に失敗しました：", error);
    }
  };


  // 楽観的UIにロールバック処理を追加
  // 逆に、API通信が成功してからフロント上での削除を行うことで、確実性を優先する方法もあるが、
  // 今回はUIの反応をできるだけ速めることを優先し、楽観的UIを選択
  const deleteTask = async (id: number) => {
    // 元のtodosを避難させておく
    const originalTodos = [...todos];
    // ここで再度fetchTasksを呼び出して最新の状態に更新するとなると、
    // データ件数が多くなってきたときに、パフォーマンスが悪化する可能性がある
    // その場合は、setTodosを使用してローカルの状態を更新する方法もある
    const newTodos = todos.filter(todo => todo.id !== id);
    setTodos(newTodos);
    try {
      await axios.delete(`http://localhost:8080/api/tasks/${id}`);
    } catch (error) {
      console.error("タスクの削除に失敗しました：", error);
      setTodos(originalTodos);
    }
  }

  const updateTaskStatus = async (todo: Todo) => {
    try {
      const updatedTodo = {
        id: todo.id,
        title: todo.title,
        taskStatus: todo.taskStatus === 'COMPLETED' ? 'NOT_STARTED' : 'COMPLETED'
      }
      await axios.put('http://localhost:8080/api/tasks', updatedTodo);
      fetchTasks();
    } catch (error) {
      console.error("タスクの更新に失敗しました：", error);
    }
  }

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