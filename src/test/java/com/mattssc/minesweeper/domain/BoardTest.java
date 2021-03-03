package com.mattssc.minesweeper.domain;

import com.mattssc.minesweeper.domain.exceptions.CellExplodedException;
import com.mattssc.minesweeper.domain.exceptions.MarkedCellException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BoardTest {

    @Test
    public void createBoard(){
        var board = new Board(2, 2, 2);

        Assertions.assertEquals(board.getColumns() * board.getRows(), board.getCells().size());
        Assertions.assertEquals(board.getMines(), board.getCells().stream().filter(Cell::isMine).count());
    }

    @Test
    public void showAllBombs(){
        var board = new Board(2, 2, 2);
        board.showAllBombs();

        Assertions.assertEquals(
                board.getMines(),
                board.getCells().stream()
                        .filter(Cell::isMine)
                        .filter(cell -> cell.getStatus().equals(CellStatus.BOOM))
                        .count()
        );
        Assertions.assertEquals(
                2,
                board.getCells().stream()
                        .filter(cell -> cell.getStatus().equals(CellStatus.CLOSED))
                        .count()
        );
    }

    @Test
    public void markFlagCell(){
        var board = new Board(2, 2, 2);

        var cell = board.getCells().stream()
                .filter(c -> !c.isMine())
                .findFirst()
                .get();

        board.flagMarkCell(cell.getRow(), cell.getColumn());

        Assertions.assertEquals(CellStatus.FLAG_MARKED, cell.getStatus());
    }

    @Test
    public void dismarkCell(){
        var board = new Board(2, 2, 2);

        var cell = board.getCells().stream()
                .filter(c -> !c.isMine())
                .findFirst()
                .get();

        board.flagMarkCell(cell.getRow(), cell.getColumn());
        board.dismarkCell(cell.getRow(), cell.getColumn());

        Assertions.assertEquals(CellStatus.CLOSED, cell.getStatus());
    }

    @Test
    public void questionMarkCell(){
        var board = new Board(2, 2, 2);

        var cell = board.getCells().stream()
                .filter(c -> !c.isMine())
                .findFirst()
                .get();

        board.questionMarkCell(cell.getRow(), cell.getColumn());

        Assertions.assertEquals(CellStatus.QUESTION_MARKED, cell.getStatus());
    }

    @Test
    public void openCell() throws MarkedCellException, CellExplodedException {
        var board = new Board(2, 2, 2);

        var cell = board.getCells().stream()
                .filter(c -> !c.isMine())
                .findFirst()
                .get();

        board.openCell(cell.getRow(), cell.getColumn());

        Assertions.assertEquals(CellStatus.OPEN, cell.getStatus());
    }
}
