package com.mateus.holisticon.NimGame.model.NimGame;

import org.springframework.stereotype.Service;

@Service
public class NimGameService {
	
	public NimGameManagerDto.PutOutput setWinnerOrTakeTurn(NimGame game) {
		if (game.getMatches() <= 0) {
			PlayerType winner = getNextPlayer(game);
			game.setWinner(winner);
			
			// Winner return data
			return NimGameManagerDto.PutOutput.builder()
					.gameId(game.getId())
					.winner(winner)
					.turnsTaken(game.getTurn())
					.build();
		} else {
			PlayerType nextPlayer = getNextPlayer(game);
			game.takeTurn(nextPlayer);
			
			// Next turn data
			return NimGameManagerDto.PutOutput.builder()
					.gameId(game.getId())
					.nextPlayer(nextPlayer)
					.matchesLeft(game.getMatches())
					.turnsTaken(game.getTurn())
					.running(true)
					.build();
		}
	}
	
	public PlayerType getNextPlayer(NimGame game) {
		return (game.getCurrentPlayer() == PlayerType.PLAYER) ? PlayerType.COMPUTER : PlayerType.PLAYER;
	}
}
