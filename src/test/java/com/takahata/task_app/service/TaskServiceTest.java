package com.takahata.task_app.service;

import com.takahata.task_app.config.TaskMapper;
import com.takahata.task_app.dto.TaskUpdateDto;
import com.takahata.task_app.entity.Task;
import com.takahata.task_app.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @Test
    void findTaskUpdateDtoById_Success() {
        Task dummyTask = new Task();
        dummyTask.setId(1);
        dummyTask.setTitle("テストタスク");

        TaskUpdateDto dummyDto = new TaskUpdateDto();
        dummyDto.setId(1);
        dummyDto.setTitle("テストタスク");

        when(taskRepository.findTaskById(1)).thenReturn(Optional.of(dummyTask));

        when(taskMapper.toTaskUpdateDto(dummyTask)).thenReturn(dummyDto);

        TaskUpdateDto actualResult = taskService.findTaskUpdateDtoById(1);

        assertThat(actualResult.getId()).isEqualTo(1);
        assertThat(actualResult.getTitle()).isEqualTo("テストタスク");

        verify(taskRepository, times(1)).findTaskById(1);
        verify(taskMapper, times(1)).toTaskUpdateDto(dummyTask);






    }
}