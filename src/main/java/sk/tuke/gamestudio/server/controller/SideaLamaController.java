package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.GameState;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.game.Direction;
import sk.tuke.gamestudio.game.Game;
import sk.tuke.gamestudio.game.Tile;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.GameStateService;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.ScoreService;

import java.util.Date;

@Controller
@RequestMapping("/sidealama")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class SideaLamaController {

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private GameStateService gameStateService;

    private Game game = new Game(8, 8);

    private String player1Name = "player1";
    private String player2Name = "player2";

    private String gameName;

    @GetMapping
    public String index(@RequestParam(required = false) String dir, @RequestParam(required = false) Integer index,  Model model) {
        if (dir != null && index != null) {
            try {
                game.randomTile();
                game.playTurn(Direction.valueOf(dir), index);
            } catch (IllegalArgumentException e) {
            }
        }
        fillModel(model);
        return "sidealama";
    }
    @GetMapping("/Choose")
    public String showChoose() {
        return "choose";
    }

    @PostMapping("/new")
    public String startNewGame(@RequestParam String gameName, Model model) {
        this.game = new Game(4, 4);
        this.gameName = gameName;

        return "redirect:/sidealama";
    }
    @PostMapping("/name")
    public String setName(@RequestParam String name1, String name2, Model model) {
        this.player1Name = name1;
        this.player2Name = name2;
        fillModel(model);
        return "sidealama";
    }
    @GetMapping("/new")
    public String newGame(Model model) {
        game = new Game(5, 5);
        game.randomTile();
        fillModel(model);
        return "sidealama";
    }

    @PostMapping("/comment")
    public String addComment(@RequestParam String content, Model model) {
        try {
            commentService.addComment(new Comment("sidealama", "anonymous", content, new Date()));
        } catch (Exception e) {
        }
        fillModel(model);
        return "sidealama";
    }

    @PostMapping("/rating")
    public String addRating(@RequestParam int rating, Model model) {
        try {
            ratingService.setRating(new Rating("sidealama", "anonymous", rating, new Date()));
        } catch (Exception e) {

        }
        fillModel(model);
        return "sidealama";
    }

    private void fillModel(Model model) {
        try {
            model.addAttribute("scores", scoreService.getTopScores("sidealama"));
        } catch (Exception e) {
            model.addAttribute("scores", java.util.Collections.emptyList());
        }
        try {
            model.addAttribute("comments", commentService.getComments("sidealama"));
        } catch (Exception e) {
            model.addAttribute("comments", java.util.Collections.emptyList());
        }
        try {
            model.addAttribute("averageRating", ratingService.getAverageRating("sidealama"));
        } catch (Exception e) {
            model.addAttribute("averageRating", 0);
        }
        model.addAttribute("currentPlayer", game.getCurrentlyPlayer());
        model.addAttribute("score1", game.getScore1());
        model.addAttribute("score2", game.getScore2());
        model.addAttribute("state", game.getState());
        model.addAttribute("player1Name", this.player1Name);
        model.addAttribute("player2Name", this.player2Name);
    }

    @PostMapping("/save")
    public String saveGame(@RequestParam String gameName, Model model) {
        try {
            GameState state = new GameState();
            state.setGameName(gameName);
            state.setPlayer1(this.player1Name);
            state.setPlayer2(this.player2Name);
            state.setCurrentPlayer(game.getCurrentlyPlayer());
            state.setScore1(game.getScore1());
            state.setScore2(game.getScore2());
            state.setBoard_data(game.getBoard().toDataString());

            gameStateService.save(state);
        } catch (Exception e) {
            e.printStackTrace();
        }
        fillModel(model);
        return "sidealama";
    }

    @PostMapping("/load")
    public String loadGame(@RequestParam String gameName, Model model) {
        try {
            GameState state = gameStateService.load(gameName);
            if (state != null) {
                this.player1Name = state.getPlayer1();
                this.player2Name = state.getPlayer2();
                this.gameName = state.getGameName();
                this.game.getBoard().loadFromDataString(state.getBoard_data());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        fillModel(model);
        return "sidealama";
    }

    public String getHtmlField() {
        StringBuilder sb = new StringBuilder();
        sb.append("<table border='1' cellpadding='5'>\n");
        for (int row = 0; row < game.getBoard().getRows(); row++) {
            sb.append("<tr>\n");
            for (int col = 0; col < game.getBoard().getCols(); col++) {
                Tile tile = game.getBoard().getTile(row, col);
                sb.append("<td>");
                sb.append(String.format("<a href='/sidealama?dir=%s&index=%d'>", "LEFT", row));
                sb.append(tile != null ? tile.toString() : "·");
                sb.append("</a>");
                sb.append("</td>\n");
            }
            sb.append("</tr>\n");
        }
        sb.append("</table>\n");
        return sb.toString();
    }
}
