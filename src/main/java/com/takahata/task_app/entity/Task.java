package com.takahata.task_app.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity //このクラスがDBのエンティティ（テーブル）に対応していることを示す
@Table(name = "task") //対応するテーブル名を指定
@EntityListeners(AuditingEntityListener.class) //Auditing機能をこのエンティティで有効化
public class Task {
    @Id //このフィールドが主キーであることを示す
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DBの自動連番機能をフィールドで使用することを示す
    private long id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING) //Enum型を文字列としてDBに保存する
    private TaskStatus taskStatus;

    private LocalDate dueDate;

    @CreatedDate //新規作成次に自動で日時をセット
    private LocalDateTime createdAt;

    @LastModifiedDate //更新時に自動で日時をセット
    private LocalDateTime updatedAt;
}
