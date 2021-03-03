package com.mattssc.minesweeper.service;

import com.mattssc.minesweeper.domain.*;
import com.mattssc.minesweeper.domain.repository.GameRepository;
import com.mattssc.minesweeper.domain.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.times;

public class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GameService gameService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void getGame(){
        Mockito.when(gameRepository.findById(anyObject())).thenReturn(Optional.of(new Game()));

        gameService.getGame(1L);

        Mockito.verify(gameRepository, times(1)).findById(1L);
        Mockito.verifyNoMoreInteractions(gameRepository);
        Mockito.verifyNoInteractions(userRepository);
    }

    @Test
    public void createGameOk(){
        User user = new User();
        user.setId(1L);
        user.setUsername("test");
        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        Mockito.when(gameRepository.save(anyObject())).thenReturn(new Game());

        var response = gameService.createGame(1L, 5, 5, 4);

        Assertions.assertEquals(user, response.getUser());
        Assertions.assertEquals(GameStatus.IN_PROGRESS, response.getStatus());
        Assertions.assertEquals(0, response.getMoves());

        Mockito.verify(gameRepository, times(1)).save(anyObject());
        Mockito.verify(userRepository, times(1)).findById(1L);
        Mockito.verifyNoMoreInteractions(gameRepository);
        Mockito.verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void createGameUserNotFound(){
        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            gameService.createGame(1L, 5, 5, 4);
        });

        Assertions.assertTrue(exception.getMessage().contains("User does not exist"));
        Mockito.verify(userRepository, times(1)).findById(1L);
        Mockito.verifyNoInteractions(gameRepository);
        Mockito.verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void createGameMoreMinesThanCells(){
        User user = new User();
        user.setId(1L);
        user.setUsername("test");
        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            gameService.createGame(1L, 5, 5, 25);
        });

        Assertions.assertTrue(exception.getMessage().contains("Mines can not be greater than cells"));
        Mockito.verify(userRepository, times(1)).findById(1L);
        Mockito.verifyNoInteractions(gameRepository);
        Mockito.verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void flagMarkClosedCell(){
        var cell = new Cell();
        cell.setColumn(0);
        cell.setRow(0);
        cell.setStatus(CellStatus.CLOSED);


        var board = new Board();
        board.setCells(List.of(cell));

        var game = new Game();
        game.setBoard(board);

        Mockito.when(gameRepository.findById(anyLong())).thenReturn(Optional.of(game));


        var response = gameService.setFlagMarkCell(1L,0,0);

        Assertions.assertEquals(CellStatus.FLAG_MARKED, cell.getStatus());
        Mockito.verify(gameRepository, times(1)).findById(1L);
        Mockito.verify(gameRepository, times(1)).save(game);
        Mockito.verifyNoMoreInteractions(gameRepository);
        Mockito.verifyNoInteractions(userRepository);
    }

    @Test
    public void questionMarkClosedCell(){
        var cell = new Cell();
        cell.setColumn(0);
        cell.setRow(0);
        cell.setStatus(CellStatus.CLOSED);


        var board = new Board();
        board.setCells(List.of(cell));

        var game = new Game();
        game.setBoard(board);

        Mockito.when(gameRepository.findById(anyLong())).thenReturn(Optional.of(game));


        var response = gameService.setQuestionMarkCell(1L,0,0);

        Assertions.assertEquals(CellStatus.QUESTION_MARKED, cell.getStatus());
        Mockito.verify(gameRepository, times(1)).findById(1L);
        Mockito.verify(gameRepository, times(1)).save(game);
        Mockito.verifyNoMoreInteractions(gameRepository);
        Mockito.verifyNoInteractions(userRepository);
    }

    @Test
    public void openClosedCell(){
        var cell = new Cell();
        cell.setColumn(0);
        cell.setRow(0);
        cell.setStatus(CellStatus.CLOSED);


        var board = new Board();
        board.setCells(List.of(cell));

        var game = new Game();
        game.setBoard(board);
        game.setStatus(GameStatus.IN_PROGRESS);

        Mockito.when(gameRepository.findById(anyLong())).thenReturn(Optional.of(game));


        var response = gameService.openCell(1L,0,0);

        Assertions.assertEquals(CellStatus.OPEN, cell.getStatus());
        Assertions.assertEquals(GameStatus.WON, game.getStatus());
        Mockito.verify(gameRepository, times(1)).findById(1L);
        Mockito.verify(gameRepository, times(1)).save(game);
        Mockito.verifyNoMoreInteractions(gameRepository);
        Mockito.verifyNoInteractions(userRepository);
    }

    @Test
    public void dismarkCell(){
        var cell = new Cell();
        cell.setColumn(0);
        cell.setRow(0);
        cell.setStatus(CellStatus.QUESTION_MARKED);


        var board = new Board();
        board.setCells(List.of(cell));

        var game = new Game();
        game.setBoard(board);

        Mockito.when(gameRepository.findById(anyLong())).thenReturn(Optional.of(game));


        var response = gameService.dismarkCell(1L,0,0);

        Assertions.assertEquals(CellStatus.CLOSED, cell.getStatus());
        Mockito.verify(gameRepository, times(1)).findById(1L);
        Mockito.verify(gameRepository, times(1)).save(game);
        Mockito.verifyNoMoreInteractions(gameRepository);
        Mockito.verifyNoInteractions(userRepository);
    }

    @Test
    public void flagMarkClosedCellGameNotFound(){
        Mockito.when(gameRepository.findById(anyLong())).thenReturn(Optional.empty());

        var response = gameService.setFlagMarkCell(1L,0,0);

        Mockito.verify(gameRepository, times(1)).findById(1L);
        Mockito.verify(gameRepository, times(0)).save(anyObject());
        Mockito.verifyNoMoreInteractions(gameRepository);
        Mockito.verifyNoInteractions(userRepository);
    }

    @Test
    public void openCellGameNotFound(){
        Mockito.when(gameRepository.findById(anyLong())).thenReturn(Optional.empty());

        var response = gameService.openCell(1L,0,0);

        Mockito.verify(gameRepository, times(1)).findById(1L);
        Mockito.verify(gameRepository, times(0)).save(anyObject());
        Mockito.verifyNoMoreInteractions(gameRepository);
        Mockito.verifyNoInteractions(userRepository);
    }

    @Test
    public void dismarkCellGameNotFound(){
        Mockito.when(gameRepository.findById(anyLong())).thenReturn(Optional.empty());

        var response = gameService.dismarkCell(1L,0,0);

        Mockito.verify(gameRepository, times(1)).findById(1L);
        Mockito.verify(gameRepository, times(0)).save(anyObject());
        Mockito.verifyNoMoreInteractions(gameRepository);
        Mockito.verifyNoInteractions(userRepository);
    }

    @Test
    public void questionMarkCellGameNotFound(){
        Mockito.when(gameRepository.findById(anyLong())).thenReturn(Optional.empty());

        var response = gameService.dismarkCell(1L,0,0);

        Mockito.verify(gameRepository, times(1)).findById(1L);
        Mockito.verify(gameRepository, times(0)).save(anyObject());
        Mockito.verifyNoMoreInteractions(gameRepository);
        Mockito.verifyNoInteractions(userRepository);
    }

}
