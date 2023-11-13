# Kalaha Game

Kalaha is a two-player strategy board game which is played with a board containing 6 small pits and 1 larger pit per player.

### Business Improvements
Potential enhancements that could be added to the game are as follows:
- Improvement regarding Game Over Rule: The game can be over when the number of stones in any big pit exceeds 36.


### Business Decisions
Business-related decisions made during the development are listed as follows:
- Capturing Stones Rule Decision: When the last stone
  lands in an own empty pit, the player captures his own stone and all stones in the
  opposite pit and puts them in his own big pit.


### Technical Decisions
Technical-related decisions made during the development are listed as follows:
- Saving Game State: The game state is persisted in the database so that it can  support  resuming game in the future.

### Technology Stack

* Java 17
* Spring Boot 2.7.17
* Gradle 7.3.3+
* MongoDB 7.0

### Build

```shell script
./gradlew clean build
```
### Run

```shell script
docker-compose up
```

### Usage
API is served on localhost with port 8080 and with context path /api/kalaha.
The API provides the following endpoints:
- POST/games - Creates a new game with a first player
- PATCH/games/{gameId} - Joins the game with a second player
- PUT/games/{gameId} - Restarts the game
- DELETE/games/{gameId} - Quits the game
- PUT/games/{gameId}/players/{playerId} - Makes a move in a game.
- GET/games/{gameId} - Gets the details of a game.
- GET/games/{gameId}/score - Gets the score of a game.

*The request and response bodies for each endpoint are defined in the dto package.