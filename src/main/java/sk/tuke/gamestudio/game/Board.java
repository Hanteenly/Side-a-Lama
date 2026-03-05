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

    public Position findEmptyTile(){
        Position pos;
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                if(tiles[i][j].getType() == TileType.EMPTY){
                    pos = new Position(i, j);
                    return pos;
                }
            }
        }
        return null;
    }
}
