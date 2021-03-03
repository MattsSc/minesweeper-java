package com.mattssc.minesweeper.service;

import com.mattssc.minesweeper.domain.Game;
import com.mattssc.minesweeper.domain.User;
import com.mattssc.minesweeper.domain.repository.GameRepository;
import com.mattssc.minesweeper.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    @Autowired
    public GameService(GameRepository gameRepository, UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }



    public Game createGame(Long userId, int columns, int rows, int mines) {
        var user = validateUser(userId);
        validateMinesQuantity(rows, columns, mines);
        Game game = new Game();
        game.setUser(user);
        game.startGame(rows, columns, mines);
        gameRepository.save(game);

        return game;
    }

    public Optional<Game> setFlagMarkCell(Long gameId, int row, int column) {
        var optionalGame = gameRepository.findById(gameId);
        if(optionalGame.isPresent()){
            var game = optionalGame.get();
            game.flagMarkCell(row, column);
            gameRepository.save(game);
        }
        return optionalGame;
    }

    public Optional<Game> setQuestionMarkCell(Long gameId, int row, int column) {
        var optionalGame = gameRepository.findById(gameId);
        if(optionalGame.isPresent()){
            var game = optionalGame.get();
            game.questionMarkCell(row, column);
            gameRepository.save(game);
        }
        return optionalGame;
    }

    public Optional<Game> dismarkCell(Long gameId, int row, int column) {
        var optionalGame = gameRepository.findById(gameId);
        if(optionalGame.isPresent()){
            var game = optionalGame.get();
            game.dismarkCell(row, column);
            gameRepository.save(game);
        }
        return optionalGame;
    }

    public Optional<Game> openCell(Long gameId, int row, int column) {
        var optionalGame = gameRepository.findById(gameId);
        if(optionalGame.isPresent()){
            var game = optionalGame.get();
            game.openCell(row, column);
            gameRepository.save(game);
        }
        return optionalGame;
    }

    public Optional<Game> getGame(Long id) {
        return gameRepository.findById(id);
    }

    private User validateUser(Long userId) {
        var optionalUser = userRepository.findById(userId);
        if(optionalUser.isPresent()){
           return optionalUser.get();
        }else{
            throw new IllegalArgumentException("User does not exist. You need an account to play this game.");
        }
    }

    private void validateMinesQuantity(int rows, int columns, int mines) {
        if(mines >= columns * rows){
            throw new IllegalArgumentException("Mines can not be greater than cells in board.");
        }
    }

}
