package com.enrique.project1back.service;

import com.enrique.project1back.model.Task;

import java.util.List;

public interface TaskService {
    Task add(Task task);
    List<Task> findByUserId(String userId);
    Task findById(String id);
    Task update(Task task);
    Task updateDone(String id, boolean done);
    int deleteById(String id);
    List<Task> findAll();
}
