package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/task")
public class TaskController {

    @Autowired
    private DbService service;
    @Autowired
    private TaskMapper taskMapper;

    @GetMapping("getTasks")
    public List<TaskDto> getTasks(){
        return taskMapper.mapToTaskDtoList(service.getAllTasks());
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
