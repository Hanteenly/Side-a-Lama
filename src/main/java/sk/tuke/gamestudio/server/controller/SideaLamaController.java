package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.GameState;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.game.Direction;
import sk.tuke.gamestudio.game.Game;
import sk.tuke.gamestudio.game.State;
import sk.tuke.gamestudio.game.Tile;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.GameStateService;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.ScoreService;

import java.util.*;

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

    private Game game = new Game(4, 4);

    private String gameName;

    @GetMapping
    public String index(@RequestParam(required = false) String dir, @RequestParam(required = false) Integer index,  Model model) {
        if (dir != null && index != null) {
            try {
                game.playTurn(Direction.valueOf(dir), index);
                game.updateNextTile();
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

    @GetMapping("/Comment")
    public String showComment(Model model) {
        fillModel(model);
        return "comment";
    }

    @GetMapping("/Top-score")
    public String showRatings(Model model) {
        fillModel(model);
        return "top-score";
    }
    @PostMapping("/new")
    public String startNewGame(@RequestParam String gameName, @RequestParam String player1Name, @RequestParam String player2Name, Model model) {
        this.game = new Game(4, 4);
        this.gameName = gameName;
        game.SetPlayer1(player1Name);
        game.SetPlayer2(player2Name);
        return "redirect:/sidealama";
    }
    @PostMapping("/name")
    public String setName(@RequestParam String player1Name, @RequestParam String player2Name, Model model) {
        game.SetPlayer1(player1Name);
        game.SetPlayer2(player2Name);
        fillModel(model);
        return "redirect:/sidealama";
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
            commentService.addComment(new Comment("sidealama", game.getPlayer1(), content, new Date()));
        } catch (Exception e) {
        }
        return "redirect:/sidealama#comments";
    }

    @PostMapping("/rating")
    public String addRating(@RequestParam int rating, Model model) {
        try {
            ratingService.setRating(new Rating("sidealama", game.getPlayer1(), rating, new Date()));
        } catch (Exception e) {

        }
        fillModel(model);
        return "redirect:/sidealama";
    }

    private void fillModel(Model model) {
        try {
            model.addAttribute("scores", scoreService.getTopScores("sidealama"));
        } catch (Exception e) {
            model.addAttribute("scores", java.util.Collections.emptyList());
        }
        
        try {
            model.addAttribute("averageRating", ratingService.getAverageRating("sidealama"));
        } catch (Exception e) {
            model.addAttribute("averageRating", 0.0);
        }

        try {
            model.addAttribute("playerRating", ratingService.getRating("sidealama", game.getPlayer1()));
        } catch (Exception e) {
            model.addAttribute("playerRating", 0);
        }
        try {
            List<Comment> comments = commentService.getComments("sidealama");
            model.addAttribute("comments", comments);

            Map<String, Integer> commentRatings = new HashMap<>();
            for (Comment c : comments) {
                try {
                    int r = ratingService.getRating("sidealama", c.getPlayer());
                    commentRatings.put(c.getPlayer(), r);
                } catch (Exception e) {
                    commentRatings.put(c.getPlayer(), 0);
                }
            }
            model.addAttribute("commentRatings", commentRatings);
        } catch (Exception e) {
            model.addAttribute("comments", java.util.Collections.emptyList());
            model.addAttribute("commentRatings", java.util.Collections.emptyMap());
        }
        model.addAttribute("currentPlayer", game.getCurrentlyPlayer());
        model.addAttribute("score1", game.getScore1());
        model.addAttribute("score2", game.getScore2());
        model.addAttribute("state", game.getState());
        model.addAttribute("player1Name", game.getPlayer1());
        model.addAttribute("player2Name", game.getPlayer2());
        model.addAttribute("GameSate",this.game);
        model.addAttribute("nextTile", game.getNextTile().toString());
        model.addAttribute("gameName", this.gameName);

    }

    @PostMapping("/save")
    public String saveGame(Model model) {
        if (this.gameName == null) {
            return "redirect:/sidealama";
        }
        try {
            GameState state = new GameState();
            state.setGameName(this.gameName);
            state.setPlayer1(game.getPlayer1());
            state.setPlayer2(game.getPlayer2());
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
    public String loadGame(@RequestParam String gameName, Model model, RedirectAttributes redirectAttributes) {
        try {
            GameState state = gameStateService.load(gameName);
            if (state != null) {
                this.game = new Game(4, 4);
                game.SetPlayer1(state.getPlayer1());
                game.SetPlayer2(state.getPlayer2());
                game.setScores(state.getScore1(), state.getScore2());
                this.gameName = state.getGameName();
                this.game.getBoard().loadFromDataString(state.getBoard_data());
                return "redirect:/sidealama";
            } else {
                redirectAttributes.addFlashAttribute("error",
                        "Game \"" + gameName + "\" not found!");
                return "redirect:/sidealama/Choose";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to load game.");
            return "redirect:/sidealama/Choose";
        }
    }

    @GetMapping("/state")
    @ResponseBody
    public Map<String, Object> getState() {
        Map<String, Object> map = new HashMap<>();
        map.put("score1", game.getScore1());
        map.put("score2", game.getScore2());
        map.put("currentPlayer", game.getCurrentlyPlayer());
        map.put("state", game.getState().name());
        map.put("nextTile", game.getNextTile().toString());
        map.put("board", getBoardData());
        return map;
    }

    @GetMapping("/move")
    @ResponseBody
    public Map<String, Object> move(@RequestParam String dir, @RequestParam int index) {
        try {
            game.playTurn(Direction.valueOf(dir), index);
            game.updateNextTile();
        } catch (IllegalArgumentException e) {}
        if (game.getState() == State.PLAYER1_WIN) {
            try {
                scoreService.addScore(new Score("sidealama", game.getPlayer1(), game.getScore1(), new Date()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (game.getState() == State.PLAYER2_WIN) {
            try {
                scoreService.addScore(new Score("sidealama", game.getPlayer2(), game.getScore2(), new Date()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return getState();
    }

    private List<List<String>> getBoardData() {
        List<List<String>> rows = new ArrayList<>();
        for (int r = 0; r < game.getBoard().getRows(); r++) {
            List<String> row = new ArrayList<>();
            for (int c = 0; c < game.getBoard().getCols(); c++) {
                Tile t = game.getBoard().getTile(r, c);
                row.add(t != null ? t.toString() : "·");
            }
            rows.add(row);
        }
        return rows;
    }
    public String getHtmlField() {
        StringBuilder sb = new StringBuilder();
        int rows = game.getBoard().getRows();
        int cols = game.getBoard().getCols();

        sb.append("<table class='game-board'>\n");

        sb.append("<tr><td></td>");
        for (int col = 0; col < cols; col++) {
            sb.append("<td class='arrow-cell'>");
            sb.append("<form action='/sidealama' method='get'>");
            sb.append("<input type='hidden' name='dir' value='TOP'>");
            sb.append("<input type='hidden' name='index' value='").append(col).append("'>");
            sb.append("<button type='submit' class='arrow-button'>▼</button>");
            sb.append("</form>");
            sb.append("</td>");
        }
        sb.append("<td></td></tr>\n");

        for (int row = 0; row < rows; row++) {
            sb.append("<tr>");

            sb.append("<td class='arrow-cell'>");
            sb.append("<form action='/sidealama' method='get'>");
            sb.append("<input type='hidden' name='dir' value='LEFT'>");
            sb.append("<input type='hidden' name='index' value='").append(row).append("'>");
            sb.append("<button type='submit' class='arrow-button'>▶</button>");
            sb.append("</form>");
            sb.append("</td>");

            for (int col = 0; col < cols; col++) {
                Tile tile = game.getBoard().getTile(row, col);
                sb.append("<td class='tile-cell'>");
                sb.append("<span class='tile'>").append(tile != null ? tile.toString() : "·").append("</span>");
                sb.append("</td>");
            }

            sb.append("<td class='arrow-cell'>");
            sb.append("<form action='/sidealama' method='get'>");
            sb.append("<input type='hidden' name='dir' value='RIGHT'>");
            sb.append("<input type='hidden' name='index' value='").append(row).append("'>");
            sb.append("<button type='submit' class='arrow-button'>◀</button>");
            sb.append("</form>");
            sb.append("</td>");

            sb.append("</tr>\n");
        }

        sb.append("</table>\n");
        return sb.toString();
    }
}
