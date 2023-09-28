                                                  Nim Game

The game should be played against the computer using REST calls sent to the server via HTTP, which contain a JSON payload.

Nim is a two-player game that starts with a heap of 13 matches. On each of their turns, a player must take 1, 2, or 3 match-es from the heap.
Play continues, turns alternating between player and computer, until no matches are left. The player who takes the last match loses.
For the computer, the number of matches it takes may be chosen randomly, as long as it doesn't exceed the number of available matches. For example, if there are only two matches left, the computer can only take one or two matches.


Non-functional requirements
  1. The application should be written in Java, Kotlin or Scala.
  2. The application should be self-contained, such as being based on SpringBoot, and runnable from the command line.
  3. Use Maven, Gradle or SBT to build the application. Don’t use any other tools or plugins that might prevent the build from running on our machine.
  4. A game of Nim can be played without any issues, bugs, or crashes.


Optional goals
  a) Implement a version of the game server where the computer utilizes a winning-oriented strategy instead of ran-domly drawing matches.
  b) Implement a parameterized version of the game where the following game settings can be altered.
    1. The number of matches at game start (e.g., 13 in the default game)
    2. The number of matches that can be taken each turn (e.g., 1, 2, or 3 in the default game)
  c) Implement a persistent version of the game, such as by using a database or in-memory cache.
