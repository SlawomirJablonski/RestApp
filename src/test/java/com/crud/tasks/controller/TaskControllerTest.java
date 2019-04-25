package com.crud.tasks.controller;

import com.crud.tasks.domain.DeleteTask;
import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService service;

    @MockBean
    private TaskMapper taskMapper;

    @Test
    public void shouldReturnTaskDtoList() throws Exception {
        //Given
        Task autoParts = new Task(99L, "parts", "tires change");
        Task autoSupplies = new Task(100L, "supplies", "oil change");
        List<Task> auto_service = Arrays.asList(autoParts, autoSupplies);
        TaskDto task1 = new TaskDto(99L, "parts", "tires change");
        TaskDto task2 = new TaskDto(100L, "supplies", "oil change");
        List<TaskDto> car_service = Arrays.asList(task1, task2);
        when(service.getAllTasks()).thenReturn(auto_service);
        when(taskMapper.mapToTaskDtoList(auto_service)).thenReturn(car_service);
        //When & Then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void shouldReturnEmptyTask() throws Exception {
        //Given
        TaskDto result = new TaskDto();
        Long taskId = 11L;
        Task taskWithSearchingId = new Task();
        Optional<Task> foundedTask = Optional.of(taskWithSearchingId);
        when(service.getTask(taskId)).thenReturn(foundedTask);
        when(taskMapper.mapToTaskDto(taskWithSearchingId)).thenReturn(result);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(result);

        //When & Then
        mockMvc.perform(get("/v1/task/getTask/{taskId}", taskId).contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(jsonPath("$.id", nullValue()))
                .andExpect(jsonPath("$.title", nullValue()))
                .andExpect(jsonPath("$.content", nullValue()));
    }

    @Test
    public void shouldDeleteTask()throws Exception{
        //Given
        DeleteTask deleteTask = new DeleteTask();
        deleteTask.setTaskId(15L);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(deleteTask);

        //When & Then
        mockMvc.perform(post("/v1/task/createTask").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateTask()throws Exception{
        //Given
        Task task = new Task(12L,"printOrder","Order No 123456");
        TaskDto taskDto = new TaskDto(12L,"printOrder","Order No 123456");
        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        when(service.saveTask(task)).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        Gson gson = new Gson();
        String jContent = gson.toJson(taskDto);

        //When & Then
        mockMvc.perform(put("/v1/task/updateTask").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(12)))
                .andExpect(jsonPath("$.title", is("printOrder")))
                .andExpect(jsonPath("$.content", is("Order No 123456")));
    }

    @Test
    public void shouldCreateTask()throws Exception{
        //Given
        TaskDto taskDto = new TaskDto(12L,"printOrder","Order No 123456");

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc.perform(post("/v1/task/createTask").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }
}