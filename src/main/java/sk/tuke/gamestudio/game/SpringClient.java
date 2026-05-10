package sk.tuke.gamestudio.game;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.GameState;
import sk.tuke.gamestudio.service.GameStateService;
import org.springframework.context.annotation.Configuration;
import sk.tuke.gamestudio.service.*;

@SpringBootApplication
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
        pattern = "sk.tuke.gamestudio.server.*"))
public class SpringClient {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringClient.class).web(WebApplicationType.NONE).run(args);
    }


    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /*
    public CommandLineRunner runner(ConsoleUI ui) {
        return args -> ui.play();
    }
*/

    public Game game() {
        return new Game(4, 4);
    }


    public ScoreService scoreService() {
        return new ScoreServiceRestClient();
    }


    public CommentService commentService() {
        return new CommentServiceRestClient();
    }

    public RatingService ratingService() {
        return new RatingServiceRestClient();
    }

    public GameStateService gameStateService() {
        return new GameStateServiceRestClient();
    }
}
