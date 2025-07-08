CREATE TABLE task (
 id BIGSERIAL PRIMARY KEY,
 title TEXT NOT NULL,
 description TEXT,
 task_status VARCHAR(50) NOT NULL,
 due_date date,
 created_at TIMESTAMP NOT NULL DEFAULT NOW(),
 updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

-- トリガー用の関数を定義する
CREATE OR REPLACE FUNCTION set_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- 既存のトリガーがあれば削除してから、新しいトリガーを設定する
-- (起動のたびにエラーにならないようにするため)
DROP TRIGGER IF EXISTS update_tasks_updated_at ON tasks;
CREATE TRIGGER update_tasks_updated_at
BEFORE UPDATE ON tasks
FOR EACH ROW
EXECUTE FUNCTION set_updated_at();