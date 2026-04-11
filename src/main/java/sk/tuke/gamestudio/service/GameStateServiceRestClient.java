package sk.tuke.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.GameState;

@Component
public class GameStateServiceRestClient implements GameStateService {
    private final String url = "http://localhost:8080/api/gameState";

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
}
