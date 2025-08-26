-- 既存のテーブルを（存在すれば）削除する
-- taskテーブルがusersテーブルを参照しているため、taskから先に削除する必要がある
DROP TABLE IF EXISTS task;
DROP TABLE IF EXISTS users;

-- H2データベース用のテーブル定義
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

CREATE TABLE task (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title TEXT NOT NULL,
    description TEXT,
    task_status VARCHAR(50) NOT NULL,
    due_date DATE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);