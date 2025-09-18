package com.takahata.task_app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

// TODO:
// ①アプリケーション全体の例外処理をこのクラスにまとめる
// ②エラーメッセージを英語に統一する
// ③ src/main/resources にmessages.propertiesファイルを作成し、そこにエラーメッセージをキーと値のペアで定義してまとめる
// ④ messages.propertiesのキーをstaticの変数として定義し、それらをMessagesKeys.javaにまとめる
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleAllExceptions(Exception e) {
        Map<String, String> errorResponse = Map.of("error", "An unexpected internal server error occurred." );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleTaskNotFoundException(TaskNotFoundException e) {
        Map<String, String> errorResponse = Map.of("error", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}
