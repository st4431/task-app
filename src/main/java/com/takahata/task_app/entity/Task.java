package com.takahata.task_app.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity //このクラスがDBのエンティティ（テーブル）に対応していることを示す
@Table(name = "task") //対応するテーブル名を指定
public class Task {


    @Id //このフィールドが主キーであることを示す
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DBの自動連番機能をフィールドで使用することを示す
    private long id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING) //Enum型を文字列としてDBに保存する
    private TaskStatus taskStatus;

    private LocalDate dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
