import { useState, useEffect, useCallback } from "react";
import axios from "axios";

export interface Todo {
  id: number;
  title: string;
  taskStatus: 'NOT_STARTED' | 'IN_PROGRESS' | 'COMPLETED';
}

const API_URL = 'http://localhost:8080/api/tasks';

export const useTodos = () => {
  // useStateはセッターのようなものである上に、画面の再描画を命令する
  // またジェネリクスによって、todosにはTodo型の配列を指定
  const [todos, setTodos] = useState<Todo[]>([]);
  
  const fetchTasks = async () => {
      try {
        // axiosというAPI通信のライブラリを使用してバックエンドからデータを取得し、responseに格納する
        const response = await axios.get<Todo[]>(API_URL);
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
      const response = await axios.post(API_URL, newTodo);
      const newTaskWithId = response.data;
      setTodos(prevTodos => [...prevTodos, newTaskWithId]);
    } catch (error) {
      console.error("タスクの登録に失敗しました：", error);
    }
  };


  // 楽観的UIにロールバック処理を追加
  // 逆に、API通信が成功してからフロント上での削除を行うことで、確実性を優先する方法もあるが、
  // 今回はUIの反応をできるだけ速めることを優先し、楽観的UIを選択
  const deleteTask = useCallback(async (id: number) => {
    // 元のtodosを避難させておく
    const originalTodos = [...todos];
    // ここで再度fetchTasksを呼び出して最新の状態に更新するとなると、
    // データ件数が多くなってきたときに、パフォーマンスが悪化する可能性がある
    // その場合は、setTodosを使用してローカルの状態を更新する方法もある
    const newTodos = todos.filter(todo => todo.id !== id);
    setTodos(newTodos);
    try {
      await axios.delete(`${API_URL}/${id}`);
    } catch (error) {
      console.error("タスクの削除に失敗しました：", error);
      setTodos(originalTodos);
    }
  }, [todos]);

  const updateTaskStatus = useCallback(async (todo: Todo) => {
    const originalTodos = [...todos];
    const updatedTodo: Todo = {
        id: todo.id,
        title: todo.title,
        taskStatus: todo.taskStatus === 'COMPLETED' ? 'NOT_STARTED' : 'COMPLETED'
    }
    // Arrays.map は、新しい配列を作成するメソッドであり、
    // return される値がそのループで新たな配列に格納される要素を表す
    const newTodos = todos.map((prevTodo) => {
      if (prevTodo.id === updatedTodo.id) {
        return updatedTodo;
      } 
      return prevTodo;
    });
    setTodos(newTodos);
    try {
      await axios.put(API_URL, updatedTodo);
    } catch (error) {
      console.error("タスクの更新に失敗しました：", error);
      setTodos(originalTodos);
    }
  },[todos]);
  
  return { todos, addTask, deleteTask, updateTaskStatus };
};

