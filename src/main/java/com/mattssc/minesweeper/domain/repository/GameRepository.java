package com.mattssc.minesweeper.domain.repository;

import com.mattssc.minesweeper.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long>, JpaSpecificationExecutor<Game> {

    @Query(value = "SELECT game FROM Game game WHERE game.user.id = :userId")
    List<Game> findByUser(@Param("userId") Long userId);



}
