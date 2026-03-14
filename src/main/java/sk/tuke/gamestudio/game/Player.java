package sk.tuke.gamestudio.game;

public class Player {
    private String name;
    private int score;

    public Player(String name, int score){
        this.name = name;
        this.score = score;
    }

    public int getScore(){
        return score;
    }

    public void addToScore(int score){
        this.score += score;
    }

}
