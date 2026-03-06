package sk.tuke.gamestudio.game;

public class Board {
    private int rows;
    private int cols;
    private Tile[][] tiles;
    public Board(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        tiles = new Tile[rows][cols];
        generateRandomBoard();
    }

    public void generateRandomBoard(){
        for(int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int randomVal = (int)(Math.random()*TileType.values().length);
                TileType type = TileType.values()[randomVal];
                Tile tile = new Tile(type);
                tiles[i][j] = tile;
            }
        }
    }

    public void print(){
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                Tile tile = tiles[i][j];
                System.out.print(tile + " ");
            }
            System.out.println();
        }
    }
    public Tile getTile(int row, int col){
        return tiles[row][col];
    }


    public void swapTiles(Position p1, Position p2){
        Tile tile = tiles[p1.row][p1.col];
        tiles[p1.row][p1.col] = tiles[p2.row][p2.col];
        tiles[p2.row][p2.col] = tile;
    }

    public void insertFromLeft(int row, Tile tile){
        for(int col = tiles[row].length - 1; col > 0; col--){
            tiles[row][col] = tiles[row][col-1];
        }
        tiles[row][0] = tile;
    }

    public void insertFromRight(int row, Tile tile){
        for(int col = 0; col < tiles[row].length - 1; col++){
            tiles[row][col] = tiles[row][col+1];
        }
        tiles[row][tiles[row].length - 1] = tile;
    }

    public void insertFromTop(int col, Tile tile) {
        for (int row = tiles[col].length - 1; row > 0; row--) {
            tiles[row][col] = tiles[row-1][col];
        }
        tiles[0][col] = tile;
    }
}








