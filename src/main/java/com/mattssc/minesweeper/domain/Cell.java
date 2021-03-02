package com.mattssc.minesweeper.domain;

public class Cell {

    private enum Status{
        CLOSED,
        OPEN,
        FLAG_MARKED,
        QUESTION_MARKED
    }

    private int row;
    private int column;
    private Status status;

    private boolean isMine;
    private int neighboursMines;

    public Cell() {
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public int getNeighboursMines() {
        return neighboursMines;
    }

    public void setNeighboursMines(int neighboursMines) {
        this.neighboursMines = neighboursMines;
    }
}
