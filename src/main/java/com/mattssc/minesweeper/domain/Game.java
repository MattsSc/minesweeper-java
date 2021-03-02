package com.mattssc.minesweeper.domain;

import java.time.LocalDateTime;

public class Game {

    private enum Status {
        IN_PROGRESS,
        PAUSED,
        GAME_OVER,
        WON
    }

    private Status status;
    private LocalDateTime startDate;
    private LocalDateTime endTime;
    private User user;


}
