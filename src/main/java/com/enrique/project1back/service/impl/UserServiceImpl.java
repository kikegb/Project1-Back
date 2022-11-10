package com.enrique.project1back.service.impl;

import com.enrique.project1back.model.User;
import com.enrique.project1back.repository.UserRepository;
import com.enrique.project1back.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User add(final User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return null;
        }
        return userRepository.insert(user);
    }

    @Override
    public User findByEmail(final String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User findById(final String id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User updateName(final String id, final String name) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            user.get().setName(name);
            return userRepository.save(user.get());
        }
        return null;
    }

    @Override
    public int deleteById(final String id) {
        return userRepository.deleteUserById(id);
    }

    @Override
    public User update(final User user) {
        return userRepository.save(user);
    }
}
