package com.enrique.project1back.repository;

import com.enrique.project1back.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findByUserId(final String userId);
    int deleteTaskById(final String id);
}
