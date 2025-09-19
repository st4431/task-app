package com.takahata.task_app.service;

import com.takahata.task_app.mapper.TaskMapper;
import com.takahata.task_app.dto.TaskInputDto;
import com.takahata.task_app.dto.TaskUpdateDto;
import com.takahata.task_app.entity.Task;
import com.takahata.task_app.entity.TaskStatus;
import com.takahata.task_app.exception.TaskNotFoundException;
import com.takahata.task_app.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    //読み取り専用のメソッドであることを明示し、処理を効率化させられる
    @Transactional(readOnly = true)
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public void registerNewTask(TaskInputDto newTask) {
        taskRepository.save(taskMapper.fromInputDtoToTask(newTask));
    }

    public void deleteTask(long id) {
        taskRepository.deleteById(id);
    }

    //もし中身が空だったら例外を投げておく
    public TaskUpdateDto findTaskUpdateDtoById(long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("ID:" + id + "のタスクが見つかりません。"));
        return taskMapper.toTaskUpdateDto(task);
    }

    public void updateTask(TaskUpdateDto taskUpdateDto) {
        //findTaskByIdで一度呼び出しているが、今後「作成時間が-ヶ月前の場合は-する」と言ったような条件分岐を実装することを考慮し、あえて2回呼び出す。
        //時にはDRY原則を破ることもある
        Task task = taskRepository.findById(taskUpdateDto.getId())
                .orElseThrow(() -> new TaskNotFoundException("ID:" + taskUpdateDto.getId() + "のタスクが見つかりません。"));
        taskMapper.updateTaskFromUpdateDto(task, taskUpdateDto);
        taskRepository.save(task);
    }

    //Stream APIを習得するための練習
    public List<Task> findTasksByStatus(TaskStatus taskStatus) {
        return taskRepository.findAll().stream()
                .filter(task -> task.getTaskStatus().equals(taskStatus))
                .toList();
    }

}
