package com.mattssc.minesweeper.domain;

import com.mattssc.minesweeper.domain.exceptions.CellExplodedException;

import javax.persistence.*;

@Entity
public class Cell {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="row_pos")
    private int row;

    @Column(name="column_pos")
    private int column;

    @Enumerated(value = EnumType.STRING)
    private CellStatus status;

    @Column
    private boolean isMine;

    @Column
    private int value;

    public Cell() {
    }

    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
        this.isMine = Boolean.FALSE;
        this.status = CellStatus.CLOSED;
    }

    public Cell(boolean isMine) {
        this.isMine = isMine;
        this.status = CellStatus.CLOSED;
        this.value = 9;
    }

    public boolean isMyPosition(int row, int column){
        return (this.row == row && this.column == column);
    }

    public boolean isMyNeighbour(Cell cell){
        return (Math.abs(this.row - cell.row) <= 1 && Math.abs(this.column - cell.column) <= 1);
    }

    public void open() throws CellExplodedException {
        if(this.isMine){
            this.status = CellStatus.BOOM;
            throw new CellExplodedException();
        }
        this.status = CellStatus.OPEN;
    }

    public void openBomb(){
        if(this.isMine){
            this.status = CellStatus.BOOM;
        }
    }

    public void addNeighbourBomb(){
        this.value++;
    }


    //GETTERS AND SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public CellStatus getStatus() {
        return status;
    }

    public void setStatus(CellStatus status) {
        this.status = status;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
        this.setValue(9);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }


}
