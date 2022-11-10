package com.enrique.project1back.repository;

import com.enrique.project1back.model.Task;
import com.enrique.project1back.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;

@Component
public class DbInit {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskRepository taskRepository;

    @PostConstruct
    private void postConstruct() {
        User u1 = new User("1", "Helena Nito", "hnito56", "hnito@gmail.com", LocalDate.now().minusYears(21));
        User u2 = new User("2", "Leo Carteles", "lcarteles01", "lcarteles@gmail.com",  LocalDate.now().minusYears(15));
        User u3 = new User("3", "Alejandro Medario", "amedario9", "amedario@gmail.com", LocalDate.now().minusYears(60));
        User u4 = new User("4", "Encarna Vales", "evales123", "evales@gmail.com", LocalDate.now().minusYears(32));
        userRepository.save(u1);
        userRepository.save(u2);
        userRepository.save(u3);
        userRepository.save(u4);
        Task t1 = new Task("1", "2", "Homework", "Do homework, like every day.", false);
        Task t2 = new Task("2", "2", "Wash car", "Go wash the car, it's disgusting.", false);
        Task t3 = new Task("3", "4", "Take the dog for a walk", "Take the dog for a walk, it's not an asocial animal like you.", true);
        taskRepository.save(t1);
        taskRepository.save(t2);
        taskRepository.save(t3);
    }

    @PreDestroy
    private void destroy() {
        userRepository.deleteAll();
        taskRepository.deleteAll();
    }
}
