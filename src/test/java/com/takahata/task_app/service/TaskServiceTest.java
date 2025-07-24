package com.takahata.task_app.service;

import com.takahata.task_app.config.TaskMapper;
import com.takahata.task_app.dto.TaskInputDto;
import com.takahata.task_app.dto.TaskUpdateDto;
import com.takahata.task_app.entity.Task;
import com.takahata.task_app.exception.TaskNotFoundException;
import com.takahata.task_app.repository.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

//JUnit5でMockitoという便利なライブラリを使えるようにするアノテーション

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    //テストしたいクラスのモックを注入し、テストを可能にするためのアノテーション
    @InjectMocks
    private TaskService taskService;

    //実際のそのクラスでAutowiredしているクラスをモックとして注入し、テストを可能にするためのアノテーション
    @Mock
    private TaskRepository taskRepository;

    //@Mockは一つにまとめることができない
    @Mock
    private TaskMapper taskMapper;

    //テストしたいメソッドごとに付与するアノテーション
    @Test
    //テストケースに名前を付けるために付与するアノテーション
    @DisplayName("IDでタスクが正常に取得できる場合のテスト")
    void findTaskById_Success() {
        //1. Arrange(準備)
        //まず、ダミーデータを用意
        Task dummyTask = new Task();
        dummyTask.setId(1);
        dummyTask.setTitle("テストタスク");

        //同じくダミーデータ
        TaskUpdateDto dummyUpdateDto = new TaskUpdateDto();
        dummyUpdateDto.setId(1);
        dummyUpdateDto.setTitle("テストタスク");

        //「taskRepositoryのfindTaskById(1)が呼ばれたら、dummyTaskをOptionalでラップして返してください。」
        //と命令しておく
        when(taskRepository.findTaskById(1)).thenReturn(Optional.of(dummyTask));

        //上と同じように、命令しておく
        when(taskMapper.toTaskUpdateDto(dummyTask)).thenReturn(dummyUpdateDto);

        //2. Act(実行)
        //ここで、上で命令したことが実行され、それによって得られた値が期待値と一致するかこの後で検証する
        TaskUpdateDto actualResult = taskService.findTaskById(1);

        //3. Assert(検証)
        assertThat(actualResult.getId()).isEqualTo(1);
        assertThat(actualResult.getTitle()).isEqualTo("テストタスク");

        //実際に命令が実行されているかについても検証するために、
        //「taskRepositoryのfindTaskById(1)は、ちゃんと1回呼ばれましたか？」と尋ねている
        verify(taskRepository, times(1)).findTaskById(1);
        verify(taskMapper, times(1)).toTaskUpdateDto(dummyTask);
    }

    @Test
    @DisplayName("IDでタスクが見つからない場合にTaskNotFoundExceptionをちゃんとスローするかについてのテスト")
    void findTaskById_ThrowsTaskNotFoundException() {
        final int NON_EXISTENT_ID = 99;

        when(taskRepository.findTaskById(NON_EXISTENT_ID)).thenReturn(Optional.empty());

        //対象の例外がちゃんとスローするか検証するためのメソッド
        assertThrows(TaskNotFoundException.class, () -> taskService.findTaskById(NON_EXISTENT_ID));

        //たとえ例外がスローされるとしてもfindTaskByIdは一回呼び出されるので、その点を検証
        verify(taskRepository, times(1)).findTaskById(NON_EXISTENT_ID);
        //例外がスローされる場合はtoTaskUpdateDtoは呼び出されないので、その点を検証
        //anyは「引数がどんな値でも成立する」ことを表した記述の仕方
        verify(taskMapper, never()).toTaskUpdateDto(any());
    }

    @Test
    @DisplayName("新規登録のためにregisterNewTaskが機能し、オブジェクトの内容が一致するかのテスト")
    void registerNewTask_Success() {
        TaskInputDto dummyInputDto = new TaskInputDto();
        dummyInputDto.setTitle("期待するタイトル");
        dummyInputDto.setDescription("期待する内容");
        Task dummyTask = new Task();
        dummyTask.setTitle("期待するタイトル");
        dummyTask.setDescription("期待する内容");

        when(taskMapper.fromInputDtoToTask(dummyInputDto)).thenReturn(dummyTask);
        doNothing().when(taskRepository).registerNewTask(any(Task.class));

        taskService.registerNewTask(dummyInputDto);

        verify(taskMapper, times(1)).fromInputDtoToTask(dummyInputDto);
        ArgumentCaptor<Task> taskArgumentCaptor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepository, times(1)).registerNewTask(taskArgumentCaptor.capture());

        Task capturedTask = taskArgumentCaptor.getValue();
        assertThat(capturedTask.getTitle()).isEqualTo("期待するタイトル");
        assertThat(capturedTask.getDescription()).isEqualTo("期待する内容");

    }
}