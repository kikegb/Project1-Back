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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    List<User> userList = Arrays.asList(
            new User(
                    "1",
                    "Helena Nito",
                    "hnito56",
                    "hnito@gmail.com",
                    LocalDate.of(1998, 4, 12)),
            new User(
                    "2",
                    "Leo Carteles",
                    "lcarteles01",
                    "lcarteles@gmail.com",
                    LocalDate.of(2001, 7, 20)),
            new User(
                    "3",
                    "Alejandro Medario",
                    "amedario9",
                    "amedario@gmail.com",
                    LocalDate.of(1954, 11, 6)),
            new User(
                    "4",
                    "Encarna Vales",
                    "evales123",
                    "evales@gmail.com",
                    LocalDate.of(1981, 9, 17))
    );

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService = new UserServiceImpl();

    @BeforeEach
    public void setUpMock() {
        Mockito.lenient().when(userRepository.insert(any(User.class))).then(AdditionalAnswers.returnsFirstArg());
    }

    @DisplayName("Test add user")
    @Test
    public void testAdd() {
        User user = userList.get(0);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);

        assertEquals(user, userService.add(user));
        verify(userRepository).existsByEmail(user.getEmail());
        verify(userRepository).insert(user);
    }

    @DisplayName("Test add invalid user")
    @Test
    public void testAddInvalid() {
        User user = userList.get(1);
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        assertNull(userService.add(user));
        verify(userRepository).existsByEmail(user.getEmail());
    }

    @DisplayName("Test find by email")
    @Test
    public void testFindByEmail() {
        User user = userList.get(2);
        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(user);

        assertEquals(user, userService.findByEmail(user.getEmail()));
        verify(userRepository).findUserByEmail(user.getEmail());
    }

    @DisplayName("Test find by email")
    @Test
    public void testFindByNonExistentEmail() {
        User user = userList.get(3);
        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(null);

        assertNull(userService.findByEmail(user.getEmail()));
        verify(userRepository).findUserByEmail(user.getEmail());
    }

    @DisplayName("Test find by id")
    @Test
    public void testFindById() {
        User user = userList.get(0);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        assertEquals(user, userService.findById(user.getId()));
        verify(userRepository).findById(user.getId());
    }

    @DisplayName("Test find by invalid id")
    @Test
    public void testFindByInvalidId() {
        User user = userList.get(1);
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        assertNull(userService.findById(user.getId()));
        verify(userRepository).findById(user.getId());
    }
}
