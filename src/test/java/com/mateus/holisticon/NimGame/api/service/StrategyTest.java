package com.mateus.holisticon.NimGame.api.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mateus.holisticon.NimGame.ai.strategies.RandomStrategy;
import com.mateus.holisticon.NimGame.ai.strategies.StrategyType;
import com.mateus.holisticon.NimGame.ai.strategies.WinningStrategy;
import com.mateus.holisticon.NimGame.model.NimGame.NimGame;
import com.mateus.holisticon.NimGame.model.NimGame.PlayerType;

@SpringBootTest
public class StrategyTest {
	
	private final int numberOfAssertChecks = 20;
	
	@Autowired
	private RandomStrategy randomStrat;
	
	@Autowired
	private WinningStrategy winStrat;
	
	@Test
	public void NimGame_Strategy_RandomMove() {
		
		NimGame game = NimGame.builder()
				.matches(13)
				.taken_per_turn(5)
				.strategy(StrategyType.RANDOM)
				.currentPlayer(PlayerType.PLAYER)
				.build();
		
		for (int i = 0; i < numberOfAssertChecks; i++) {
			int move = randomStrat.makeMove(game);
			assertThat(move).isBetween(1, game.getTaken_per_turn());
		}
	}
	
	@Test
	public void NimGame_Strategy_WinningMove() {
		
		NimGame game = NimGame.builder()
				.matches(13)
				.taken_per_turn(5)
				.strategy(StrategyType.RANDOM)
				.currentPlayer(PlayerType.PLAYER)
				.build();
		
		for (int i = 0; i < numberOfAssertChecks; i++) {
			int move = winStrat.makeMove(game);
			assertThat(move).isBetween(1, game.getTaken_per_turn());
		}
	}
}
