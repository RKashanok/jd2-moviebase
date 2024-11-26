package com.jd2.moviebase.service;

import com.jd2.moviebase.dto.AccountDto;
import com.jd2.moviebase.model.Account;
import com.jd2.moviebase.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private CommentService commentService;
    @Mock
    private AccountMovieService accountMovieService;

    @InjectMocks
    private AccountService accountService;

    private Account account;
    private AccountDto accountDto;

    @BeforeEach
    void setUp() {
        account = Account.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .preferredName("JD")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .phone("1234567890")
                .gender("Male")
                .photoUrl("http://example.com/photo.jpg")
                .build();

        accountDto = AccountDto.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .preferredName("JD")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .phone("1234567890")
                .gender("Male")
                .photoUrl("http://example.com/photo.jpg")
                .build();
    }

    @Test
    void create_ShouldSaveAccountAndReturnDto() {
        when(accountRepository.create(any(Account.class))).thenReturn(account);

        AccountDto result = accountService.create(accountDto);

        assertNotNull(result);
        assertEquals(accountDto.getId(), result.getId());
        verify(accountRepository, times(1)).create(any(Account.class));
    }

    @Test
    void findById_ShouldReturnAccountDto_WhenAccountExists() {
        when(accountRepository.findById(1L)).thenReturn(account);

        AccountDto result = accountService.findById(1L);

        assertNotNull(result);
        assertEquals(accountDto.getId(), result.getId());
        verify(accountRepository, times(1)).findById(1L);
    }

    @Test
    void findByUserId_ShouldReturnAccountDto_WhenAccountExists() {
        when(accountRepository.findByUserId(1L)).thenReturn(account);

        AccountDto result = accountService.findByUserId(1L);

        assertNotNull(result);
        assertEquals(accountDto.getId(), result.getId());
        verify(accountRepository, times(1)).findByUserId(1L);
    }

    @Test
    void update_ShouldUpdateAccountAndReturnDto() {
        when(accountRepository.update(any(Account.class))).thenReturn(account);

        AccountDto result = accountService.update(1L, accountDto);

        assertNotNull(result);
        assertEquals(accountDto.getId(), result.getId());
        verify(accountRepository, times(1)).update(any(Account.class));
    }

    @Test
    void deleteById_ShouldCallCommentAndAccountMovieServicesAndDeleteAccount() {
        doNothing().when(commentService).deactivateByAccId(1L);
        doNothing().when(accountMovieService).deleteByAccId(1L);
        doNothing().when(accountRepository).deleteById(1L);

        accountService.deleteById(1L);

        verify(commentService, times(1)).deactivateByAccId(1L);
        verify(accountMovieService, times(1)).deleteByAccId(1L);
        verify(accountRepository, times(1)).deleteById(1L);
    }
}