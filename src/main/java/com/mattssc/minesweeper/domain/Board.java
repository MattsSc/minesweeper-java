package com.mattssc.minesweeper.domain;

import com.mattssc.minesweeper.domain.exceptions.CellExplodedException;
import com.mattssc.minesweeper.domain.exceptions.MarkedCellException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="total_rows")
    private int rows;

    @Column(name="total_columns")
    private int columns;

    @Column
    private int mines;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Cell> cells;

    public Board() {}

    public Board(int rows, int columns, int mines) {
        this.rows = rows;
        this.columns = columns;
        this.mines = mines;
        cells = new ArrayList<>();
        generateCellsAndMines();
    }

    public void flagMarkCell(int row, int column){
        changeCellStatus(row, column, CellStatus.FLAG_MARKED);
    }

    public void questionMarkCell(int row, int column){
        changeCellStatus(row, column, CellStatus.QUESTION_MARKED);
    }

    public void dismarkCell(int row, int column) {
        changeCellStatus(row, column, CellStatus.CLOSED);
    }

    public void openCell(int row, int column) throws CellExplodedException, MarkedCellException {
        var cell = cells.stream()
                .filter(c -> c.isMyPosition(row, column))
                .findFirst().get();

        if(cell.getStatus().equals(CellStatus.CLOSED)){
            cell.open();
            if(cell.getValue() == 0){
                getCellNeighbours(cell)
                        .forEach(this::openNeighbours);
            }
        }else{
            throw new MarkedCellException();
        }
    }

    public void showAllBombs() {
        this.cells.stream()
                .filter(Cell::isMine)
                .forEach(Cell::openBomb);
    }

    public boolean isAllOpened() {
        return this.cells.stream()
                .filter(cell -> !cell.isMine())
                .noneMatch(cell -> cell.getStatus().equals(CellStatus.CLOSED));
    }

    private void changeCellStatus(int row, int column, CellStatus questionMarked) {
        var cell = cells.stream()
                .filter(c -> c.isMyPosition(row, column))
                .findFirst();

        if (cell.isPresent() && !cell.get().getStatus().equals(CellStatus.OPEN) && !cell.get().getStatus().equals(questionMarked))
            cell.get().setStatus(questionMarked);
    }

    private void generateCellsAndMines(){
        generateBlankCells();
        var minesCount = 0;
        while( minesCount < mines){
            var randomIndex = new Random().ints(0,cells.size() -1).findFirst().getAsInt();
            var cell = cells.get(randomIndex);
            if(!cell.isMine()){
                cell.setMine(Boolean.TRUE);
                updateBombNeighbours(cell);
                minesCount ++;
            }
        }
    }

    private void generateBlankCells(){
        for (int currRow = 0; currRow < rows; currRow++) {
            for (int currColumn = 0; currColumn < columns; currColumn++) {
                cells.add(new Cell(currRow, currColumn));
            }
        }
    }

    private void updateBombNeighbours(Cell bombCell){
        getCellNeighbours(bombCell)
                .filter(cell -> !cell.isMine())
                .forEach(Cell::addNeighbourBomb);
    }

    private Stream<Cell> getCellNeighbours(Cell cell){
        return cells.stream().filter(c-> c.isMyNeighbour(cell));
    }

    private void openNeighbours(Cell neighbourCell) {
        try {
            openCell(neighbourCell.getRow(), neighbourCell.getColumn());
        }catch (MarkedCellException ignored){
        } catch (CellExplodedException e) {
            e.printStackTrace();
        }
    }



    //GETTERS AND SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getMines() {
        return mines;
    }

    public void setMines(int mines) {
        this.mines = mines;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }

}
