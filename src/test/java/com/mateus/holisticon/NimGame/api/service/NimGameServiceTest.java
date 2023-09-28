package com.mateus.holisticon.NimGame.api.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mateus.holisticon.NimGame.ai.strategies.StrategyType;
import com.mateus.holisticon.NimGame.model.NimGame.NimGame;
import com.mateus.holisticon.NimGame.model.NimGame.NimGameService;
import com.mateus.holisticon.NimGame.model.NimGame.PlayerType;

@SpringBootTest
public class NimGameServiceTest {
	
	@Autowired
	private NimGameService nimGameService;
	
	@Test
	public void NimGame_Service_TakeTurn() {
		
		PlayerType initialPlayer = PlayerType.PLAYER;
		
		NimGame game = NimGame.builder()
				.matches(13)
				.taken_per_turn(3)
				.strategy(StrategyType.RANDOM)
				.currentPlayer(initialPlayer)
				.build();
		
		game.removeMatches(3);		
		int initialTurn = game.getTurn();

		nimGameService.setWinnerOrTakeTurn(game);
		
		assertThat(game.getTurn()).isEqualTo(initialTurn + 1);
		assertThat(game.getCurrentPlayer()).isEqualByComparingTo(PlayerType.COMPUTER);
	}
	
	@Test
	public void NimGame_Service_SetWinner() {
		
		NimGame game = NimGame.builder()
				.matches(1)
				.taken_per_turn(3)
				.strategy(StrategyType.RANDOM)
				.currentPlayer(PlayerType.COMPUTER)
				.build();
		
		game.removeMatches(1);		
		int initialTurn = game.getTurn();

		nimGameService.setWinnerOrTakeTurn(game);
		
		assertThat(game.getWinner()).isEqualByComparingTo(PlayerType.PLAYER);
		assertThat(game.getCurrentPlayer()).isEqualTo(null);
		assertThat(game.isRunning()).isEqualTo(false);
		assertThat(game.getTurn()).isEqualTo(initialTurn);
		
	}
}
