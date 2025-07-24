package com.takahata.task_app.repository;

import com.takahata.task_app.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

//classからinterfaceに変更し、JpaRepositoryを継承
//<Task, Integer>は、扱うエンティティの型とその主キーの型を指定
public interface TaskRepository extends JpaRepository<Task, Long> {

}
