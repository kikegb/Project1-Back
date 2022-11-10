package com.enrique.project1back.controller;

import com.enrique.project1back.model.Task;
import com.enrique.project1back.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/task")
@AllArgsConstructor
public class TaskController {

    private TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> add(@RequestBody Task task) {
        return ResponseEntity.ok(taskService.add(task));
    }

    @GetMapping("/findByUserId")
    public ResponseEntity<List<Task>> findByUserId(@RequestParam String userId) {
        return ResponseEntity.ok(taskService.findByUserId(userId));
    }

    @GetMapping("/findById")
    public ResponseEntity<?> findById(@RequestParam String id) {
        Task task = taskService.findById(id);
        if (task == null) {
            return new ResponseEntity<>(ResponseCode.NOT_FOUND_ID, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(task);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Task>> findAll() {
        return ResponseEntity.ok(taskService.findAll());
    }

    @PutMapping
    public ResponseEntity<Task> update(@RequestBody Task task) {
        return ResponseEntity.ok(taskService.update(task));
    }

    @PatchMapping
    public ResponseEntity<?> updateDone(@RequestParam String id, @RequestParam boolean done) {
        Task task = taskService.updateDone(id, done);
        if (task == null) {
            return new ResponseEntity<>(ResponseCode.NOT_FOUND_ID, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(task);
    }

    @DeleteMapping
    public ResponseEntity<ResponseCode> deleteById(@RequestParam String id) {
        if (taskService.deleteById(id) < 1) {
            return new ResponseEntity<>(ResponseCode.NOT_FOUND_ID, HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(ResponseCode.OK);
        }
    }
}
