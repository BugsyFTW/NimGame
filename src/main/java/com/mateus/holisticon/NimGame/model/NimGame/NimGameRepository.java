package com.mateus.holisticon.NimGame.model.NimGame;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface NimGameRepository extends CrudRepository<NimGame, Long> {
	
	Optional<NimGame> findById(Long id);
}
