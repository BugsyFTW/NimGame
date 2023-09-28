package com.mateus.holisticon.NimGame.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.mateus.holisticon.NimGame.ai.strategies.StrategyType;
import com.mateus.holisticon.NimGame.model.NimGame.PlayerType;

@Service
public class AppConfig {
	
	@Autowired
	private Environment env;
	
	public int getStartingMatches(Integer other) {
		int matches = Integer.parseInt(env.getProperty("game.default.starting_matches"));
		
		if (other != null && other > 0) {
			matches = other;
		}
		return matches;
	}
	
	public int getMatchesPerTurn(Integer other) {
		int per_turn = Integer.parseInt(env.getProperty("game.default.matches_per_turn"));
		
		if (other != null) {
			if (isNumberInRange(other, getMinMatchesToRemove(), getMaxMatchesToRemove()) ) {
				per_turn = other;	
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Max allowed matches_per_turn is: " + per_turn);
			}
		}
		return per_turn;
	}
	
	public StrategyType getStrategy(String other) {
		StrategyType stratType = StrategyType.valueOf(env.getProperty("game.default.ai.strategy").toUpperCase());
		
		if (other != null) {
			try {
				StrategyType newType = StrategyType.valueOf(other); 
				stratType = newType;
			} catch (IllegalArgumentException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Strategy type: " + other + ". Does not exist!");
			}
		}
		return stratType;
	}
	
	public PlayerType getFirstTurnPlayer(String other) {
		Integer ordinal = Integer.parseInt(env.getProperty("game.turn.first_player_type"));
		
		if (other != null) {
			try {
				PlayerType newPlayer = PlayerType.valueOf(other);
				return newPlayer;
			} catch (IllegalArgumentException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Player type: " + other + ". Does not exist!");
			}
		}
		return PlayerType.values()[ordinal];
	}
	
	public int getMinMatchesToRemove() {
		return Integer.parseInt(env.getProperty("game.min_matches_per_turn"));
	}
	
	public int getMaxMatchesToRemove() {
		return Integer.parseInt(env.getProperty("game.max_matches_per_turn"));
	}	
	
	public boolean isNumberInRange(int number, int min, int max) {
	    return number >= min && number <= max;
	}
}
