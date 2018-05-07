package com.fx.client;

import com.fx.models.Board;
import com.fx.mongodocs.Game;
import com.fx.mongorepos.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;

import java.util.List;


@RestController
@RequestMapping(path="/game")
public class TicTacToeClient {

    @Autowired
    GameRepository gameRepository;

    @RequestMapping("/wow")
    public String createNewGame (@RequestParam(value="input") JSONObject input){
        int gamesCount=getAllGamesFromStorage().size();
        Game newGame = new Game(gamesCount+1,"*********",(char)input.get("character"),(String)input.get("name"));
        gameRepository.save(newGame);
        return "Created new game with ID:" + gamesCount+1;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{gameId}")
    public String getGame(@PathVariable Integer gameId){

        Game currentGame = getGameFromStorage(gameId);
        Board currentBoard = new Board(currentGame.getGameState().toCharArray());
        return currentBoard.getBoardInASCII(currentGame.getOpponentCharacter());
    }

/*    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public String makeMove (JSONObject input){

    }*/

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

   /* public static void main(String[] args){
        getGame(0);
    }*/
}
