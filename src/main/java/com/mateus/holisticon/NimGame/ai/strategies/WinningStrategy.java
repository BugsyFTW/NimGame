package com.mateus.holisticon.NimGame.ai.strategies;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mateus.holisticon.NimGame.model.NimGame.NimGame;

@Service
@Qualifier("WINNING_ORIENTED")
public class WinningStrategy implements IStrategy {
	
	@Override
	public int makeMove(NimGame game) {
        int nimSum = calculateNimSum(game.getMatches(), game.getTaken_per_turn());

        // If the Nim sum is 0, make a random move
        if (nimSum == 0) {
            return (int) (Math.random() * game.getTaken_per_turn()) + 1;
        }

        // Otherwise, find a move that leaves the Nim sum as 0 (losing position)
        for (int choice = 1; choice <= game.getTaken_per_turn(); choice++) {
            int newHeapSize = game.getMatches() - choice;
            int newNimSum = calculateNimSum(newHeapSize, game.getTaken_per_turn());
            if ((nimSum ^ newNimSum) == 0) {
                return choice;
            }
        }

        // If no such move is found, make a random move
        return (int) (Math.random() * game.getTaken_per_turn()) + 1;
	}

    // Function to calculate the Nim sum recursively
    private int calculateNimSum(int heapSize, int maxMatchesPerTurn) {
        if (heapSize == 0) {
            return 0;
        }
        int nimSum = 0;

        // Calculate the Nim sum for each possible move
        for (int choice = 1; choice <= maxMatchesPerTurn; choice++) {
            if (heapSize >= choice) {
                nimSum ^= calculateNimSum(heapSize - choice, maxMatchesPerTurn);
            }
        }
        return nimSum;
    }
}
