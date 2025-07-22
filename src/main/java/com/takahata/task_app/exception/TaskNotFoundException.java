package com.takahata.task_app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// この例外がController層まで到達した場合、Spring Bootが自動で
// 404 Not Found HTTPステータスコードを返してくれるようになります。
@ResponseStatus(HttpStatus.NOT_FOUND)
public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String message) {
        super(message);
    }
}
