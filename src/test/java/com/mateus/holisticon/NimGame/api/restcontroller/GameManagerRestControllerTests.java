package com.mateus.holisticon.NimGame.api.restcontroller;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mateus.holisticon.NimGame.ai.strategies.StrategyType;
import com.mateus.holisticon.NimGame.model.NimGame.NimGame;
import com.mateus.holisticon.NimGame.model.NimGame.NimGameManagerDto;
import com.mateus.holisticon.NimGame.model.NimGame.PlayerType;

@SpringBootTest
@AutoConfigureMockMvc
public class GameManagerRestControllerTests {
	
	public static final String ENDPOINT = "api/gamemanager";
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void NimGameManager_Post_1() throws Exception {
		MvcResult result = this.mockMvc.perform( 
				post("/api/gamemanager"))
				.andExpect(status().isOk())
				.andReturn();
		
		NimGameManagerDto.Output actualResponseBody = objectMapper.readValue(result.getResponse()
				.getContentAsString(), NimGameManagerDto.Output .class);
		actualResponseBody.setId(1);
		
		NimGameManagerDto.Output expectedResponseBody  = NimGameManagerDto.Output.builder()
				.id(1)
				.current_matches(13)
				.matches_per_turn(3)
				.strategy(StrategyType.RANDOM)
				.currentPlayer(PlayerType.PLAYER)
				.build();
		
		assertThat(actualResponseBody).isEqualTo(expectedResponseBody);
	}
	
	@Test
	public void NimGameManager_Post_2() throws Exception {
		
		NimGameManagerDto.Input input = NimGameManagerDto.Input.builder()
				.starting_matches(10)
				.matches_per_turn(1)
				.strategy(StrategyType.WINNING_ORIENTED.name())
				.firstPlayer(PlayerType.COMPUTER.name())
				.build();
		
		MvcResult result = this.mockMvc.perform( 
				post("/api/gamemanager")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(input)))
				.andExpect(status().isOk())
				.andReturn();
		
		NimGameManagerDto.Output actualResponseBody = objectMapper.readValue(result.getResponse()
				.getContentAsString(), NimGameManagerDto.Output .class);
		actualResponseBody.setId(1);
		
		NimGameManagerDto.Output expectedResponseBody  = NimGameManagerDto.Output.builder()
				.id(1)
				.current_matches(10)
				.matches_per_turn(1)
				.strategy(StrategyType.WINNING_ORIENTED)
				.currentPlayer(PlayerType.COMPUTER)
				.build();
		
		assertThat(actualResponseBody).isEqualTo(expectedResponseBody);
	}
	
	@Test
	public void NimGameManager_Post_Input_BadRequest_1() throws Exception {
		
		NimGameManagerDto.Input input = NimGameManagerDto.Input.builder()
				.matches_per_turn(5)
				.build();
		
		this.mockMvc.perform( 
				post("/api/gamemanager")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(input)))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void NimGameManager_Post_Input_BadRequest_2() throws Exception {
		
		NimGameManagerDto.Input input = NimGameManagerDto.Input.builder()
				.matches_per_turn(0)
				.build();
		
		this.mockMvc.perform( 
				post("/api/gamemanager")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(input)))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void NimGameManager_Post_Input_BadRequest_3() throws Exception {
		
		NimGameManagerDto.Input input = NimGameManagerDto.Input.builder()
				.strategy("RANAOSDNASDN")
				.build();
		
		this.mockMvc.perform( 
				post("/api/gamemanager")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(input)))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void NimGameManager_Post_Input_BadRequest_4() throws Exception {

		NimGameManagerDto.Input input = NimGameManagerDto.Input.builder()
				.firstPlayer("HALELEOu")
				.build();
		
		this.mockMvc.perform( 
				post("/api/gamemanager")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(input)))
				.andExpect(status().isBadRequest());
	}
	
	public NimGame createNimGame() throws Exception {
		MvcResult postResult = this.mockMvc.perform( 
				post("/api/gamemanager"))
				.andExpect(status().isOk())
				.andReturn();
		
		return objectMapper.readValue(postResult.getResponse().getContentAsString(), NimGame.class);
	}
	
	public NimGame createNimGame(Integer startingMatches, Integer matchesPerTurn, StrategyType stratType, PlayerType firstPlayer) throws Exception {
		
		NimGameManagerDto.Input input = NimGameManagerDto.Input.builder()
				.starting_matches(startingMatches)
				.matches_per_turn(matchesPerTurn)
				.strategy(stratType != null ? stratType.name() : null)
				.firstPlayer(firstPlayer != null ? firstPlayer.name() : null)
				.build();
		
		MvcResult result = this.mockMvc.perform( 
				post("/api/gamemanager")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(input)))
				.andExpect(status().isOk())
				.andReturn();
		
		return objectMapper.readValue(result.getResponse().getContentAsString(), NimGame.class);
	}	
	
	@Test
	public void NimGameManager_Put_Input_1() throws Exception {

		NimGame game = createNimGame();	
		
		NimGameManagerDto.PutInput input = NimGameManagerDto.PutInput.builder()
				.game_id(game.getId())
				.remove_matches(3)
				.build();
		
		MvcResult result = this.mockMvc.perform(
				put("/api/gamemanager")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(input)))
				.andExpect(status().isOk())
				.andReturn();
		
		NimGameManagerDto.PutOutput actualResponseBody = objectMapper.readValue(result.getResponse()
				.getContentAsString(), NimGameManagerDto.PutOutput .class);
		
		NimGameManagerDto.PutOutput expectedResponseBody  = NimGameManagerDto.PutOutput.builder()
				.gameId(game.getId())
				.winner(null)
				.nextPlayer(PlayerType.COMPUTER)
				.matchesLeft(10)
				.turnsTaken(2)
				.running(true)
				.build();
		
		assertThat(actualResponseBody).isEqualTo(expectedResponseBody);
	}
	
	@Test
	public void NimGameManager_Put_Input_2() throws Exception {
		NimGame game = createNimGame(2, 2, null, null);
		
		NimGameManagerDto.PutInput input = NimGameManagerDto.PutInput.builder()
				.game_id(game.getId())
				.remove_matches(2)
				.build();
		
		MvcResult result = this.mockMvc.perform(
				put("/api/gamemanager")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(input)))
				.andExpect(status().isOk())
				.andReturn();
		
		NimGameManagerDto.PutOutput actualResponseBody = objectMapper.readValue(result.getResponse()
				.getContentAsString(), NimGameManagerDto.PutOutput .class);
		
		NimGameManagerDto.PutOutput expectedResponseBody  = NimGameManagerDto.PutOutput.builder()
				.gameId(game.getId())
				.winner(PlayerType.COMPUTER)
				.nextPlayer(null)
				.matchesLeft(0)
				.turnsTaken(1)
				.running(false)
				.build();
		
		assertThat(actualResponseBody).isEqualTo(expectedResponseBody);
	}
	
	@Test
	public void NimGameManager_Put_BadRequest_1() throws Exception {
		NimGameManagerDto.PutInput input = NimGameManagerDto.PutInput.builder()
				.game_id(null)
				.remove_matches(2)
				.build();
		
		this.mockMvc.perform(
				put("/api/gamemanager")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(input)))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void NimGameManager_Put_BadRequest_2() throws Exception {
		NimGameManagerDto.PutInput input = NimGameManagerDto.PutInput.builder()
				.game_id(684961698L)
				.remove_matches(2)
				.build();
		
		this.mockMvc.perform(
				put("/api/gamemanager")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(input)))
				.andExpect(status().isNotFound());
	}
}
