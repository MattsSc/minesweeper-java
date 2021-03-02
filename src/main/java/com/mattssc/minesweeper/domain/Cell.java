package com.mattssc.minesweeper.domain;

import javax.persistence.*;

@Entity
public class Cell {

    private enum Status{
        CLOSED,
        OPEN,
        FLAG_MARKED,
        QUESTION_MARKED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="row_pos")
    private int row;

    @Column(name="column_pos")
    private int column;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    @Column
    private boolean isMine;

    @Column
    private int neighboursMines;

    public Cell() {
    }

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
