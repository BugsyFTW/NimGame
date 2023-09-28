                                                  Nim Game

The game should be played against the computer using REST calls sent to the server via HTTP, which contain a JSON payload.

Nim is a two-player game that starts with a heap of 13 matches. On each of their turns, a player must take 1, 2, or 3 match-es from the heap.
Play continues, turns alternating between player and computer, until no matches are left. The player who takes the last match loses.
For the computer, the number of matches it takes may be chosen randomly, as long as it doesn't exceed the number of available matches. For example, if there are only two matches left, the computer can only take one or two matches.

The application in written in Java with Maven and SpringBoot.
The application is self-contained, using H2 as an in-memory cache, no additional installs required.

There exists 3 endpoints:

  GET = "/api/gamemanager?id={GAME_ID}" - Returns the data information regarding a game of nim.
  POST = "/api/gamemanager" - Creates a new game of NIM, you can send a JSON body to change configuration of the game or nothing, which will then use the default values.
    JSON data for POST is the following:
    
      
      {
      "starting_matches": "NUMBER_OF_STARTING_MATCHES",
      "strategy": "WINNING_ORIENTED" OR "RANDOM",
      "matches_per_turn": 1, 2, 3...,
      "firstPlayer": "PLAYER" OR "COMPUTER"
      }
      
      
  PUT = "/api/gamemanager" - The main endpoint in which we play the game.
      JSON data for PUT is the following:
      
      
      {
      "game_id": THE ID OF THE GAME,
      "remove_matches": NUMBER OF MATCHES TO REMOVE
      }
      
      

There is also a ```.properties``` file in which you can change config values of the game.

