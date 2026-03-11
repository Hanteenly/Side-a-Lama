package sk.tuke.gamestudio.game;

public class Main {
    public static void main(String[] args) {
        ConsoleUI console = new ConsoleUI();
        Field f = new Field();
        console.play(f);
    }
}
