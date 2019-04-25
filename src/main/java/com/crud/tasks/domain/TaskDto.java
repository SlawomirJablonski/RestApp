package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class TaskDto {
    private Long id;
    private String title;
    private String content;

    public TaskDto() {
    }
}
