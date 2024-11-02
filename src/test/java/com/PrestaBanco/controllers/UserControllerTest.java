package com.PrestaBanco.controllers;

import com.PrestaBanco.entities.UserEntity;
import com.PrestaBanco.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllUsers() {
        // Arrange
        ArrayList<UserEntity> users = new ArrayList<>();
        users.add(new UserEntity());
        when(userService.getUsers()).thenReturn(users);

        // Act
        ResponseEntity<List<UserEntity>> response = userController.getAllUsers();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(users, response.getBody());
        verify(userService, times(1)).getUsers();
    }

    @Test
    public void testGetUserById() {
        // Arrange
        Long userId = 1L;
        UserEntity user = new UserEntity();
        when(userService.getUserById(userId)).thenReturn(user);

        // Act
        ResponseEntity<UserEntity> response = userController.getUserById(userId);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(user, response.getBody());
        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    public void testExistsByMail() {
        // Arrange
        String email = "test@example.com";
        when(userService.existsByMail(email)).thenReturn(true);

        // Act
        ResponseEntity<Boolean> response = userController.existsByMail(email);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(true, response.getBody());
        verify(userService, times(1)).existsByMail(email);
    }

    @Test
    public void testGetUserByMail() {
        // Arrange
        String email = "test@example.com";
        UserEntity user = new UserEntity();
        when(userService.getUserByMail(email)).thenReturn(user);

        // Act
        ResponseEntity<UserEntity> response = userController.getUserByMail(email);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(user, response.getBody());
        verify(userService, times(1)).getUserByMail(email);
    }

    @Test
    public void testAddUser() {
        // Arrange
        UserEntity user = new UserEntity();
        when(userService.saveUser(user)).thenReturn(user);

        // Act
        ResponseEntity<UserEntity> response = userController.addUser(user);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(user, response.getBody());
        verify(userService, times(1)).saveUser(user);
    }

    @Test
    public void testUpdateUser() {
        // Arrange
        UserEntity user = new UserEntity();
        when(userService.updateUser(user)).thenReturn(user);

        // Act
        ResponseEntity<UserEntity> response = userController.updateUser(user);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(user, response.getBody());
        verify(userService, times(1)).updateUser(user);
    }

    @Test
    public void testDeleteUser() throws Exception {
        // Arrange
        Long userId = 1L;
        when(userService.deleteUser(userId)).thenReturn(true);

        // Act
        ResponseEntity<Void> response = userController.deleteUser(userId);

        // Assert
        assertEquals(204, response.getStatusCodeValue());
        verify(userService, times(1)).deleteUser(userId);
    }
}

