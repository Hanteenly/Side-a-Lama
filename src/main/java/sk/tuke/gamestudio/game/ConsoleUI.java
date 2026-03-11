package sk.tuke.gamestudio.game;

import java.util.Scanner;

public class ConsoleUI {

    private Field field;
    private Game game = new Game(3, 3);

    public void play(Field field){
        this.field = field;
        while(field.getState() == GameState.PLAYING){
            show();
            handleInput();
        }

        show();

        if(field.getState() == GameState.PLAYER1_WIN){
            System.out.println("Player 1 wins!");
        }else if(field.getState() == GameState.PLAYER2_WIN){
            System.out.println("Player 2 wins!");
        }
    }

    public void show(){
        game.printBoard();
    }

    public void handleInput(){
        Scanner scanner = new Scanner(System.in);

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
    }
}
