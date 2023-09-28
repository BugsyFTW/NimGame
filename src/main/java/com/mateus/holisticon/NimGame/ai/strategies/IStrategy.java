package com.mateus.holisticon.NimGame.ai.strategies;

import com.mateus.holisticon.NimGame.model.NimGame.NimGame;

public interface IStrategy {
	
	/**
	 * Returns the number of matches removed.

	 * @param game The game in affect
	 * @return the number of matches to remove, > 0 and <= number of matches
	 */
	public int makeMove(NimGame game);
}
