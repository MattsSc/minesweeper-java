package com.mattssc.minesweeper.service;

import com.mattssc.minesweeper.domain.User;
import com.mattssc.minesweeper.domain.repository.GameRepository;
import com.mattssc.minesweeper.domain.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.times;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createNewUser(){
        User user = new User();
        user.setUsername("test");
        user.setId(1L);
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        var response = userService.newUser("test");

        Assertions.assertEquals("test", response.getUsername());
        Mockito.verify(userRepository, times(1)).save(anyObject());
        Mockito.verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void getGamesByUser(){
        Mockito.when(gameRepository.findByUser(anyLong())).thenReturn(new ArrayList<>());

        var response = userService.getGamesForUser(1L);

        Mockito.verify(gameRepository, times(1)).findByUser(1L);
        Mockito.verifyNoMoreInteractions(gameRepository);
        Mockito.verifyNoInteractions(userRepository);
    }
}
