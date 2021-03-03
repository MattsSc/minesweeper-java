package com.mattssc.minesweeper.domain;

import com.mattssc.minesweeper.domain.exceptions.CellExplodedException;
import com.mattssc.minesweeper.domain.exceptions.MarkedCellException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;

public class GameTest {

    @Test
    public void startGame(){
        var game = new Game();
        game.startGame(2,2,2);

        Assertions.assertNotNull(game.getBoard());
        Assertions.assertEquals(0, game.getMoves());
        Assertions.assertEquals(GameStatus.IN_PROGRESS, game.getStatus());
    }

    @Test
    public void flagMarkCell(){
        var board = Mockito.mock(Board.class);
        var game = new Game();
        game.setBoard(board);

        game.flagMarkCell(0,0);

        Mockito.verify(board, times(1)).flagMarkCell(0,0);
        Mockito.verifyNoMoreInteractions(board);

        Assertions.assertEquals(0, game.getMoves());
    }

    @Test
    public void dismarkCell(){
        var board = Mockito.mock(Board.class);
        var game = new Game();
        game.setBoard(board);

        game.dismarkCell(0,0);

        Mockito.verify(board, times(1)).dismarkCell(0,0);
        Mockito.verifyNoMoreInteractions(board);

        Assertions.assertEquals(0, game.getMoves());
    }

    @Test
    public void questionMarkCell(){
        var board = Mockito.mock(Board.class);
        var game = new Game();
        game.setBoard(board);

        game.questionMarkCell(0,0);

        Mockito.verify(board, times(1)).questionMarkCell(0,0);
        Mockito.verifyNoMoreInteractions(board);

        Assertions.assertEquals(0, game.getMoves());
    }

    @Test
    public void openCellButStillPlaying() throws MarkedCellException, CellExplodedException {

        var board = Mockito.mock(Board.class);
        var game = new Game();
        game.setBoard(board);
        game.setStatus(GameStatus.IN_PROGRESS);

        Mockito.when(board.isAllOpened()).thenReturn(Boolean.FALSE);

        game.openCell(0,0);

        Mockito.verify(board, times(1)).openCell(0,0);
        Mockito.verify(board, times(1)).isAllOpened();
        Mockito.verifyNoMoreInteractions(board);

        Assertions.assertEquals(1, game.getMoves());
        Assertions.assertEquals(GameStatus.IN_PROGRESS, game.getStatus());
    }

    @Test
    public void openLastCellAndWin() throws MarkedCellException, CellExplodedException {
        var board = Mockito.mock(Board.class);
        var game = new Game();
        game.setBoard(board);

        Mockito.when(board.isAllOpened()).thenReturn(Boolean.TRUE);

        game.openCell(0,0);

        Mockito.verify(board, times(1)).openCell(0,0);
        Mockito.verify(board, times(1)).isAllOpened();
        Mockito.verifyNoMoreInteractions(board);

        Assertions.assertEquals(1, game.getMoves());
        Assertions.assertEquals(GameStatus.WON, game.getStatus());
    }

    @Test
    public void openBombCellAndLoose() throws MarkedCellException, CellExplodedException {
        var board = Mockito.mock(Board.class);
        var game = new Game();
        game.setBoard(board);

        Mockito.doThrow(CellExplodedException.class).when(board).openCell(anyInt(),anyInt());

        game.openCell(0,0);

        Assertions.assertEquals(1, game.getMoves());
        Assertions.assertEquals(GameStatus.GAME_OVER, game.getStatus());
    }
}
