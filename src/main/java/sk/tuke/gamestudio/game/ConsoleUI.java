package sk.tuke.gamestudio.game;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.entity.GameState;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Scanner;

@Component
public class ConsoleUI {
    private String name1;
    private String name2;
    private String currentSaveName;

    @Autowired
    private Game game;
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private GameStateService gameStateService;

    public void play(){
            Scanner input = new Scanner(System.in);
            System.out.println("Game name: ");
            this.currentSaveName = input.nextLine();
        try {
            GameState savedState = gameStateService.load(currentSaveName);

            if (savedState != null) {
                game.SetPlayer1(savedState.getPlayer1());
                game.SetPlayer2(savedState.getPlayer2());
                game.setScores(savedState.getScore1(), savedState.getScore2());
                game.getBoard().loadFromDataString(savedState.getBoard_data());

                this.name1 = savedState.getPlayer1();
                this.name2 = savedState.getPlayer2();
            } else {
                System.out.println("new game: ");
                System.out.println("Enter your name1: ");
                this.name1 = input.nextLine();
                System.out.println("Enter your name2: ");
                this.name2 = input.nextLine();

                game.SetPlayer1(name1);
                game.SetPlayer2(name2);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
            while (game.getState() == State.PLAYING) {
                show();
                handleInput();
            }

            show();

            if (game.getState() == State.PLAYER1_WIN) {
                System.out.println(name1 + " wins!");
                Score score = new Score("side-a-lama", name1, game.getScore1(), new java.util.Date());
                scoreService.addScore(score);
            } else if (game.getState() == State.PLAYER2_WIN) {
                System.out.println(name2 + " wins!");
                Score score = new Score("side-a-lama", name2, game.getScore2(), new java.util.Date());
                scoreService.addScore(score);
            }

    }

    public void show(){
        game.printBoard();
    }

    public void handleInput() {
        Scanner scanner = new Scanner(System.in);
        game.randomTile();
        System.out.println("Tile: " + game.getTile());
        System.out.println(name1 + ": " + game.getScore1() + " | " + name2 + ": " + game.getScore2());

        String currentlyPlayer = (game.getCurrentlyPlayer().equals("PLayer1")) ? name1 : name2;
        System.out.println("currentlyPlayer: " + currentlyPlayer);
        System.out.println("'S' for save");
        System.out.println("Move (L/R/T index): ");

        String cmd = scanner.next();

        if (cmd.equalsIgnoreCase("S")) {
            try {
                GameState stateToSave = new GameState(
                        currentSaveName,
                        name1,
                        name2,
                        game.getCurrentlyPlayer(),
                        game.getScore1(),
                        game.getScore2(),
                        game.getBoard().toDataString()
                );
                gameStateService.save(stateToSave);
                System.out.println("✅ Game saved as: " + currentSaveName);
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
            return;
        }

        if (cmd.equalsIgnoreCase("menu")) {
            menu();
            return;
        }

        int index = scanner.nextInt();
        Direction direction = null;

        if (cmd.equalsIgnoreCase("L")) direction = Direction.LEFT;
        else if (cmd.equalsIgnoreCase("R")) direction = Direction.RIGHT;
        else if (cmd.equalsIgnoreCase("T")) direction = Direction.TOP;

        if (direction != null) {
            game.playTurn(direction, index);
        } else {
            System.out.println("Unknown command!");
        }
    }

    public void menu(){

        for(int i = 0; i < 10; i++)System.out.println();
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("MENU");
            System.out.println("play");
            System.out.println("score");
            System.out.println("comment");
            System.out.println("rating");
            System.out.println();
            System.out.println("Choose one:");
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("play")) {
                play();
            } else if (choice.equalsIgnoreCase("score")) {
                showTopScores();
            } else if (choice.equalsIgnoreCase("comment")) {
                showComments();
            } else if (choice.equalsIgnoreCase("rating")) {
                showRatings();
            } else {
                System.out.println("*WRONG CHOICE*");
                System.out.println();
            }
        }
    }

    private void showTopScores() {
        System.out.println("\n--- TOP SCORES ---");
        try {
            var scores = scoreService.getTopScores("side-a-lama");
            for (Score s : scores) {
                System.out.println(s.getPlayer() + " " + s.getPoints() + " " + s.getPlayedOn());
            }
        } catch (ScoreException e) {
            System.err.println("Error loading scores: " + e.getMessage());
        }
        System.out.println("EXIT?");
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();
        for(int i = 0; i < 10; i++)System.out.println();
    }

    private void showComments() {
        System.out.println("\n--- COMMENTS ---");
        try {
            var comments = commentService.getComments("side-a-lama");
            for (Comment c : comments) {
                System.out.println(c.getPlayer() + ": " + c.getComments() + " (" + c.getPlayedOn() + ")");
            }
        } catch (CommentException e) {
            System.err.println("Error loading comments: " + e.getMessage());
        }
        while(true) {
            System.out.println();
            System.out.println("add comment?yes/no: ");
            Scanner scanner = new Scanner(System.in);
            String choose = scanner.nextLine();
            if (choose.equalsIgnoreCase("yes")) {
                System.out.println("name: ");
                String name = scanner.nextLine();
                System.out.print("text: ");
                String TEXT = scanner.nextLine();
                Comment comment = new Comment("side-a-lama", name, TEXT, new java.util.Date());
                commentService.addComment(comment);
                break;
            } else if (choose.equalsIgnoreCase("no")) {
                System.out.println();
                break;
            } else {
                System.out.println("*WRONG CHOICE*");
                System.out.println();
            }
        }
    }

    private void showRatings() {
        System.out.println("\n--- RATING ---");
        try {
            int avg = ratingService.getAverageRating("side-a-lama");
            if (avg > 0) {
                System.out.print("Average rating: " + avg + " / 5 ");
                for (int i = 0; i < avg; i++) System.out.print("⭐");
                System.out.println();
            } else {
                System.out.println("No ratings yet.");
            }
        } catch (RatingException e) {
            System.err.println("Error loading rating: " + e.getMessage());
        }
        System.out.println();
        System.out.println("add rating?yes/no: ");
        Scanner scanner = new Scanner(System.in);
        while(true){
            String choose = scanner.nextLine();
            if (choose.equalsIgnoreCase("yes")) {
                System.out.println("name: ");
                String name = scanner.nextLine();
                System.out.println("number (0-5): ");
                int number = scanner.nextInt();
                if(number > 5 || number < 0){
                    System.out.println("Invalid number");
                }
                Rating rating = new Rating("side-a-lama", name, number, new java.util.Date());
                ratingService.setRating(rating);
                scanner.nextLine();
                System.out.println("Show all rating?yes/no: ");
                String choice2 = scanner.nextLine();


                int avg = ratingService.getRating("side-a-lama", name);

                List<Rating> all = ratingService.getAllRatings();
                if(choice2.equalsIgnoreCase("yes")) {
                    System.out.println("all rating: " + avg);
                    for (int i = 0; i < all.size(); i++) {
                        Rating r = all.get(i);
                        System.out.print(r.getPlayer() + ": " + r.getRating() + "/5 ");
                        for (int j = 0; j < r.getRating(); j++) System.out.print("⭐");
                        System.out.println();
                    }
                }
                System.out.println();
                break;
            }else if(choose.equalsIgnoreCase("no")) {
                System.out.println();
                break;
            }else{
                System.out.println("*WRONG CHOICE*");
                System.out.println();
            }
        }
    }
}
