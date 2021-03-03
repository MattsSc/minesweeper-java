package com.mattssc.minesweeper.domain;

import com.mattssc.minesweeper.domain.exceptions.CellExplodedException;
import com.mattssc.minesweeper.domain.exceptions.MarkedCellException;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade= CascadeType.ALL, fetch= FetchType.EAGER, orphanRemoval= true)
    @JoinColumn(name= "board_id", referencedColumnName= "id", nullable= false)
    private Board board;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column
    private int moves;

    @Enumerated(EnumType.STRING)
    private GameStatus status;

    @Column(name= "start_time")
    private LocalDateTime startDateTime;

    @Column(name= "end_time")
    private LocalDateTime endDateTime;

    public Game() {}

    public void startGame(int rows, int columns, int mines) {
        this.board = new Board(rows, columns, mines);
        this.startDateTime = LocalDateTime.now();
        this.status = GameStatus.IN_PROGRESS;
    }

    public Board getBoard() {
        return board;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getMoves() {
        return moves;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }



    public void flagMarkCell(int row, int column){
        this.board.flagMarkCell(row, column);
    }

    public void questionMarkCell(int row, int column){
        this.board.questionMarkCell(row, column);
    }

    public void dismarkCell(int row, int column){
        this.board.dismarkCell(row, column);
    }

    public void openCell(int row, int column){
        try {
            this.board.openCell(row, column);
            if(this.board.isAllOpened()){
                this.endGame(GameStatus.WON);
            }
            this.moves++;
        } catch (MarkedCellException ignored) {
        } catch (CellExplodedException e) {
            endGame(GameStatus.GAME_OVER);
            this.board.showAllBombs();
        }

    }

    private void endGame(GameStatus status){
        this.status = status;
        this.endDateTime = LocalDateTime.now();
    }
}
