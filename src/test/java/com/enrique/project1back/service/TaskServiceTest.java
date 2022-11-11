package com.enrique.project1back.service;

import com.enrique.project1back.model.Task;
import com.enrique.project1back.repository.TaskRepository;
import com.enrique.project1back.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    List<Task> taskList = Arrays.asList(
            new Task("1", "2", "Homework", "Do homework, like every day.", false),
            new Task("2", "2", "Wash car", "Go wash the car, it's disgusting.", false),
            new Task("3", "4", "Take the dog for a walk", "Take the dog for a walk, please.", true)
    );

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService = new TaskServiceImpl();

    @BeforeEach
    public void setUpMock() {
        Mockito.lenient().when(taskRepository.save(any(Task.class))).then(AdditionalAnswers.returnsFirstArg());
    }

    @DisplayName("Test add task")
    @Test
    public void testAdd() {
        Task task = taskList.get(0);

        assertEquals(task, taskService.add(task));
        verify(taskRepository).save(task);
    }

    @DisplayName("Test find by user id")
    @Test
    public void testFindByUserId() {
        List<Task> tasks = taskList.subList(0,2);
        when(taskRepository.findByUserId(tasks.get(0).getUserId())).thenReturn(tasks);

        assertEquals(tasks, taskService.findByUserId(tasks.get(0).getUserId()));
        verify(taskRepository).findByUserId(tasks.get(0).getUserId());
    }
}
