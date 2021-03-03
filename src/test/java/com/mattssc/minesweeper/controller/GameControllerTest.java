package com.mattssc.minesweeper.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mattssc.minesweeper.api.ActionRequest;
import com.mattssc.minesweeper.api.ActionType;
import com.mattssc.minesweeper.api.GameRequest;
import com.mattssc.minesweeper.domain.Game;
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

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@AutoConfigureJsonTesters
@WebMvcTest(controllers = GameController.class)
public class GameControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @MockBean
    private UserService userService;

    @InjectMocks
    private GameController gameController;

    @Test
    public void createNewGame() throws Exception {
        Mockito.when(gameService.createGame(Mockito.anyLong(),Mockito.anyInt(),Mockito.anyInt(),Mockito.anyInt()))
                .thenReturn(new Game());

        GameRequest gameRequest = new GameRequest(5, 5, 5, 1L);

        MockHttpServletResponse response  = mockMvc.perform(
                post("/api/minesweeper/game")
                        .contextPath("/api/minesweeper")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(gameRequest)))
                .andReturn().getResponse();

        Assertions.assertEquals(HttpStatus.OK.value(),response.getStatus());
        Mockito.verify(gameService, times(1)).createGame(Mockito.anyLong(),Mockito.anyInt(),Mockito.anyInt(),Mockito.anyInt());
        Mockito.verifyNoMoreInteractions(gameService);
        Mockito.verifyNoInteractions(userService);
    }

    @Test
    public void createNewGameWithoutRowsColumnsAndMines() throws Exception {
        Mockito.when(gameService.createGame(Mockito.anyLong(),Mockito.anyInt(),Mockito.anyInt(),Mockito.anyInt()))
                .thenReturn(new Game());

        GameRequest gameRequest = new GameRequest(0, 0, 0, 1L);

        MockHttpServletResponse response  = mockMvc.perform(
                post("/api/minesweeper/game")
                        .contextPath("/api/minesweeper")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(gameRequest)))
                .andReturn().getResponse();

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatus());

        Mockito.verifyNoInteractions(gameService);
        Mockito.verifyNoInteractions(userService);
    }

    @Test
    public void getGame() throws Exception {
        Mockito.when(gameService.getGame(Mockito.anyLong())).thenReturn(Optional.of(new Game()));

        MockHttpServletResponse response  = mockMvc.perform(
                get("/api/minesweeper/game/1")
                        .contextPath("/api/minesweeper")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assertions.assertEquals(HttpStatus.OK.value(),response.getStatus());
        Mockito.verify(gameService, times(1)).getGame(1L);
        Mockito.verifyNoMoreInteractions(gameService);
        Mockito.verifyNoInteractions(userService);
    }

    @Test
    public void performFlagMarkAction() throws Exception {
        ActionRequest actionRequest = new ActionRequest(ActionType.FLAG_MARK,0, 0);

        Mockito.when(gameService.setFlagMarkCell(anyLong(), anyInt(), anyInt()))
                .thenReturn(Optional.of(new Game()));

        MockHttpServletResponse response  = mockMvc.perform(
                post("/api/minesweeper/game/1")
                        .contextPath("/api/minesweeper")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(actionRequest)))
                .andReturn().getResponse();

        Assertions.assertEquals(HttpStatus.OK.value(),response.getStatus());
        Mockito.verify(gameService, times(1)).setFlagMarkCell(1L, actionRequest.getRow(), actionRequest.getColumn());
        Mockito.verifyNoMoreInteractions(gameService);
        Mockito.verifyNoInteractions(userService);
    }

    @Test
    public void performDismarkAction() throws Exception {

        ActionRequest actionRequest = new ActionRequest(ActionType.DISMARK,0, 0);

        Mockito.when(gameService.dismarkCell(anyLong(), anyInt(), anyInt()))
                .thenReturn(Optional.of(new Game()));

        MockHttpServletResponse response  = mockMvc.perform(
                post("/api/minesweeper/game/1")
                        .contextPath("/api/minesweeper")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(actionRequest)))
                .andReturn().getResponse();

        Assertions.assertEquals(HttpStatus.OK.value(),response.getStatus());
        Mockito.verify(gameService, times(1)).dismarkCell(1L, actionRequest.getRow(), actionRequest.getColumn());
        Mockito.verifyNoMoreInteractions(gameService);
        Mockito.verifyNoInteractions(userService);
    }

    @Test
    public void performQuestionMarkAction() throws Exception {

        ActionRequest actionRequest = new ActionRequest(ActionType.QUESTION_MARK,0, 0);

        Mockito.when(gameService.setQuestionMarkCell(anyLong(), anyInt(), anyInt()))
                .thenReturn(Optional.of(new Game()));

        MockHttpServletResponse response  = mockMvc.perform(
                post("/api/minesweeper/game/1")
                        .contextPath("/api/minesweeper")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(actionRequest)))
                .andReturn().getResponse();

        Assertions.assertEquals(HttpStatus.OK.value(),response.getStatus());
        Mockito.verify(gameService, times(1)).setQuestionMarkCell(1L, actionRequest.getRow(), actionRequest.getColumn());
        Mockito.verifyNoMoreInteractions(gameService);
        Mockito.verifyNoInteractions(userService);
    }

    @Test
    public void performOpenAction() throws Exception {

        ActionRequest actionRequest = new ActionRequest(ActionType.OPEN,0, 0);

        Mockito.when(gameService.openCell(anyLong(), anyInt(), anyInt()))
                .thenReturn(Optional.of(new Game()));

        MockHttpServletResponse response  = mockMvc.perform(
                post("/api/minesweeper/game/1")
                        .contextPath("/api/minesweeper")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(actionRequest)))
                .andReturn().getResponse();

        Assertions.assertEquals(HttpStatus.OK.value(),response.getStatus());
        Mockito.verify(gameService, times(1)).openCell(1L, actionRequest.getRow(), actionRequest.getColumn());
        Mockito.verifyNoMoreInteractions(gameService);
        Mockito.verifyNoInteractions(userService);
    }
}
