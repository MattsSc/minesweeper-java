package com.mattssc.minesweeper.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class GameRequest {

    @JsonProperty("rows")
    @Schema
    @Min(1)
    private int rows;

    @JsonProperty("columns")
    @Schema
    @Min(1)
    private int columns;

    @JsonProperty("mines")
    @Schema
    @Min(1)
    private int mines;

    @JsonProperty("user_id")
    @Schema
    @NotNull
    private Long userId;


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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
