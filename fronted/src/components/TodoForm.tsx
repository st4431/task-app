import { useState } from "react";

interface TodoFormProps {
  // 引数が（）ではなく{}で囲まれている場合は、関数あるいはオブジェクトを表す
  // 関数を使用したければ以下のように記述する
  onAdd: (text: string) => void;
}

export function TodoForm({ onAdd }: TodoFormProps) {
  const [inputText, setInputText] = useState<string>('');
  
  
  const handleSubmit = () => {
    if (inputText.trim() === '') {
      return;
    } 
    // ここで親クラスから呼び出されているaddTodoが、
    // onAddというTodoFormのPropsとして別の名で呼び出されている
    onAdd(inputText);
    setInputText('');
  };

  return (
    <div>
      <input 
        type="text"
        value={inputText}
        onChange={(e) => setInputText(e.target.value)} 
      />
      <button onClick={handleSubmit}>追加</button>
    </div>
  );
}