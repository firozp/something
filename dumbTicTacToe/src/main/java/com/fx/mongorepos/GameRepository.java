package com.fx.mongorepos;

import com.fx.mongodocs.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameRepository extends MongoRepository<Game,Integer>{
    public Game findByGameID(int gameID);
}