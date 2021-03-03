package com.mattssc.minesweeper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mattssc.minesweeper.api.UserRequest;
import com.mattssc.minesweeper.domain.User;
import com.mattssc.minesweeper.service.GameService;
import com.mattssc.minesweeper.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@AutoConfigureJsonTesters
@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @MockBean
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void createUser() throws Exception {
        User userResponse = new User();
        userResponse.setId(1L);
        Mockito.when(userService.newUser(Mockito.anyString())).thenReturn(userResponse);

        UserRequest userRequest = new UserRequest("username");

        MockHttpServletResponse response  = mockMvc.perform(
                post("/api/minesweeper/user")
                .contextPath("/api/minesweeper")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userRequest)))
                .andReturn().getResponse();

        Assertions.assertEquals(HttpStatus.OK.value(),response.getStatus());
        Assertions.assertEquals(userResponse.getId().toString(), response.getContentAsString());
        Mockito.verify(userService, times(1)).newUser(anyString());
        Mockito.verifyNoMoreInteractions(userService);
        Mockito.verifyNoInteractions(gameService);
    }

    @Test
    public void getGamesForUser() throws Exception {
        Mockito.when(userService.getGamesForUser(Mockito.anyLong())).thenReturn(new ArrayList<>());

        MockHttpServletResponse response  = mockMvc.perform(
                get("/api/minesweeper/user/1/games")
                        .contextPath("/api/minesweeper")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assertions.assertEquals(HttpStatus.OK.value(),response.getStatus());
        Mockito.verify(userService, times(1)).getGamesForUser(anyLong());
        Mockito.verifyNoMoreInteractions(userService);
        Mockito.verifyNoInteractions(gameService);
    }
}
