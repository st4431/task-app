package com.takahata.task_app.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String userName;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    //Taskクラスの中にあるuserフィールドがこの外部キーの関係性のオーナーであることを示す
    //つまり、TaskクラスがUserクラスに対して外部キーを持っているということ
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Task> tasks;
}
