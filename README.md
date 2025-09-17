# タスク管理アプリケーション

個人の日々のタスク（To-Do）を管理するためのWebアプリケーションです。このプロジェクトは、Spring Boot、ReactによるWebアプリケーションの基本的なCRUD（作成、読み取り、更新、削除）機能の習得を目的としています。

---

## ⚠️ 注意点

* **ログイン機能は現在実装中です。** そのため、現状では認証なしで全ての機能にアクセス可能です。
* **CI/CDパイプラインは現在機能していません。** 以前はバックエンドのみに対応していましたが、フロントエンドの追加に伴い現在停止しています。
* **AWSへのデプロイは現在停止中です。**

---

## 主な機能 ✨

* **タスク管理 (Web UI)**
    * タスク一覧表示
    * タスクの新規登録
    * タスクの更新
    * タスクの削除
* **タスク管理 (REST API)**
    * `GET /api/tasks`: 全てのタスクを取得します。
    * `POST /api/tasks`: 新しいタスクを登録します。
    * `PUT /api/tasks/{id}`: 既存のタスクを更新します。
    * `DELETE /api/tasks/{id}`: タスクを削除します。

---

## 使用技術 🛠️

* **バックエンド:**
    * Java 17
    * Spring Boot 3.5.3
    * Spring Web
    * Spring Data JPA
    * Spring Security
* **フロントエンド:**
    * React 19.1.1
    * TypeScript
    * Vite
    * Thymeleaf
* **データベース:**
    * PostgreSQL 14
    * H2 Database (テスト用)
* **ビルドツール:**
    * Gradle
* **その他:**
    * Docker / Docker Compose
    * Lombok

---

## 実行環境の構築 (ローカル) 🚀

このアプリケーションはDockerを使用して簡単にローカル環境で実行できます。

1.  **リポジトリをクローンします。**

    ```bash
    git clone [https://github.com/st4431/task-app.git](https://github.com/st4431/task-app.git)
    cd task-app
    ```

2.  **Dockerコンテナをビルドして起動します。**

    ```bash
    docker-compose up --build
    ```

3.  **アプリケーションにアクセスします。**

    * Webアプリケーション: `http://localhost:8080/home`
    * フロントエンド (Vite): `http://localhost:5173`

---

## データベース設計

### `users` テーブル

| カラム名   | データ型       | 制約                   | 説明                   |
| :--------- | :------------- | :--------------------- | :--------------------- |
| `id`       | `BIGINT`       | `PRIMARY KEY`          | ユーザーID（自動採番） |
| `username` | `VARCHAR(255)` | `NOT NULL`, `UNIQUE`   | ユーザー名             |
| `password` | `VARCHAR(255)` | `NOT NULL`             | パスワード             |
| `role`     | `VARCHAR(50)`  | `NOT NULL`             | ロール（例: USER, ADMIN） |

### `task` テーブル

| カラム名      | データ型        | 制約            | 説明                           |
| :------------ | :-------------- | :-------------- | :----------------------------- |
| `id`          | `BIGSERIAL`     | `PRIMARY KEY`   | タスクID（自動採番）           |
| `title`       | `TEXT`          | `NOT NULL`      | タスクのタイトル               |
| `description` | `TEXT`          |                 | タスクの詳細説明               |
| `task_status` | `VARCHAR(50)`   | `NOT NULL`      | 状態（未着手, 進行中, 完了）   |
| `due_date`    | `DATE`          |                 | 完了期限                       |
| `created_at`  | `TIMESTAMP`     | `NOT NULL`      | 作成日時                       |
| `updated_at`  | `TIMESTAMP`     | `NOT NULL`      | 更新日時                       |
| `user_id`     | `BIGINT`        | `FOREIGN KEY`   | ユーザーID                     |
