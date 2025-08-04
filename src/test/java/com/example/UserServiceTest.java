package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User(1L, "Juan Perez", "juan@email.com", 25);
    }

    @Test
    void testGetUserById_ValidId_ReturnsUser() {
        when(userDAO.findById(1L)).thenReturn(testUser);

        User result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals("Juan Perez", result.getName());
        assertEquals("juan@email.com", result.getEmail());
        verify(userDAO, times(1)).findById(1L);
    }

    @Test
    void testGetUserById_InvalidId_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            userService.getUserById(null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            userService.getUserById(0L);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            userService.getUserById(-1L);
        });

        verify(userDAO, never()).findById(anyLong());
    }

    @Test
    void testGetAllUsers_ReturnsUserList() {
        List<User> users = Arrays.asList(
                testUser,
                new User(2L, "Maria Garcia", "maria@email.com", 30)
        );
        when(userDAO.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertEquals(2, result.size());
        assertEquals("Juan Perez", result.get(0).getName());
        assertEquals("Maria Garcia", result.get(1).getName());
        verify(userDAO, times(1)).findAll();
    }

    @Test
    void testCreateUser_ValidData_ReturnsCreatedUser() {
        when(userDAO.findByEmail("nuevo@email.com")).thenReturn(null);
        when(userDAO.save(any(User.class))).thenReturn(
                new User(3L, "Pedro Lopez", "nuevo@email.com", 22)
        );

        User result = userService.createUser("Pedro Lopez", "nuevo@email.com", 22);

        assertNotNull(result);
        assertEquals("Pedro Lopez", result.getName());
        assertEquals("nuevo@email.com", result.getEmail());
        assertEquals(22, result.getAge());
        verify(userDAO, times(1)).findByEmail("nuevo@email.com");
        verify(userDAO, times(1)).save(any(User.class));
    }

    @Test
    void testCreateUser_DuplicateEmail_ThrowsException() {
        when(userDAO.findByEmail("juan@email.com")).thenReturn(testUser);

        assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser("Otro Juan", "juan@email.com", 30);
        });

        verify(userDAO, times(1)).findByEmail("juan@email.com");
        verify(userDAO, never()).save(any(User.class));
    }

    @Test
    void testCreateUser_InvalidData_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(null, "test@email.com", 25);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser("", "test@email.com", 25);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser("Test", "invalid-email", 25);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser("Test", "test@email.com", -1);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser("Test", "test@email.com", 150);
        });

        verify(userDAO, never()).save(any(User.class));
    }

    @Test
    void testDeleteUser_ExistingUser_ReturnsTrue() {
        when(userDAO.findById(1L)).thenReturn(testUser);
        when(userDAO.delete(1L)).thenReturn(true);

        boolean result = userService.deleteUser(1L);

        assertTrue(result);
        verify(userDAO, times(1)).findById(1L);
        verify(userDAO, times(1)).delete(1L);
    }

    @Test
    void testDeleteUser_NonExistingUser_ReturnsFalse() {
        when(userDAO.findById(999L)).thenReturn(null);

        boolean result = userService.deleteUser(999L);

        assertFalse(result);
        verify(userDAO, times(1)).findById(999L);
        verify(userDAO, never()).delete(999L);
    }

    @Test
    void testGetUsersByAge_ValidAge_ReturnsUsers() {
        List<User> users = Arrays.asList(testUser);
        when(userDAO.findByAge(25)).thenReturn(users);

        List<User> result = userService.getUsersByAge(25);

        assertEquals(1, result.size());
        assertEquals(25, result.get(0).getAge());
        verify(userDAO, times(1)).findByAge(25);
    }

    @Test
    void testGetUsersByAge_NegativeAge_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            userService.getUsersByAge(-1);
        });

        verify(userDAO, never()).findByAge(anyInt());
    }

    @Test
    void testIsAdult_AdultUser_ReturnsTrue() {
        when(userDAO.findById(1L)).thenReturn(testUser);

        boolean result = userService.isAdult(1L);

        assertTrue(result);
        verify(userDAO, times(1)).findById(1L);
    }

    @Test
    void testIsAdult_MinorUser_ReturnsFalse() {
        User minorUser = new User(2L, "Ana Menor", "ana@email.com", 17);
        when(userDAO.findById(2L)).thenReturn(minorUser);

        boolean result = userService.isAdult(2L);

        assertFalse(result);
        verify(userDAO, times(1)).findById(2L);
    }

    @Test
    void testIsAdult_NonExistingUser_ReturnsFalse() {
        when(userDAO.findById(999L)).thenReturn(null);

        boolean result = userService.isAdult(999L);

        assertFalse(result);
        verify(userDAO, times(1)).findById(999L);
    }
}