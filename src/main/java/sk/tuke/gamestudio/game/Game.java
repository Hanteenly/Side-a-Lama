package sk.tuke.gamestudio.game;

public class Game {
    private Board board;
    private GameState state;

    private Player player1;
    private Player player2;
    private Player currentPlayer;

    public Game(int rows, int cols) {
        player1 = new Player("PLayer1", 500);
        player2 = new Player("PLayer2", 500);
        currentPlayer = player1;

        board = new Board(rows, cols);
        this.state = GameState.PLAYING;
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
        Tile tile = board.randomTile();
        if(direction == Direction.LEFT){
            board.insertFromLeft(index, tile);
        }else if(direction == Direction.RIGHT) {
            board.insertFromRight(index, tile);
        }else{
            board.insertFromTop(index, tile);
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
}
