package com.mateus.holisticon.NimGame.model.NimGame;

import com.mateus.holisticon.NimGame.ai.strategies.StrategyType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

public class NimGameManagerDto {
	
	@Data
	@SuperBuilder
	public static class GetOutput {
		public long id;
		public boolean isRunning;
		private PlayerType winner;
		private PlayerType nextPlayer;
		private int matchesLeft;
		private int turnsTaken;
		private StrategyType strategy;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Input {
		private Integer starting_matches;
		private Integer matches_per_turn;
		private String strategy;
		private String firstPlayer;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@SuperBuilder
	public static class Output {
		private long id;
		private int current_matches;
		private int matches_per_turn;
		private StrategyType strategy;
		private PlayerType currentPlayer;
	}
	
	@Data
	public static class PutInput {
		private Long game_id;
		private Integer remove_matches;
		private PlayerType currentPlayer;
	}
	
	@Data
	@SuperBuilder	
	public static class PutOutput {
		private Long gameId;
		private PlayerType winner;
		private PlayerType nextPlayer;
		private int matchesLeft;
		private int turnsTaken;
		private boolean running;
		
	}
}
