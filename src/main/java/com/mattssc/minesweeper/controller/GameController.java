package com.mattssc.minesweeper.controller;

import com.mattssc.minesweeper.api.ActionRequest;
import com.mattssc.minesweeper.api.GameRequest;
import com.mattssc.minesweeper.domain.Game;
import com.mattssc.minesweeper.service.GameService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@Api(value = "/game", tags = { "Game controller" })
@RequestMapping("game")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @ApiOperation(value = "newGame", notes = "Creates a new game")
    @PostMapping
    public ResponseEntity<Game> createNewGame(@Valid @RequestBody GameRequest request) {
        Game game = gameService.createGame(request.getUserId(), request.getColumns(), request.getRows(), request.getMines());
        return new ResponseEntity(game, HttpStatus.OK);
    }

    @ApiOperation(value = "getGame", notes = "Get all the info of a specific game")
    @GetMapping("/{id}")
    public ResponseEntity<Game> getGame(@PathVariable Long id) {
        var optionalGame = gameService.getGame(id);
        return optionalGame
                .map(game ->new ResponseEntity<>(game, HttpStatus.OK))
                .orElse( new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @ApiOperation(value = "action", notes = "Perform an action in the game")
    @PostMapping("/{id}")
    public ResponseEntity<Game>  action(@PathVariable Long id, @RequestBody ActionRequest request) {
        Optional<Game> optionalGame = switch (request.getAction()){
            case FLAG_MARK -> gameService.setFlagMarkCell(id, request.getRow(), request.getColumn());
            case QUESTION_MARK -> gameService.setQuestionMarkCell(id, request.getRow(), request.getColumn());
            case OPEN -> gameService.openCell(id, request.getRow(), request.getColumn());
            case DISMARK -> gameService.dismarkCell(id, request.getRow(), request.getColumn());
        };

        return optionalGame
                .map(game ->new ResponseEntity<>(game, HttpStatus.OK))
                .orElse( new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
