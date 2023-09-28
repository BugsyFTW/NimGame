package com.mateus.holisticon.NimGame.ai.strategies;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mateus.holisticon.NimGame.config.AppConfig;
import com.mateus.holisticon.NimGame.model.NimGame.NimGame;

@Service
@Qualifier("RANDOM")
public class RandomStrategy implements IStrategy {
	
	@Autowired
	private AppConfig config;

	@Override
	public int makeMove(NimGame game) {
		Random rand = new Random();
		int numSticksToRemove = rand.nextInt(config.getMinMatchesToRemove(), game.getTaken_per_turn() + 1);
        return numSticksToRemove;
	}
}
