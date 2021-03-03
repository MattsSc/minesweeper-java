package com.mattssc.minesweeper.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public class ActionRequest {

    @JsonProperty("action")
    @Schema
    private ActionType action;

    @JsonProperty("row")
    @Schema
    private int row;

    @JsonProperty("column")
    @Schema
    private int column;

    public ActionRequest() {
    }

    public ActionType getAction() {
        return action;
    }

    public void setAction(ActionType action) {
        this.action = action;
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
}
