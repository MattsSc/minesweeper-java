package com.mattssc.minesweeper.controller;

import com.mattssc.minesweeper.api.ActionRequest;
import com.mattssc.minesweeper.api.GameRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "/game", tags = { "Game controller" })
@RequestMapping("game")
public class GameController {

    @ApiOperation(value = "newGame", notes = "Creates a new game")
    @PostMapping
    public void createNewGame(@RequestBody GameRequest request) { }

    @ApiOperation(value = "getGame", notes = "Get all the info of a specific game")
    @GetMapping("/{id}")
    public void getGame(@PathVariable Long id) { }

    @ApiOperation(value = "action", notes = "Perform an action in the game")
    @PostMapping("/{id}")
    public void action(@PathVariable Long id, @RequestBody ActionRequest request) { }

}
