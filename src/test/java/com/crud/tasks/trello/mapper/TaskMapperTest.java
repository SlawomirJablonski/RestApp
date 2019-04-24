package com.crud.tasks.trello.mapper;

import org.apache.commons.lang3.StringUtils;
import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TaskMapperTest {

    @InjectMocks
    private TaskMapper taskMapper;

    @Test
    public void mapToTaskTest(){
        //Given
        TaskDto taskDto = new TaskDto(128L,"dinner","first course");

        //When
        Task task = taskMapper.mapToTask(taskDto);

        //Then
        Assert.assertEquals(128L, (long)task.getId());
        Assert.assertEquals("dinner",task.getTitle());
        Assert.assertEquals("first course",task.getContent());
    }
    @Test
    public void mapToTaskWhenNullAtributesTest(){
        //Given
        TaskDto taskDto = new TaskDto(null,null,null);

        //When
        Task task = taskMapper.mapToTask(taskDto);

        //Then
        Assert.assertNull(task.getId());
        Assert.assertNull(task.getTitle());
        Assert.assertNull(task.getTitle());
    }
    @Test
    public void mapToTaskDtoTest(){
        //Given
        Task task = new Task(0L,""," ");

        //When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);

        //Then
        Assert.assertTrue(StringUtils.isNotEmpty(taskDto.getId().toString()));
        Assert.assertFalse(StringUtils.isNotEmpty(taskDto.getTitle()));
        Assert.assertTrue(StringUtils.isNotEmpty(taskDto.getContent()));
    }
    @Test
    public void mapToTaskDtoListTest(){
        //Given
        Task schoolTask = new Task(1L,"homework","exercise 6");
        Task footballTeamTask = new Task(2L,"Summer Cup","away match");
        List<Task> taskList = Arrays.asList(schoolTask,footballTeamTask);

        //When
        List<TaskDto> taskDtoList = taskMapper.mapToTaskDtoList(taskList);
        TaskDto firstTaskDto = taskDtoList.get(0);
        TaskDto secondTaskDto = taskDtoList.get(1);

        //Then
        Assert.assertEquals(2,taskDtoList.size());
        Assert.assertEquals("exercise 6",firstTaskDto.getContent());
        Assert.assertEquals("Summer Cup",secondTaskDto.getTitle());
    }
    @Test
    public void mapToTaskDtoListWhenEmptyListTest(){
        //Given
        List<Task> taskList = Collections.emptyList();

        //When
        List<TaskDto> taskDtoList = taskMapper.mapToTaskDtoList(taskList);

        //Then
        Assert.assertEquals(0,taskDtoList.size());
    }
}
