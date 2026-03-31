package sk.tuke.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.GameState;
import sk.tuke.gamestudio.service.GameStateService;
import sk.tuke.gamestudio.service.GameStateServiceJPA;
import sk.tuke.gamestudio.service.ScoreService;

@RestController
@RequestMapping("/api/gameState")
public class GameStateServiceRest {

    @Autowired
    private GameStateService gameStateService;

    @GetMapping("/{game}")
    public GameState load(@PathVariable String game) {
        return gameStateService.load(game);
    }

    @PostMapping
    public void save(@RequestBody GameState gameState) {
        gameStateService.save(gameState);
    }
}
