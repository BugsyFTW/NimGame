package com.mateus.holisticon.NimGame.restcontroller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mateus.holisticon.NimGame.ai.strategies.IStrategy;
import com.mateus.holisticon.NimGame.ai.strategies.StrategyType;
import com.mateus.holisticon.NimGame.config.AppConfig;
import com.mateus.holisticon.NimGame.model.NimGame.NimGame;
import com.mateus.holisticon.NimGame.model.NimGame.NimGameManagerDto;
import com.mateus.holisticon.NimGame.model.NimGame.NimGameManagerDto.PutOutput;

import com.mateus.holisticon.NimGame.model.NimGame.NimGameRepository;
import com.mateus.holisticon.NimGame.model.NimGame.NimGameService;
import com.mateus.holisticon.NimGame.model.NimGame.PlayerType;

@RestController
@RequestMapping( value = "api/gamemanager" )
public class GameManagerRestController {
	
	@Autowired
	private AppConfig config;
	
	@Autowired
	private NimGameRepository nimGameRepository;
	
	@Autowired
	private NimGameService nimGameService;
	
	private final Map<StrategyType, IStrategy> strategyMap;
	
	@Autowired
	public GameManagerRestController(Map<StrategyType, IStrategy> strategyMap) {
		this.strategyMap = strategyMap;
	}
	
	@GetMapping
	public NimGameManagerDto.GetOutput get(@RequestParam Long id) {
		if (id == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No game id expecified!");
		}
		
		NimGame game = nimGameRepository.findById(id).orElse(null);
		if (game == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No game with ID: " + id + " found!");
		}
		
		return NimGameManagerDto.GetOutput.builder()
				.id(game.getId())
				.isRunning(game.isRunning())
				.winner(game.getWinner())
				.nextPlayer(game.getCurrentPlayer())
				.matchesLeft(game.getMatches())
				.turnsTaken(game.getTurn())
				.strategy(game.getStrategy())
				.build();
	}
	
	@PostMapping
	public NimGameManagerDto.Output createGame(@RequestBody( required = false ) NimGameManagerDto.Input input) {
		
		int matches = config.getStartingMatches(input != null ? input.getStarting_matches() : null);
		int per_turn = config.getMatchesPerTurn(input != null ? input.getMatches_per_turn() : null);
		
		StrategyType strategy = config.getStrategy(input != null ? input.getStrategy() : null);
		PlayerType firstTurnPlayer = config.getFirstTurnPlayer(input != null ? input.getFirstPlayer() : null);
		
		NimGame game = nimGameRepository.save(NimGame.builder()
				.matches(matches)
				.taken_per_turn(per_turn)
				.strategy(strategy)
				.currentPlayer(firstTurnPlayer)
				.build());
		
		return NimGameManagerDto.Output.builder()
				.id(game.getId())
				.current_matches(game.getMatches())
				.matches_per_turn(game.getTaken_per_turn())
				.strategy(game.getStrategy())
				.currentPlayer(game.getCurrentPlayer())
				.build();
	}
	
	@PutMapping
	public PutOutput putGame(@RequestBody NimGameManagerDto.PutInput input) {
		NimGameManagerDto.PutOutput output = null;
		
		if (input.getGame_id() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No game id expecified!");
		}
		
		NimGame game = nimGameRepository.findById(input.getGame_id()).orElse(null);
		if (game == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No game with ID: " + input.getGame_id() + " found!");
		}
		
		//Check if game ended
		if (game.isRunning() == false) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Game with ID: " + input.getGame_id() + " has already ended!");
		}
		
		// Player turn
		if (game.getCurrentPlayer() == PlayerType.PLAYER) {
			
			if (!config.isNumberInRange(input.getRemove_matches(), config.getMinMatchesToRemove(), game.getTaken_per_turn())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The taken ammount is not within the specified game range");
			}
			
			game.removeMatches(input.getRemove_matches());
			
			output = nimGameService.setWinnerOrTakeTurn(game);
		} // Computer turn
		else if (game.getCurrentPlayer() == PlayerType.COMPUTER) {
			
			IStrategy strat = getGameStrategy(game.getStrategy());
			int toRemove = strat.makeMove(game);
			
			game.removeMatches(toRemove);
			
			output = nimGameService.setWinnerOrTakeTurn(game);
		}
		
		nimGameRepository.save(game);
		
		return output;
	}
	

	
	private IStrategy getGameStrategy(StrategyType stratType) {
		return strategyMap.get(stratType);
	}
}
