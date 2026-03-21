package sk.tuke.gamestudio.game;

public class Game {
    private Board board;
    private GameState state;

    private Player player1;
    private Player player2;
    private Player currentPlayer;

    private String Tile;
    private Tile tile;
    public Game(int rows, int cols) {
        player1 = new Player("Player1", 500);
        player2 = new Player("Player2", 500);
        currentPlayer = player1;

        board = new Board(rows, cols);
        this.state = GameState.PLAYING;
    }

    public void SetPlayer1(String player) {
        this.player1.setName(player);
    }
    public void SetPlayer2(String player) {
        this.player2.setName(player);
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

    public GameState getState(){
        return state;
    }

    public void playTurn(Direction direction, int index){
        if(direction == Direction.LEFT){
            board.insertFromLeft(index, gettile());
        }else if(direction == Direction.RIGHT) {
            board.insertFromRight(index, gettile());
        }else{
            board.insertFromTop(index, gettile());
        }

        if(board.hasMatches()){
            if(currentPlayer == player1){
                player1.addToScore(50);
                player2.addToScore(-50);
            }else{
                player1.addToScore(-50);
                player2.addToScore(50);
            }
        }else{
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
            state = GameState.PLAYER2_WIN;
        }
        if (player2.getScore() <= 0) {
            state = GameState.PLAYER1_WIN;
        }
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

    public String getCurrentlyPlayer(){
        if(currentPlayer == player1){
            return "PLayer1";
        }else{
            return "PLayer2";
        }
    }
}
