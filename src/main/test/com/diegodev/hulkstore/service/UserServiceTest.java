package com.diegodev.hulkstore.service;

import com.diegodev.hulkstore.SpringConfigurator;
import com.diegodev.hulkstore.model.User;
import com.diegodev.hulkstore.repository.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {SpringConfigurator.class})
public class UserServiceTest {

    @Mock
    UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllUsers() {
        // Arrange
        String stringFilter = "";
        List<User> userList = Arrays.asList(new User(), new User(), new User());
        when(userRepository.findAll()).thenReturn(userList);

        // Act
        List<User> result = userService.findAllUsers(stringFilter);

        // Assert
        assertNotNull(result);
        assertEquals(userList.size(), result.size());
    }

    @Test
    public void testFindAllUsersWithFilter() {
        // Arrange
        String stringFilter = "test";
        List<User> userList = Arrays.asList(new User(), new User(), new User());
        when(userRepository.search(stringFilter)).thenReturn(userList);

        // Act
        List<User> result = userService.findAllUsers(stringFilter);

        // Assert
        assertNotNull(result);
        assertEquals(userList.size(), result.size());
    }

    @Test
    public void testFindByUsername() {
        // Arrange
        String username = "testuser";
        User user = new User();
        user.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(user);

        // Act
        User result = userService.findByUsername(username);

        // Assert
        assertNotNull(result);
        assertEquals(username, result.getUsername());
    }

    @Test
    public void testCountUsers() {
        // Arrange
        long expectedCount = 10;
        when(userRepository.count()).thenReturn(expectedCount);

        // Act
        long result = userService.countUsers();

        // Assert
        assertEquals(expectedCount, result);
    }

    @Test
    public void testDeleteUser() {
        // Arrange
        User user = new User();

        // Act
        userService.deleteUser(user);

        // Assert
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    public void testSaveUser() {
        // Arrange
        User user = new User();

        // Act
        userService.saveUser(user);

        // Assert
        verify(userRepository, times(1)).save(user);
    }
}
