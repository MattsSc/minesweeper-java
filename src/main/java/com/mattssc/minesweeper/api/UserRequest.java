package com.mattssc.minesweeper.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public class UserRequest {
    @JsonProperty("username")
    @Schema
    private String username;

    public UserRequest() {
    }

    public UserRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
