package com.jd2.moviebase.service;

import com.jd2.moviebase.dto.AccountDto;
import com.jd2.moviebase.model.User;
import com.jd2.moviebase.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private AccountService accountService;
    @Mock
    private CommentService commentService;
    @Mock
    private AccountMovieService accountMovieService;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .email("test@example.com")
                .password("password")
                .build();

    }

    @Test
    void create_ShouldEncodePasswordAndSaveUser() {
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.create(any(User.class))).thenReturn(user);

        User createdUser = userService.create(user);

        assertNotNull(createdUser);
        assertEquals("encodedPassword", user.getPassword());
        verify(passwordEncoder, times(1)).encode("password");
        verify(userRepository, times(1)).create(any(User.class));
    }

    @Test
    void findById_ShouldReturnUser_WhenUserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User foundUser = userService.findById(1L);

        assertNotNull(foundUser);
        assertEquals(1L, foundUser.getId());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void findById_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.findById(1L));
        assertEquals("User not found with id: 1", exception.getMessage());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void findAll_ShouldReturnListOfUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> users = userService.findAll();

        assertNotNull(users);
        assertEquals(1, users.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void update_ShouldCallRepositoryUpdateMethod() {
        when(userRepository.update(any(User.class))).thenReturn(user);

        User updatedUser = userService.update(user);

        assertNotNull(updatedUser);
        assertEquals(1L, updatedUser.getId());
        verify(userRepository, times(1)).update(any(User.class));
    }

    @Test
    void deleteById_ShouldCallRelatedServicesAndDeleteUser() {
        when(accountService.findByUserId(1L)).thenReturn(new AccountDto(1L, 1L, "John", "Doe", "JD", null, null, null, null));
        doNothing().when(commentService).deactivateByAccId(1L);
        doNothing().when(accountMovieService).deleteByAccId(1L);
        doNothing().when(accountService).deleteById(1L);
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteById(1L);

        verify(accountService, times(1)).findByUserId(1L);
        verify(commentService, times(1)).deactivateByAccId(1L);
        verify(accountMovieService, times(1)).deleteByAccId(1L);
        verify(accountService, times(1)).deleteById(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void loadUserByUsername_ShouldReturnUserDetails_WhenUserExists() {
        when(userRepository.findByUserEmail("test@example.com")).thenReturn(Optional.of(user));

        UserDetails userDetails = userService.loadUserByUsername("test@example.com");

        assertNotNull(userDetails);
        assertEquals("test@example.com", userDetails.getUsername());
        verify(userRepository, times(1)).findByUserEmail("test@example.com");
    }

    @Test
    void loadUserByUsername_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findByUserEmail("unknown@example.com")).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("unknown@example.com"));
        assertEquals("User not found with username: unknown@example.com", exception.getMessage());
        verify(userRepository, times(1)).findByUserEmail("unknown@example.com");
    }
}