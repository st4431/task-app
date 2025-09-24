package com.takahata.task_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
// 引数なしのデフォルトコンストラクタを自動生成する
@NoArgsConstructor
// すべてのフィールドを引数に持つコンストラクタを自動生成する
@AllArgsConstructor
public class LoginResponseDto {
    private String jwt;
}
