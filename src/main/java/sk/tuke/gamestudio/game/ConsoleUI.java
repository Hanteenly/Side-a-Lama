package sk.tuke.gamestudio.game;

import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.*;

import java.util.Scanner;

public class ConsoleUI {

    private Game game;
    private int on = 0;
    private Score score;
    private Rating rating;
    private Comment comment;
    private ScoreService scoreService = new ScoreServiceJDBC();
    private CommentService commentService = new CommentServiceJDBC();
    private RatingService ratingService = new RatingServiceJDBC();
    public void play(Game game){
            on = 1;
            Scanner input = new Scanner(System.in);
            System.out.println("Enter your name1: ");
            String name1 = input.nextLine();
            System.out.println("Enter your name2: ");
            String name2 = input.nextLine();
            game.SetPlayer1(name1);
            game.SetPlayer2(name2);
            this.game = game;
            while (game.getState() == GameState.PLAYING) {
                show();
                handleInput();
            }

            show();

            if (game.getState() == GameState.PLAYER1_WIN) {
                System.out.println(name1 + " wins!");
            } else if (game.getState() == GameState.PLAYER2_WIN) {
                System.out.println(name2 + " wins!");
            }
    }

    public void show(){
        game.printBoard();
    }

    public void handleInput(){
        Scanner scanner = new Scanner(System.in);
        game.randomTile();
        System.out.println("Tile: " + game.getTile() + "\n" + "player1 - " + game.getScore1() + " | player2 - " + game.getScore2());
        System.out.println("CurrentlyPlayer: " + game.getCurrentlyPlayer());
        System.out.println("Enter move (L/R/T index):");
        String dir = scanner.next();
        int index = scanner.nextInt();

        Direction direction = null;
        if(dir.equalsIgnoreCase("L")){
            direction = Direction.LEFT;
        }else if(dir.equalsIgnoreCase("R")){
            direction = Direction.RIGHT;
        }else if(dir.equalsIgnoreCase("T")){
            direction = Direction.TOP;
        }

        game.playTurn(direction, index);
        for(int i = 0; i < 5; i++)System.out.println();
    }

    public void menu(Game game){
        this.game = game;
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
                play(game);
            } else if (choice.equalsIgnoreCase("score")) {
                showTopScores();
            } else if (choice.equalsIgnoreCase("comment")) {
                showComments();
            } else if (choice.equalsIgnoreCase("rating")) {
                showRatings();
            } else {
                break;
            }
        }
    }

    private void showTopScores() {
        System.out.println("\n--- TOP SCORES ---");
        try {
            var scores = scoreService.getTopScores("side-a-lama");
            for (Score s : scores) {
                System.out.printf("%-15s %10d %tF%n", s.getPlayer(), s.getPoints(), s.getPlayedOn());
            }
        } catch (ScoreException e) {
            System.err.println("Error loading scores: " + e.getMessage());
        }
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
    }
}
