package com.enrique.project1back.service;

import com.enrique.project1back.model.User;
import com.enrique.project1back.repository.UserRepository;
import com.enrique.project1back.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService = new UserServiceImpl();

    @BeforeEach
    void setUpMock() {
        when(userRepository.insert(any(User.class))).then(AdditionalAnswers.returnsFirstArg());
    }

    @DisplayName("Test add user")
    @Test
    void testAdd() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        User user = new User(
                "f6496",
                "Mockito User",
                "p4ssw0rd",
                "mUser@hotmail.com",
                LocalDate.of(1998, 4, 12));

        assertEquals(user, userService.add(user));
        verify(userRepository).existsByEmail(user.getEmail());
        verify(userRepository).insert(user);
    }

    @DisplayName("Test add invalid user")
    @Test
    void testAddInvalid() {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);
        User user = new User(
                "f6275",
                "Mockito User",
                "p4ssw0rd",
                "mUser@hotmail.com",
                LocalDate.of(1998, 4, 12));

        assertNull(userService.add(user));
        verify(userRepository).existsByEmail(user.getEmail());
    }

    @DisplayName("Test find by email")
    @Test
    void testFindByEmail() {
        User user = new User(
                "3d400",
                "Mockito User",
                "p4ssw0rd",
                "mUser@hotmail.com",
                LocalDate.of(1998, 4, 12));

        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(user);

        assertEquals(user, userService.findByEmail(user.getEmail()));
        verify(userRepository).findUserByEmail(user.getEmail());
    }

    @DisplayName("Test find by email")
    @Test
    void testFindByNonExistentEmail() {
        User user = new User(
                "3d400",
                "Mockito User",
                "p4ssw0rd",
                "mUser@hotmail.com",
                LocalDate.of(1998, 4, 12));

        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(null);

        assertEquals(null, userService.findByEmail(user.getEmail()));
        verify(userRepository).findUserByEmail(user.getEmail());
    }

    @DisplayName("Test find by id")
    @Test
    void testFindById() {
        User user = new User(
                "3d400",
                "Mockito User",
                "p4ssw0rd",
                "mUser@hotmail.com",
                LocalDate.of(1998, 4, 12));

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        assertEquals(user, userService.findById(user.getId()));
        verify(userRepository).findById(user.getId());
    }

    @DisplayName("Test find by invalid id")
    @Test
    void testFindByInvalidId() {
        User user = new User(
                "3d400",
                "Mockito User",
                "p4ssw0rd",
                "mUser@hotmail.com",
                LocalDate.of(1998, 4, 12));

        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        assertNull(userService.findById(user.getId()));
        verify(userRepository).findById(user.getId());
    }
}
