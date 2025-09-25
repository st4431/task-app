package com.takahata.task_app.service;

import com.takahata.task_app.mapper.TaskMapper;
import com.takahata.task_app.dto.TaskInputDto;
import com.takahata.task_app.dto.TaskUpdateDto;
import com.takahata.task_app.entity.Task;
import com.takahata.task_app.exception.TaskNotFoundException;
import com.takahata.task_app.repository.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

// JUnit5でMockitoという便利なライブラリを使えるようにするアノテーション
// @ExtendWithはSpring Bootの機能を使わない単体テストで使用するアノテーション
// 単体テストでは、SpringBootのDIコンテナを使用せず、Mockitoというライブラリの最小限の機能だけでテストを行う
@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    // 単体テストにおいて、テストしたいクラスのインスタンスを作り、
    // そこに@Mockがついた偽物のオブジェクトをDIするためのアノテーション
    @InjectMocks
    private TaskService taskService;

    // 実際のそのクラスでAutowiredしているクラスをモックとして注入し、テストを可能にするためのアノテーション
    @Mock
    private TaskRepository taskRepository;

    // @Mockは一つにまとめることができない
    @Mock
    private TaskMapper taskMapper;

    @Nested
    @DisplayName("findAllのテスト")
    class FindAllTests {
        @Test
        @DisplayName("タスクが存在する場合、すべてのタスクが返されること")
        void findAll_Success() {
            List<Task> testTaskList = new ArrayList<>();
            Task testTask1 = new Task();
            testTask1.setId(1);
            testTask1.setTitle("test1");
            testTaskList.add(testTask1);
            Task testTask2 = new Task();
            testTask2.setId(2);
            testTask2.setTitle("test2");
            testTaskList.add(testTask2);

            when(taskRepository.findAll()).thenReturn(testTaskList);

            List<Task> actualList = taskService.findAll();

            verify(taskRepository, times(1)).findAll();

            assertThat(actualList.size()).isEqualTo(2);
            assertThat(actualList).isEqualTo(testTaskList);
        }

        @Test
        @DisplayName("タスクが存在しない場合、空のリストが返されること")
        void findAll_ReturnsEmptyList_WhenNoTasksExist() {
            when(taskRepository.findAll()).thenReturn(Collections.emptyList());

            List<Task> actualList = taskService.findAll();

            verify(taskRepository, times(1)).findAll();

            assertThat(actualList).isEmpty();
            assertThat(actualList).isNotNull();
        }

        @Test
        @DisplayName("全取得の際、TaskRepositoryが例外をスローした場合、findAllも同様に例外をスローするか")
        void findAll_ThrowsException_WhenRepositoryThrowsException() {
            when(taskRepository.findAll()).thenThrow(new RuntimeException("Database error"));

            assertThrows(RuntimeException.class, () -> taskService.findAll());

            verify(taskRepository, times(1)).findAll();
        }
    }

    @Nested
    @DisplayName("registerNewTaskのテスト")
    class RegisterNewTaskTests {
        @Test
        @DisplayName("新規登録のためにregisterNewTaskが機能し、オブジェクトの内容が一致するかのテスト")
        void registerNewTask_Success() {
            // Arrange
            TaskInputDto dummyInputDto = new TaskInputDto();
            dummyInputDto.setTitle("期待するタイトル");
            dummyInputDto.setDescription("期待する内容");
            Task dummyTask = new Task();
            dummyTask.setTitle("期待するタイトル");
            dummyTask.setDescription("期待する内容");

            when(taskMapper.fromInputDtoToTask(dummyInputDto)).thenReturn(dummyTask);

            // 今回のテストケースの場合、以下は省略しても大丈夫。理由はAIに聞けば納得する。
//        doNothing().when(taskRepository).createTask(any(Task.class));

            // Act
            taskService.createTask(dummyInputDto);

            // Assert
            verify(taskMapper, times(1)).fromInputDtoToTask(dummyInputDto);

            // 中身が期待値と一致するか検証するため、 ArgumentCaptor を使用してRepositoryに渡された実際の引数（Taskオブジェクト）を捕まえる
            ArgumentCaptor<Task> taskArgumentCaptor = ArgumentCaptor.forClass(Task.class);
            verify(taskRepository, times(1)).save(taskArgumentCaptor.capture());

            Task capturedTask = taskArgumentCaptor.getValue();
            assertThat(capturedTask.getTitle()).isEqualTo("期待するタイトル");
            assertThat(capturedTask.getDescription()).isEqualTo("期待する内容");

        }
    }

    @Nested
    @DisplayName("updateTaskのテスト")
    class UpdateTaskTests {
        // テストしたいメソッドごとに付与するアノテーション
        @Test
        // テストケースに名前を付けるために付与するアノテーション
        @DisplayName("IDでタスクが正常に取得できる場合のテスト")
        void updateTask_Success() {
            // 1. Arrange(準備)
            // まず、ダミーデータを用意
            Task dummyTask = new Task();
            dummyTask.setId(1);
            dummyTask.setTitle("更新前");

            // 同じくダミーデータ
            TaskUpdateDto dummyUpdateDto = new TaskUpdateDto();
            dummyUpdateDto.setId(1);
            dummyUpdateDto.setTitle("更新後");

            Task updatedTask = new Task();
            updatedTask.setId(1);
            updatedTask.setTitle("更新後");

            // 「 taskRepositoryのfindById(1) が呼ばれたら、dummyTaskをOptionalでラップして返してください。」
            // と命令しておく
            when(taskRepository.findById(1L)).thenReturn(Optional.of(dummyTask));
            when(taskMapper.updateTaskFromUpdateDto(dummyTask, dummyUpdateDto)).thenReturn(updatedTask);

            // 2. Act(実行)
            // ここで、上で命令したことが実行され、それによって得られた値が期待値と一致するかこの後で検証する
            taskService.updateTask(1L, dummyUpdateDto);

            verify(taskRepository, times(1)).findById(1L);
            verify(taskMapper, times(1)).updateTaskFromUpdateDto(dummyTask, dummyUpdateDto);

            ArgumentCaptor<Task> argumentCaptor = ArgumentCaptor.forClass(Task.class);
            verify(taskRepository, times(1)).save(argumentCaptor.capture());

            Task capturedTask = argumentCaptor.getValue();
            assertThat(capturedTask.getId()).isEqualTo(1);
            assertThat(capturedTask.getTitle()).isEqualTo("更新後");
        }

        @Test
        @DisplayName("IDでタスクが見つからない場合にTaskNotFoundExceptionをちゃんとスローするかについてのテスト")
        void updateTask_ThrowsTaskUpdateDtoNotFoundException() {
            final long NON_EXISTENT_ID = 99L;
            TaskUpdateDto taskUpdateDto = new TaskUpdateDto();
            taskUpdateDto.setId(NON_EXISTENT_ID);

            when(taskRepository.findById(NON_EXISTENT_ID)).thenReturn(Optional.empty());

            // 対象の例外がちゃんとスローするか検証するためのメソッド
            assertThrows(TaskNotFoundException.class, () -> taskService.updateTask(NON_EXISTENT_ID, taskUpdateDto));

            // 例外がスローされるとしても findById は一回呼び出されるので、その点を検証
            verify(taskRepository, times(1)).findById(NON_EXISTENT_ID);

            // 例外がスローされる場合はtoTaskUpdateDtoは呼び出されないので、その点を検証
            // anyは「引数がどんな値でも成立する」ことを表した記述の仕方
            verify(taskMapper, never()).toTaskUpdateDto(any());
        }
    }









}