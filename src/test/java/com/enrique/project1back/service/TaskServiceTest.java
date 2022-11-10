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
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService = new TaskServiceImpl();

    @BeforeEach
    void setUpMock() {
        when(taskRepository.save(any(Task.class))).then(AdditionalAnswers.returnsFirstArg());
    }

    @DisplayName("Test add task")
    @Test
    void testAdd() {
        Task task = new Task(
                "1",
                "2",
                "Work",
                "Just that, work.",
                false);

        assertEquals(task, taskService.add(task));
        verify(taskRepository).save(task);
    }

    @DisplayName("Test find by user id")
    @Test
    void testFindByUserId() {
        Task task1 = new Task(
                "1",
                "2",
                "Work",
                "Just that, work.",
                false);
        Task task2 = new Task(
                "2",
                "2",
                "Fix bugs",
                "Go fix those bugs.",
                true);
        List<Task> taskList = List.of(task1, task2);
        when(taskRepository.findByUserId(task1.getUserId())).thenReturn(taskList);

        assertEquals(taskList, taskService.findByUserId(task1.getUserId()));
        verify(taskRepository).findByUserId(task1.getUserId());
    }
}
