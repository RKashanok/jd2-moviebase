package com.jd2.moviebase.service;

import com.jd2.moviebase.dto.AccountDto;
import com.jd2.moviebase.exception.MovieDbRepositoryOperationException;
import com.jd2.moviebase.model.User;
import com.jd2.moviebase.repository.UserRepository;
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
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void create_ShouldEncodePasswordAndSaveUser() {
        User user = getUser();
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
        when(userRepository.findById(1L)).thenReturn(Optional.of(getUser()));

        User foundUser = userService.findById(1L);

        assertNotNull(foundUser);
        assertEquals(1L, foundUser.getId());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void findById_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        MovieDbRepositoryOperationException exception = assertThrows(MovieDbRepositoryOperationException.class, () -> userService.findById(1L));
        assertEquals("User not found with id: 1", exception.getMessage());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void findAll_ShouldReturnListOfUsers() {
        when(userRepository.findAll()).thenReturn(List.of(getUser()));

        List<User> users = userService.findAll();

        assertNotNull(users);
        assertEquals(1, users.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void update_ShouldCallRepositoryUpdateMethod() {
        when(userRepository.update(any(User.class))).thenReturn(getUser());

        User updatedUser = userService.update(getUser());

        assertNotNull(updatedUser);
        assertEquals(1L, updatedUser.getId());
        verify(userRepository, times(1)).update(any(User.class));
    }

    @Test
    void deleteById_ShouldCallRelatedServicesAndDeleteUser() {
        when(accountService.findByUserId(1L)).thenReturn(AccountDto.builder().id(1L).userId(1L).firstName("John").lastName("Doe").build());
        doNothing().when(accountService).deleteById(1L);
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteById(1L);

        verify(accountService, times(1)).findByUserId(1L);
        verify(accountService, times(1)).deleteById(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void loadUserByUsername_ShouldReturnUserDetails_WhenUserExists() {
        when(userRepository.findByUserEmail("test@example.com")).thenReturn(Optional.of(getUser()));

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

    private User getUser() {
        return User.builder()
                .id(1L)
                .email("test@example.com")
                .password("password")
                .role("ADMIN")
                .build();
    }
}