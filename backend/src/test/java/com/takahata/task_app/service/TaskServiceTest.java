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

    final long FIRST_DUMMY_ID = 1L;
    final long SECOND_DUMMY_ID = 2L;
    final int ONE_INTERACTIONS_WITH_THIS_MOCK = 1;

    @Nested
    @DisplayName("findAllのテスト")
    class FindAllTests {
        @Test
        @DisplayName("タスクが存在する場合、すべてのタスクが返されること")
        void findAll_Success() {
            List<Task> expectedTasks = new ArrayList<>();
            Task dummyTask1 = new Task();
            dummyTask1.setId(FIRST_DUMMY_ID);
            dummyTask1.setTitle("test1");
            expectedTasks.add(dummyTask1);
            Task dummyTask2 = new Task();
            dummyTask2.setId(SECOND_DUMMY_ID);
            dummyTask2.setTitle("test2");
            expectedTasks.add(dummyTask2);

            when(taskRepository.findAll()).thenReturn(expectedTasks);

            List<Task> actualList = taskService.findAll();

            verify(taskRepository, times(ONE_INTERACTIONS_WITH_THIS_MOCK)).findAll();

            assertThat(actualList.size()).isEqualTo(expectedTasks.size());
            assertThat(actualList).isEqualTo(expectedTasks);
        }

        @Test
        @DisplayName("タスクが存在しない場合、空のリストが返されること")
        void findAll_ReturnsEmptyList_WhenNoTasksExist() {
            when(taskRepository.findAll()).thenReturn(Collections.emptyList());

            List<Task> actualList = taskService.findAll();

            verify(taskRepository, times(ONE_INTERACTIONS_WITH_THIS_MOCK)).findAll();

            assertThat(actualList).isEmpty();
            assertThat(actualList).isNotNull();
        }

        @Test
        @DisplayName("全取得の際、TaskRepositoryが例外をスローした場合、findAllも同様に例外をスローするか")
        void findAll_ThrowsException_WhenRepositoryThrowsException() {
            when(taskRepository.findAll()).thenThrow(new RuntimeException("Database error"));

            assertThrows(RuntimeException.class, () -> taskService.findAll());

            verify(taskRepository, times(ONE_INTERACTIONS_WITH_THIS_MOCK)).findAll();
        }
    }

    @Nested
    @DisplayName("createTaskのテスト")
    class CreateTaskTests {
        @Test
        @DisplayName("新規登録のためにregisterNewTaskが機能し、オブジェクトの内容が一致するかのテスト")
        void createTask_Success() {
            TaskInputDto dummyInputDto = new TaskInputDto();
            dummyInputDto.setTitle("Expected title");
            dummyInputDto.setDescription("Expected description");

            Task dummyTask = new Task();
            dummyTask.setTitle("Expected title");
            dummyTask.setDescription("Expected description");

            when(taskMapper.fromInputDtoToTask(dummyInputDto)).thenReturn(dummyTask);

            taskService.createTask(dummyInputDto);

            verify(taskMapper, times(ONE_INTERACTIONS_WITH_THIS_MOCK)).fromInputDtoToTask(dummyInputDto);

            // 実現値が期待値と一致するか検証するため、 ArgumentCaptor を使用してRepositoryに渡された実際の引数（Taskオブジェクト）を捕まえる
            ArgumentCaptor<Task> taskArgumentCaptor = ArgumentCaptor.forClass(Task.class);
            verify(taskRepository, times(ONE_INTERACTIONS_WITH_THIS_MOCK)).save(taskArgumentCaptor.capture());

            Task capturedTask = taskArgumentCaptor.getValue();
            assertThat(capturedTask.getTitle()).isEqualTo(dummyTask.getTitle());
            assertThat(capturedTask.getDescription()).isEqualTo(dummyTask.getDescription());

        }
    }

    @Nested
    @DisplayName("deleteTaskのテスト")
    class DeleteTaskTests {
        @Test
        @DisplayName("正常にタスクを削除できる場合")
        void deleteTask_Success() {
            doNothing().when(taskRepository).deleteById(anyLong());

            taskService.deleteTask(FIRST_DUMMY_ID);

            verify(taskRepository, times(ONE_INTERACTIONS_WITH_THIS_MOCK)).deleteById(FIRST_DUMMY_ID);
        }

    }

    @Nested
    @DisplayName("updateTaskのテスト")
    class UpdateTaskTests {
        // テストしたいメソッドごとに付与するアノテーション
        @Test
        // テストケースに名前を付けるために付与するアノテーション
        @DisplayName("正常にタスクが更新できる場合")
        void updateTask_Success() {
            // 1. Arrange(準備)
            // まず、ダミーデータを用意
            Task dummyTask = new Task();
            dummyTask.setId(FIRST_DUMMY_ID);
            dummyTask.setTitle("Before updated");

            // 同じくダミーデータ
            TaskUpdateDto dummyUpdateDto = new TaskUpdateDto();
            dummyUpdateDto.setId(FIRST_DUMMY_ID);
            dummyUpdateDto.setTitle("Updated");

            Task expectedTask = new Task();
            expectedTask.setId(FIRST_DUMMY_ID);
            expectedTask.setTitle("Updated");

            // 「 taskRepositoryのfindById(1) が呼ばれたら、dummyTaskをOptionalでラップして返してください。」
            // と命令しておく
            when(taskRepository.findById(FIRST_DUMMY_ID)).thenReturn(Optional.of(dummyTask));
            when(taskMapper.updateTaskFromUpdateDto(dummyTask, dummyUpdateDto)).thenReturn(expectedTask);

            // 2. Act(実行)
            // ここで、上で命令したことが実行され、それによって得られた値が期待値と一致するかこの後で検証する
            taskService.updateTask(FIRST_DUMMY_ID, dummyUpdateDto);

            verify(taskRepository, times(ONE_INTERACTIONS_WITH_THIS_MOCK)).findById(FIRST_DUMMY_ID);
            verify(taskMapper, times(ONE_INTERACTIONS_WITH_THIS_MOCK)).updateTaskFromUpdateDto(dummyTask, dummyUpdateDto);

            ArgumentCaptor<Task> argumentCaptor = ArgumentCaptor.forClass(Task.class);
            verify(taskRepository, times(ONE_INTERACTIONS_WITH_THIS_MOCK)).save(argumentCaptor.capture());

            Task capturedTask = argumentCaptor.getValue();
            assertThat(capturedTask.getId()).isEqualTo(expectedTask.getId());
            assertThat(capturedTask.getTitle()).isEqualTo(expectedTask.getTitle());
        }

        @Test
        @DisplayName("IDでタスクが見つからない場合にTaskNotFoundExceptionをちゃんとスローするかについてのテスト")
        void updateTask_ThrowsTaskUpdateDtoNotFoundException() {
            final long NON_EXISTENT_ID = 99L;
            TaskUpdateDto dummyUpdateDto = new TaskUpdateDto();
            dummyUpdateDto.setId(NON_EXISTENT_ID);

            when(taskRepository.findById(NON_EXISTENT_ID)).thenReturn(Optional.empty());

            // 対象の例外がちゃんとスローするか検証するためのメソッド
            assertThrows(TaskNotFoundException.class, () -> taskService.updateTask(NON_EXISTENT_ID, dummyUpdateDto));

            // 例外がスローされるとしても findById は一回呼び出されるので、その点を検証
            verify(taskRepository, times(ONE_INTERACTIONS_WITH_THIS_MOCK)).findById(NON_EXISTENT_ID);

            // 例外がスローされる場合はtoTaskUpdateDtoは呼び出されないので、その点を検証
            // anyは「引数がどんな値でも成立する」ことを表した記述の仕方
            verify(taskMapper, never()).updateTaskFromUpdateDto(any(Task.class), any(TaskUpdateDto.class));
            verify(taskRepository, never()).save(any(Task.class));
        }

        @Test
        @DisplayName("一部のフィールドだけが更新され、他がnullだった場合")
        void updateTask_ShouldUpdateFieldsToNull_WhenDtoFieldsAreNull() {
            Task dummyTask = new Task();
            dummyTask.setId(FIRST_DUMMY_ID);
            dummyTask.setTitle("Before updated title");
            dummyTask.setDescription("Before updated description");

            TaskUpdateDto dummyUpdateDto = new TaskUpdateDto();
            dummyUpdateDto.setId(FIRST_DUMMY_ID);
            dummyUpdateDto.setTitle("Updated title");
            dummyUpdateDto.setDescription(null);

            Task expectedTask = new Task();
            expectedTask.setId(FIRST_DUMMY_ID);
            expectedTask.setTitle("Updated description");
            expectedTask.setDescription(null);

            when(taskRepository.findById(FIRST_DUMMY_ID)).thenReturn(Optional.of(dummyTask));
            when(taskMapper.updateTaskFromUpdateDto(dummyTask, dummyUpdateDto)).thenReturn(expectedTask);

            taskService.updateTask(FIRST_DUMMY_ID, dummyUpdateDto);


            ArgumentCaptor<Task> argumentCaptor = ArgumentCaptor.forClass(Task.class);
            verify(taskRepository, times(ONE_INTERACTIONS_WITH_THIS_MOCK)).save(argumentCaptor.capture());

            Task capturedTask = argumentCaptor.getValue();
            assertThat(capturedTask.getId()).isEqualTo(expectedTask.getId());
            assertThat(capturedTask.getTitle()).isEqualTo(expectedTask.getTitle());
            assertThat(capturedTask.getDescription()).isEqualTo(expectedTask.getDescription());

        }
    }
}