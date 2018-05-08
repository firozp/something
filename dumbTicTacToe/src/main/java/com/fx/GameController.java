package com.fx;

import com.fx.models.Board;
import com.fx.mongodocs.Game;
import com.fx.mongorepos.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;

import java.util.List;
import java.util.Random;


@RestController
@RequestMapping(path="/game")
public class GameController {

    @Autowired
    GameRepository gameRepository;

    @RequestMapping(method = RequestMethod.POST, value = "/")
    public String createNewGame (@RequestParam(value="input") JSONObject input){
        int gamesCount=getAllGamesFromStorage().size();
        String character = (String)input.get("character");
        Game newGame = new Game(gamesCount+1,"*********",character.charAt(0),(String)input.get("name"));
        gameRepository.save(newGame);
        return "Created new game with ID:" + (++gamesCount);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{gameId}")
    public String getGame(@PathVariable Integer gameId){
        Game currentGame = getGameFromStorage(gameId);
        Board currentBoard = new Board(currentGame.getGameState().toCharArray());
        return currentBoard.getBoardInASCII(currentGame.getOpponentCharacter());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{gameId}/move")
    public String tryMove(@PathVariable Integer gameId,@RequestParam(value="input") JSONObject moveInput) {
        String stateOfGame="Game ongoing, your turn ..."
        String keys="ABC";

        int rowToMove=-1;
        int columnToMove=-1;

        rowToMove= keys.indexOf(String.valueOf(moveInput.get("row")).charAt(0));
        columnToMove=keys.indexOf(String.valueOf(moveInput.get("column")).charAt(0));

        if(rowToMove<0 || columnToMove<0){
           throw new RuntimeException("Invalid inputs !");
        }

        Game currentGame = getGameFromStorage(gameId);
        Board currentBoard = new Board(currentGame.getGameState().toCharArray());

        currentBoard.makeMove(rowToMove,columnToMove,false);

        if(!currentBoard.hasWinner) {
            
            if (currentBoard.movesAvailable()) {
                Random rand = new Random();
                int randomMove = rand.nextInt((currentBoard.availablePlaces.size()));
                int placeToMove = currentBoard.availablePlaces.get(randomMove);
                rowToMove = placeToMove / 3;
                columnToMove = placeToMove % 3;
                currentBoard.makeMove(rowToMove, columnToMove, true);
            }
        }
        currentGame.setGameState(currentBoard.getGameState());
        gameRepository.save(currentGame);
        return currentBoard.getBoardInASCII(currentGame.getOpponentCharacter() + "/n" + stateOfGame);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public String getTest(){
       System.out.println("In test");
       return "test";
    }

    public Game getGameFromStorage(int gId) {
        if (gameRepository == null) {
            System.out.println("Repo is null !");
            System.out.println("Check connection to DB !");
            return null;
        } else if (gameRepository.findAll() == null) {
            System.out.println("Nothing to show here !");
            return null;
        }
        return gameRepository.findByGameID(gId);
    }

    public List<Game> getAllGamesFromStorage() {
        if (gameRepository == null) {
            System.out.println("Repo is null !");
            System.out.println("Check connection to DB !");
            return null;
        } else if (gameRepository.findAll() == null) {
            System.out.println("Nothing to show here !");
            return null;
        }
        return gameRepository.findAll();
    }

}
