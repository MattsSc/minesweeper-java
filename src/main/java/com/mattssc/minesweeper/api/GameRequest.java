package com.mattssc.minesweeper.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public class GameRequest {

    @JsonProperty("rows")
    @Schema
    private int rows;

    @JsonProperty("columns")
    @Schema
    private int columns;

    @JsonProperty("mines")
    @Schema
    private int mines;


    public GameRequest() {}

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
}
