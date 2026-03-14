package sk.tuke.gamestudio.game;

import java.util.Scanner;

public class ConsoleUI {

    private Game game;

    public void play(Game game){
        this.game = game;
        while(game.getState() == GameState.PLAYING){
            show();
            handleInput();
        }

        show();

        if(game.getState() == GameState.PLAYER1_WIN){
            System.out.println("Player 1 wins!");
        }else if(game.getState() == GameState.PLAYER2_WIN){
            System.out.println("Player 2 wins!");
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

}
