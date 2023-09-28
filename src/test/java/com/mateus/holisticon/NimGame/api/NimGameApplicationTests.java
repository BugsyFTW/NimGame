package com.mateus.holisticon.NimGame.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import com.mateus.holisticon.NimGame.ai.strategies.StrategyType;
import com.mateus.holisticon.NimGame.model.NimGame.NimGame;
import com.mateus.holisticon.NimGame.model.NimGame.NimGameRepository;
import com.mateus.holisticon.NimGame.model.NimGame.PlayerType;

//@SpringBootTest
@DataJpaTest
class NimGameApplicationTests {
	
	@Autowired
	private NimGameRepository nimRepo;
	
	@Test
	void itShouldCreateNewGame() {
		NimGame g = NimGame.builder()
				.matches(3)
				.taken_per_turn(3)
				.strategy(StrategyType.RANDOM)
				.currentPlayer(PlayerType.PLAYER)
				.build();
		
		nimRepo.save(g);
		
		
		 
		assertThat(g.getId()).isNotNull();
	}

}
