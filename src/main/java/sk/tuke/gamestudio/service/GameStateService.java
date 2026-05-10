package sk.tuke.gamestudio.service;


import sk.tuke.gamestudio.entity.GameState;
import sk.tuke.gamestudio.game.TileType;

import java.util.List;

public interface GameStateService {
    void save(GameState state) throws GameStateException;
    GameState load(String gameName) throws GameStateException;
    public List<GameState> getGameStates(String name) throws GameStateException;
}
