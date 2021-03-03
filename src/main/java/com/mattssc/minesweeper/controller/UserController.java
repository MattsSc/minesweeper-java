package com.mattssc.minesweeper.controller;

import com.mattssc.minesweeper.api.GameRequest;
import com.mattssc.minesweeper.api.UserRequest;
import com.mattssc.minesweeper.domain.Game;
import com.mattssc.minesweeper.domain.User;
import com.mattssc.minesweeper.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api(value = "/user", tags = { "User controller" })
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "createUser", notes = "Creates a new user")
    @PostMapping
    public ResponseEntity<Long> createsUser(@Valid @RequestBody UserRequest request) {
        User user = userService.newUser(request.getUsername());
        return new ResponseEntity(user.getId(), HttpStatus.OK);
    }

    @ApiOperation(value = "getGamesForUser", notes = "Get all the games for a specific user")
    @GetMapping("/{userId}/games")
    public ResponseEntity<List<Game>> getGamesForUser(@PathVariable Long userId) {
        return new ResponseEntity(userService.getGamesForUser(userId), HttpStatus.OK);
    }
}
