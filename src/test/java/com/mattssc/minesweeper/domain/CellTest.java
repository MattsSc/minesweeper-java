package com.mattssc.minesweeper.domain;

import com.mattssc.minesweeper.domain.exceptions.CellExplodedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CellTest {

    @Test
    public void createNewCell(){
        var cell = new Cell(0,0);

        Assertions.assertEquals(0, cell.getColumn());
        Assertions.assertEquals(0, cell.getRow());
        Assertions.assertEquals(0, cell.getValue());
        Assertions.assertEquals(CellStatus.CLOSED, cell.getStatus());
        Assertions.assertFalse(cell.isMine());
    }

    @Test
    public void validateCellIsInSpecificPosition(){
        var cell = new Cell(0,0);

        Assertions.assertFalse(cell.isMyPosition(1, 1));
        Assertions.assertTrue(cell.isMyPosition(0, 0));
    }

    @Test
    public void validateCellIsMyNeighbour(){
        var cell = new Cell(1,1);

        Assertions.assertTrue(cell.isMyNeighbour(new Cell(0, 0)));
        Assertions.assertTrue(cell.isMyNeighbour(new Cell(0, 1)));
        Assertions.assertTrue(cell.isMyNeighbour(new Cell(0, 2)));
        Assertions.assertTrue(cell.isMyNeighbour(new Cell(1, 0)));
        Assertions.assertTrue(cell.isMyNeighbour(new Cell(1, 2)));
        Assertions.assertTrue(cell.isMyNeighbour(new Cell(2, 0)));
        Assertions.assertTrue(cell.isMyNeighbour(new Cell(2, 1)));
        Assertions.assertTrue(cell.isMyNeighbour(new Cell(2, 2)));


        Assertions.assertFalse(cell.isMyNeighbour(new Cell(4, 4)));
        Assertions.assertFalse(cell.isMyNeighbour(new Cell(3, 3)));
        Assertions.assertFalse(cell.isMyNeighbour(new Cell(1, 5)));
    }

    @Test
    public void openCell() throws CellExplodedException {
        var cell = new Cell(1,1);
        cell.open();

        Assertions.assertEquals(CellStatus.OPEN, cell.getStatus());
    }

    @Test
    public void openBombCell(){
        var cell = new Cell(1,1);
        cell.setMine(Boolean.TRUE);

        Exception exception = assertThrows(CellExplodedException.class, cell::open);

        Assertions.assertEquals(CellStatus.BOOM, cell.getStatus());
    }
}
