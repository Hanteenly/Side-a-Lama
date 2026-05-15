package sk.tuke.gamestudio.game;

public class Game {
    private Board board;
    private State state;

    private String gameName;
    private Player player1;
    private Player player2;
    private Player currentPlayer;

    private String player1Name = "player1";
    private String player2Name = "player2";

    private int borderSize;

    private Tile nextTile;

    private String Tile;
    private Tile tile;
    public Game(int rows, int cols) {
        board = new Board(rows, cols);
        this.borderSize = rows;
        this.nextTile = board.randomTile();

        player1 = new Player(player1Name, 50);
        player2 = new Player(player2Name, 50);
        currentPlayer = player1;

        this.state = State.PLAYING;
    }

    public String getPlayer1(){
        return player1Name;
    }
    public void SetPlayer1(String name) {
        this.player1Name = name;
        this.player1.setName(name);
    }
    public String getPlayer2(){
        return player2Name;
    }
    public void SetPlayer2(String name) {
        this.player2Name = name;
        this.player2.setName(name);
    }

    public void updateNextTile() {
        this.nextTile = board.randomTile();
    }

    public Tile getNextTile() {
        return nextTile;
    }
    public void switchPlayer(){
        if(currentPlayer == player1){
            currentPlayer = player2;
        }else{
            currentPlayer = player1;
        }
    }
    public Board getBoard(){
        return board;
    }
    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    public State getState(){
        return state;
    }

    public void playTurn(Direction direction, int index){
        if (direction == Direction.LEFT) {
            board.insertFromLeft(index, nextTile);
        } else if (direction == Direction.RIGHT) {
            board.insertFromRight(index, nextTile);
        } else {
            board.insertFromTop(index, nextTile);
        }

        if (board.hasMatches()) {
            if (currentPlayer == player1) {
                player1.addToScore(50);
                player2.addToScore(-50);
            } else {
                player1.addToScore(-50);
                player2.addToScore(50);
            }
        } else {
            switchPlayer();
        }
        board.cleanBoard();
        checkGameEnd();
    }

    public void randomTile(){
        Tile tile = board.randomTile();
        this.Tile = tile.toString();
        this.tile = tile;
    }

    public void checkGameEnd() {
        if (player1.getScore() <= 0){
            state = State.PLAYER2_WIN;
        }
        if (player2.getScore() <= 0) {
            state = State.PLAYER1_WIN;
        }
    }

    public int getBorderSize() {
        return borderSize;
    }
    public void setBorderSize(int borderSize) {
        this.borderSize = borderSize;
    }
    public void printBoard(){
        board.print();
    }
    public void SetTile(String Tile){
        this.Tile = Tile;
    }

    public String getTile(){
        return Tile;
    }
    public Tile gettile(){
        return tile;
    }

    public int getScore1(){
        return player1.getScore();
    }
    public int getScore2(){
        return player2.getScore();
    }

    public void setCurrentPlayer(String name) {
        if (player1.getName().equals(name)) {
            currentPlayer = player1;
        } else {
            currentPlayer = player2;
        }
    }
    public String getCurrentlyPlayer(){
        return currentPlayer.getName();
    }
    public void setScores(int s1, int s2) {
        this.player1 = new Player(player1Name, s1);
        this.player2 = new Player(player2Name, s2);
    }
    public String getGameName(){
        return gameName;
    }
    public void setGameName(String gameName){
        this.gameName = gameName;
    }
}
