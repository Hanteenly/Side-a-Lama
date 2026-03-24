package sk.tuke.gamestudio.game;

public class Main {
    public static void main(String[] args) {
        ConsoleUI console = new ConsoleUI();
        Game f = new Game(4, 4);
        console.menu(f);
    }
}
