package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setBirthDate(LocalDate.of(1990, 1, 1));
    }

    @Test
    public void testFindUsersByBirthDateRange() {
        LocalDate startDate = LocalDate.of(1989, 1, 1);
        LocalDate endDate = LocalDate.of(1991, 1, 1);
        when(userRepository.findByBirthDateBetween(startDate, endDate)).thenReturn(Arrays.asList(user));

        List<User> result = userService.findUsersByBirthDateRange(startDate, endDate);
        assertEquals(1, result.size());
        verify(userRepository, times(1)).findByBirthDateBetween(startDate, endDate);
    }

    @Test
    public void testCreateUser() {
        when(userRepository.save(user)).thenReturn(user);
        User createdUser = userService.createUser(user);
        assertNotNull(createdUser);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testUpdateUserFields() {
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        user.setFirstName("Jane");
        User updatedUser = userService.updateUserFields(1L, user);
        assertNotNull(updatedUser);
        assertEquals("Jane", updatedUser.getFirstName());
    }
}