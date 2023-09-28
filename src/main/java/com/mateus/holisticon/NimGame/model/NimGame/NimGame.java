package com.mateus.holisticon.NimGame.model.NimGame;

import com.mateus.holisticon.NimGame.ai.strategies.StrategyType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table
public class NimGame {
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private long id;
	
	@NotNull
	@Column
	private Integer matches;
	
	@NotNull
	@Column
	private Integer taken_per_turn;
	
	@Column
	@Enumerated( EnumType.STRING )
	private PlayerType winner;
	
	@Column
	@Enumerated( EnumType.STRING )
	private PlayerType currentPlayer;
	
	@NotNull
	@Column
	@Enumerated( EnumType.STRING )
	private StrategyType strategy;
	
	@Column
	@Builder.Default
	private int turn = 1;
	
	@Column
	@Builder.Default
	private boolean running = true;
	
	public void removeMatches(int ammount) {
		setMatches(getMatches() - ammount);
	}
	
	public void setWinner(PlayerType winner) {
		this.winner = winner;
		setCurrentPlayer(null);
		setRunning(false);
	}
	
	public void takeTurn(PlayerType nextPlayer) {
		setTurn(getTurn() + 1);
		setCurrentPlayer(nextPlayer);
	}
}
