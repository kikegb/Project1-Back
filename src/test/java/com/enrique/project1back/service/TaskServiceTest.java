package com.enrique.project1back.service;

import com.enrique.project1back.model.Task;
import com.enrique.project1back.repository.TaskRepository;
import com.enrique.project1back.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService = new TaskServiceImpl();

    List<Task> taskList = Arrays.asList(
            new Task("1", "2", "Homework", "Do homework, like every day.", false),
            new Task("2", "2", "Wash car", "Go wash the car, it's disgusting.", false),
            new Task("3", "4", "Take the dog for a walk", "Take the dog for a walk, please.", true)
    );


    @DisplayName("Test add task: Success")
    @Test
    public void testAdd() {
        Task task = taskList.get(0);
        when(taskRepository.save(any(Task.class))).then(AdditionalAnswers.returnsFirstArg());

        assertEquals(task, taskService.add(task));
        verify(taskRepository).save(task);
    }

    @DisplayName("Test find by user id: Success")
    @Test
    public void testFindByUserId() {
        List<Task> tasks = taskList.subList(0,2);
        when(taskRepository.findByUserId(tasks.get(0).getUserId())).thenReturn(tasks);

        assertEquals(tasks, taskService.findByUserId(tasks.get(0).getUserId()));
        verify(taskRepository).findByUserId(tasks.get(0).getUserId());
    }

    @DisplayName("Test find by id: Success")
    @Test
    public void testFindById() {
        Task task = taskList.get(1);
        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));

        assertEquals(task, taskService.findById(task.getId()));
        verify(taskRepository).findById(task.getId());
    }

    @DisplayName("Test find by id: Id not exists error")
    @Test
    public void testFindByInvalidId() {
        Task task = taskList.get(1);
        when(taskRepository.findById(task.getId())).thenReturn(Optional.empty());

        assertNull(taskService.findById(task.getId()));
        verify(taskRepository).findById(task.getId());
    }

    @DisplayName("Test find all: Success")
    @Test
    public void testFindAll() {
        when(taskRepository.findAll()).thenReturn(taskList);

        List<Task> result = taskService.findAll();
        assertEquals(3, result.size());
        assertEquals(taskList, result);
        verify(taskRepository).findAll();
    }

    @DisplayName("Test find all: Success empty")
    @Test
    public void testFindAllEmpty() {
        when(taskRepository.findAll()).thenReturn(new ArrayList<>());

        List<Task> result = taskService.findAll();
        assertEquals(0, result.size());
        assertEquals(List.of(), result);
        verify(taskRepository).findAll();
    }

    @DisplayName("Test update done: Success")
    @Test
    public void testUpdateName() {
        Task task = taskList.get(2);
        Task updatedTask = new Task(
                task.getId(),
                task.getUserId(),
                task.getTitle(),
                task.getDescription(),
                !task.isDone()
        );
        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).then(AdditionalAnswers.returnsFirstArg());

        Task result = taskService.updateDone(task.getId(), updatedTask.isDone());
        assertEquals(updatedTask, result);
        verify(taskRepository).findById(task.getId());
        verify(taskRepository).save(updatedTask);
    }

    @DisplayName("Test update name: Id not exists error")
    @Test
    public void testUpdateNameNonExistentId() {
        Task task = taskList.get(2);
        Task updatedTask = new Task(
                task.getId(),
                task.getUserId(),
                task.getTitle(),
                task.getDescription(),
                !task.isDone()
        );
        when(taskRepository.findById(task.getId())).thenReturn(Optional.empty());

        assertNull(taskService.updateDone(task.getId(), updatedTask.isDone()));
        verify(taskRepository).findById(task.getId());
        verifyNoMoreInteractions(taskRepository);
    }

    @DisplayName("Test delete by id: Success")
    @Test
    public void testDeleteById() {
        Task task = taskList.get(0);
        when(taskRepository.deleteTaskById(task.getId())).thenReturn(1);

        assertEquals(1, taskService.deleteById(task.getId()));
        verify(taskRepository).deleteTaskById(task.getId());
    }

    @DisplayName("Test delete by id: Id not exists error")
    @Test
    public void testDeleteByNonExistentId() {
        Task task = taskList.get(0);
        when(taskRepository.deleteTaskById(task.getId())).thenReturn(0);

        assertEquals(0, taskService.deleteById(task.getId()));
        verify(taskRepository).deleteTaskById(task.getId());
    }

    @DisplayName("Test update: Success")
    @Test
    public void testUpdate() {
        Task task = taskList.get(1);
        Task updatedTask = new Task(
                task.getId(),
                "41",
                "Wash car ASAP",
                "It's time, you can't see through the windows.",
                true);
        when(taskRepository.save(any(Task.class))).then(AdditionalAnswers.returnsFirstArg());

        assertEquals(updatedTask, taskService.update(updatedTask));
        verify(taskRepository).save(updatedTask);
    }
}
