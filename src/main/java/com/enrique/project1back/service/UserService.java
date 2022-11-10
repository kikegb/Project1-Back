package com.enrique.project1back.service;

import com.enrique.project1back.model.User;

import java.util.List;

public interface UserService {

    User add(User user);
    User findByEmail(String email);
    User findById(String id);
    List<User> findAll();
    User updateName(String id, String name);
    int deleteById(String id);
    User update(User user);
}
