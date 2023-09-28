package com.mateus.holisticon.NimGame.api.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.mateus.holisticon.NimGame.ai.strategies.StrategyType;
import com.mateus.holisticon.NimGame.model.NimGame.NimGame;
import com.mateus.holisticon.NimGame.model.NimGame.NimGameRepository;
import com.mateus.holisticon.NimGame.model.NimGame.PlayerType;

@DataJpaTest
@AutoConfigureTestDatabase( connection = EmbeddedDatabaseConnection.H2 )
public class NimGameRepositoryTests {
	
	@Autowired
	private NimGameRepository repo;
	
	@Test
	public void NimGame_Get() {
		NimGame game = NimGame.builder()
				.matches(13)
				.taken_per_turn(3)
				.strategy(StrategyType.RANDOM)
				.currentPlayer(PlayerType.PLAYER)
				.build();
		
		NimGame savedGame = repo.save(game);
		
		NimGame getGame = repo.findById(savedGame.getId()).orElse(null);
		
		assertThat(getGame).isNotNull();
		assertThat(savedGame.getId()).isEqualTo(getGame.getId());
	}
	
	@Test
	public void NimGame_Create() {
		
		NimGame game = NimGame.builder()
				.matches(13)
				.taken_per_turn(3)
				.strategy(StrategyType.RANDOM)
				.currentPlayer(PlayerType.PLAYER)
				.build();
		
		NimGame savedGame = repo.save(game);
		
		assertThat(savedGame).isNotNull();
		assertThat(savedGame.getId()).isGreaterThan(0);
		
	}
	
	@Test
	public void NimGame_Update() {
		NimGame game = NimGame.builder()
				.matches(13)
				.taken_per_turn(3)
				.strategy(StrategyType.RANDOM)
				.currentPlayer(PlayerType.PLAYER)
				.build();
		
		NimGame savedGame = repo.save(game);
		
		savedGame.removeMatches(3);
		
		NimGame updatedGame = repo.save(savedGame);
		
		assertThat(savedGame.getId()).isEqualTo(updatedGame.getId());
		assertThat(updatedGame.getMatches()).isEqualTo(10);
	}	
}
