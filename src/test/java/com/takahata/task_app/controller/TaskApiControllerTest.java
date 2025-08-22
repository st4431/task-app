package com.takahata.task_app.controller;

import com.takahata.task_app.entity.Task;
import com.takahata.task_app.service.TaskService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//Spring Bootのテスト機能を有効化するためのアノテーション
//具体的には、アプリケーション実行時と同じように、コンテナの中にすべてのインスタンスをDIする
@SpringBootTest

//ControllerにHTTPリクエストを送るための仮装Postmanを有効化する
@AutoConfigureMockMvc
public class TaskApiControllerTest {
    //HTTPリクエストを送るためにDIする必要のあるオブジェクト
    //@AutoConfigureMockMvcと関係している
    @Autowired
    MockMvc mockMvc;

    //テストしたいクラスのMockをDIし、本物のインスタンスと置き換えるためのアノテーション
    @MockitoBean
    TaskService taskService;

    @Test
    @DisplayName("タスク一覧APIが、タスクリストをJSONで返し、ステータス200を返すこと")
    void findAllTasks_Success() throws Exception {
        Task task1 = new Task();
        task1.setId(1);
        task1.setTitle("Test Task1");
        Task task2 = new Task();
        task2.setId(2);
        task2.setTitle("Test Task2");

        when(taskService.findAll()).thenReturn(List.of(task1, task2));

        mockMvc.perform(get("/api/tasks"))//HTTPリクエストの実行を依頼
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))//「$」はJSON全体を表す
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Test Task1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].title", is("Test Task2")));
    }





}
