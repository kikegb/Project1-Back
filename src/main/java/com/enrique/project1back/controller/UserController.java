package com.enrique.project1back.controller;

import com.enrique.project1back.model.User;
import com.enrique.project1back.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping
    public ResponseEntity<?> add(@RequestBody User user) {
        User addedUser = userService.add(user);
        if (addedUser == null) {
            return new ResponseEntity<>(ResponseCode.ALREADY_EXISTENT_USER, HttpStatus.CONFLICT);
        }
        return ResponseEntity.ok(addedUser);
    }

    @GetMapping("/findByEmail")
    public ResponseEntity<?> findUserByEmail(@RequestParam String email) {
        User user = userService.findByEmail(email);
        if (user == null) {
            return new ResponseEntity<>(ResponseCode.NOT_FOUND_EMAIL, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/findById")
    public ResponseEntity<?> findUserById(@RequestParam String id) {
        User user = userService.findById(id);
        if (user == null){
            return new ResponseEntity<>(ResponseCode.NOT_FOUND_ID, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PatchMapping
    public ResponseEntity<?> updateName(@RequestParam String id, @RequestParam String name) {
        User user = userService.updateName(id, name);
        if (user == null) {
            return new ResponseEntity<>(ResponseCode.NOT_FOUND_ID, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping
    public ResponseEntity<User> update(@RequestBody User user) {
        return ResponseEntity.ok(userService.update(user));
    }



    @DeleteMapping
    public ResponseEntity<ResponseCode> deleteById(@RequestParam String id) {
        if (userService.deleteById(id) < 1) {
            return new ResponseEntity<>(ResponseCode.NOT_FOUND_ID, HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(ResponseCode.OK);
        }
    }
}
