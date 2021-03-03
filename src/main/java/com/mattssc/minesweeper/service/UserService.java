package com.mattssc.minesweeper.service;

import com.mattssc.minesweeper.domain.Game;
import com.mattssc.minesweeper.domain.User;
import com.mattssc.minesweeper.domain.repository.GameRepository;
import com.mattssc.minesweeper.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    @Autowired
    public UserService(UserRepository userRepository, GameRepository gameRepository) {
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    public User newUser(String username) {
        var user = new User();
        user.setUsername(username);
        userRepository.save(user);
        return user;
    }

    public List<Game> getGamesForUser(Long userId) {
        return gameRepository.findByUser(userId);
    }
}
