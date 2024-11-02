package com.PrestaBanco.services;

import com.PrestaBanco.entities.UserEntity;
import com.PrestaBanco.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private UserEntity user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new UserEntity();
        user.setId(1L);
        user.setMail("test@example.com");
        // Asigna otros campos de UserEntity según sea necesario
    }

    @Test
    public void testGetUsers() {
        ArrayList<UserEntity> users = new ArrayList<>();
        users.add(user);
        when(userRepository.findAll()).thenReturn(users);

        ArrayList<UserEntity> result = userService.getUsers();

        assertEquals(1, result.size());
        assertEquals(user.getMail(), result.get(0).getMail());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testGetUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserEntity result = userService.getUserById(1L);

        assertEquals(user.getMail(), result.getMail());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetUserByMail() {
        when(userRepository.findByMail("test@example.com")).thenReturn(user);

        UserEntity result = userService.getUserByMail("test@example.com");

        assertEquals(user.getMail(), result.getMail());
        verify(userRepository, times(1)).findByMail("test@example.com");
    }

    @Test
    public void testExistsByMail() {
        when(userRepository.existsByMail("test@example.com")).thenReturn(true);

        boolean result = userService.existsByMail("test@example.com");

        assertTrue(result);
        verify(userRepository, times(1)).existsByMail("test@example.com");
    }

    @Test
    public void testSaveUser() {
        when(userRepository.save(user)).thenReturn(user);

        UserEntity result = userService.saveUser(user);

        assertEquals(user.getMail(), result.getMail());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testUpdateUser() {
        when(userRepository.save(user)).thenReturn(user);

        UserEntity result = userService.updateUser(user);

        assertEquals(user.getMail(), result.getMail());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testDeleteUser() throws Exception {
        doNothing().when(userRepository).deleteById(1L);

        boolean result = userService.deleteUser(1L);

        assertTrue(result);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteUserThrowsException() {
        doThrow(new RuntimeException("Error al eliminar")).when(userRepository).deleteById(1L);

        Exception exception = assertThrows(Exception.class, () -> {
            userService.deleteUser(1L);
        });

        assertEquals("Error al eliminar", exception.getMessage());
        verify(userRepository, times(1)).deleteById(1L);
    }
}
