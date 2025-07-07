#　タスク管理アプリケーション設計書

## 1. 概要

個人の日々のタスク（To-Do）を管理するための Web アプリケーション。
ユーザーはタスクの登録、一覧確認、更新、削除ができる。
このプロジェクトは、Spring Boot による Web アプリケーションの基本的な CRUD 機能を習得することを目的とする。

## 2. 機能要件

- タスク一覧表示機能
- タスク新規登録機能
- タスク更新機能
- タスク削除機能

## クラス設計

```mermaid
classDiagram
    class 
```

## 3. データベース設計

### 1.3.1. テーブル定義書 (`task` テーブル)

| カラム名      | データ型       | 制約                     | 説明                         |
| :------------ | :------------- | :----------------------- | :--------------------------- |
| `id`          | `BIGSERIAL`    | `PRIMARY KEY`            | タスク ID（自動採番）        |
| `title`       | `VARCHAR(255)` | `NOT NULL`               | タスクのタイトル             |
| `description` | `TEXT`         |                          | タスクの詳細説明             |
| `status`      | `VARCHAR(50)`  | `NOT NULL`               | 状態（未着手, 進行中, 完了） |
| `due_date`    | `DATE`         |                          | 完了期限                     |
| `created_at`  | `TIMESTAMP`    | `NOT NULL DEFAULT NOW()` | 作成日時                     |
| `updated_at`  | `TIMESTAMP`    | `NOT NULL DEFAULT NOW()` | 更新日時                     |

### 1.3.2. CREATE 文

```sql
CREATE TABLE task (
    id BIGSERIAL PRIMARY KEY,
    title TEXT NOT NULL,
    description TEXT,
    status VARCHAR(50) NOT NULL,
    due_date DATE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
```
