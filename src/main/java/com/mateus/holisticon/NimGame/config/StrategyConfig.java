package com.mateus.holisticon.NimGame.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mateus.holisticon.NimGame.ai.strategies.IStrategy;
import com.mateus.holisticon.NimGame.ai.strategies.StrategyType;

@Configuration
public class StrategyConfig {
	
    @Autowired
    @Qualifier("RANDOM")
    private IStrategy randomStrategy;

    @Autowired
    @Qualifier("WINNING_ORIENTED")
    private IStrategy winningStrategy;

    @Bean
    public Map<StrategyType, IStrategy> strategyMap() {
        Map<StrategyType, IStrategy> map = new HashMap<>();
        map.put(StrategyType.RANDOM, randomStrategy);
        map.put(StrategyType.WINNING_ORIENTED, winningStrategy);
        return map;
    }
	
}
