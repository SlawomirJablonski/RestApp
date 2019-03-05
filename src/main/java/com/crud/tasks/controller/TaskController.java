package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/task")
public class TaskController {
    @GetMapping("getTasks")
    public List<TaskDto> getTasks(){
        return new ArrayList<>();
    }

    @GetMapping("getTask")
    public TaskDto getTask(Long taskId){
        return new TaskDto(1L,"test title","test_content");
    }

    @DeleteMapping("deleteTask")
    public void deleteTask(Long taskId){

    }

    @PutMapping("updateTask")
    public TaskDto updateTask(@RequestBody TaskDto taskDto){
        return new TaskDto(1L,"Edited test title","test content");
    }

    @PostMapping("createTask")
    public void createTask(@RequestBody TaskDto taskDto){

    }

}
