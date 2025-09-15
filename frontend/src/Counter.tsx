// src/components/Counter.tsx

// 1. このコンポーネントが受け取るPropsの型を定義します
// Javaでいうとコンポーネントはメソッド、Propsは引数のようなもの
export interface CounterProps {
  // TypeScriptの素晴らしいところ！Propsの型を厳密にできる
  label: string;
  count: number;
  // ここで関数名を定義している
  // onIncrementは標準搭載されているわけではない
  // 特に呼び出す関数がないことが想定される場合は以下のように記述することでオプショナルにもできる
  // onIncrement?: () => void;
  actions: CounterActions; // 引数なし、戻り値なしの関数
}

export interface CounterActions {
  increment: () => void;
  decrement: () => void;
  reset: () => void;
}

// 2. Propsを引数として受け取る
export function Counter({ label, count, actions }: CounterProps) {
  return (
    <div>
      <h2>{label}</h2>
      <p>現在のカウント: {count}</p>
      <button onClick={actions.increment}>+</button>
      <button onClick={actions.decrement}>-</button>
      <button onClick={actions.reset}>Reset</button>
    </div>
  );
}

