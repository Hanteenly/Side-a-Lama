package sk.tuke.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.GameState;

import java.util.List;

@Component
public class GameStateServiceRestClient implements GameStateService {
    private final String url = "http://localhost:8888/api/gameState";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void save(GameState gameState) {
        restTemplate.postForEntity(url, gameState, GameState.class);
    }

    @Override
    public GameState load(String gameName) {
        return restTemplate.getForObject(url + "/" + gameName, GameState.class);
    }

    @Override
    public List<GameState> getGameStates(String name) throws GameStateException {
        return List.of();
    }
}
