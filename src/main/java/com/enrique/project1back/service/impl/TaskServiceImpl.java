package com.enrique.project1back.service.impl;

import com.enrique.project1back.model.Task;
import com.enrique.project1back.repository.TaskRepository;
import com.enrique.project1back.service.TaskService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Task add(final Task task) {
        return taskRepository.save(task);
    }

    @Override
    public List<Task> findByUserId(final String userId) {
        return taskRepository.findByUserId(userId);
    }

    @Override
    public Task findById(final String id) {
        return taskRepository.findById(id).orElse(null);
    }

    @Override
    public Task update(final Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task updateDone(final String id, final boolean done) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()){
            task.get().setDone(done);
            return taskRepository.save(task.get());
        }
        return null;
    }

    @Override
    public int deleteById(final String id) {
        return taskRepository.deleteTaskById(id);
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }
}
