package sk.tuke.gamestudio.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class Board {
    private int rows;
    private int cols;
    private Tile[][] tiles;
    private String StringBuilder[];
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
                if(j > 0){
                    while(tiles[i][j].getType() == tiles[i][j-1].getType()){
                        int randomVal2 = (int)(Math.random()*TileType.values().length);
                        TileType type2 = TileType.values()[randomVal2];
                        Tile tile2 = new Tile(type2);
                        tiles[i][j] = tile2;
                    }
                }
                if(i > 0){
                    while(tiles[i][j].getType() == tiles[i-1][j].getType()){
                        int randomVal3= (int)(Math.random()*TileType.values().length);
                        TileType type3 = TileType.values()[randomVal3];
                        Tile tile3 = new Tile(type3);
                        tiles[i][j] = tile3;
                    }
                }
            }
        }
    }

    public void print(){
        System.out.print(" ");
        for(int i = 0; i < cols; i++) {
            System.out.print("______");
        }
        System.out.println();
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                Tile tile = tiles[i][j];
                if(j == 0){
                    System.out.print(" | ");
                }
                System.out.print(tile + " ");
            }
            System.out.print(" | ");
            System.out.println();
        }
        System.out.print(" ");
        for(int i = 0; i < cols; i++) {
            System.out.print("______");
        }
        System.out.println();

    }
    public Tile getTile(int row, int col){
        return tiles[row][col];
    }


    public void swapTiles(Position p1, Position p2){
        Tile tile = tiles[p1.row][p1.col];
        tiles[p1.row][p1.col] = tiles[p2.row][p2.col];
        tiles[p2.row][p2.col] = tile;
    }

    public void insertFromLeft(int row, Tile newTile) {
        List<Tile> activeTiles = new ArrayList<>();

        for (int col = 0; col < cols; col++) {
            if (tiles[row][col] != null) {
                activeTiles.add(tiles[row][col]);
            }
        }

        activeTiles.add(0, newTile);
        if (activeTiles.size() > cols) {
            activeTiles.remove(activeTiles.size() - 1);
        }

        for (int col = 0; col < cols; col++) {
            if (col < activeTiles.size()) {
                tiles[row][col] = activeTiles.get(col);
            } else {

                tiles[row][col] = null;
            }
        }
    }

    public void insertFromRight(int row, Tile newTile){
        List<Tile> activeTiles = new ArrayList<>();

        for (int col = 0; col < tiles[row].length; col++) {
            if (tiles[row][col] != null) {
                activeTiles.add(tiles[row][col]);
            }
        }

        activeTiles.add(newTile);

        if (activeTiles.size() > tiles[row].length) {
            activeTiles.remove(0);
        }
        for (int col = 0; col < tiles[row].length; col++) {
            if (col < activeTiles.size()) {
                tiles[row][col] = activeTiles.get(col);
            } else {
                tiles[row][col] = null;
            }
        }
    }

    public void insertFromTop(int col, Tile newTile) {
        List<Tile> activeTiles = new ArrayList<>();

        for (int row = 0; row < rows; row++) {
            if (tiles[row][col] != null) {
                activeTiles.add(tiles[row][col]);
            }
        }

        activeTiles.add(0, newTile);

        if (activeTiles.size() > rows) {
            activeTiles.remove(activeTiles.size() - 1);
        }

        for (int row = 0; row < rows; row++) {
            if (row < activeTiles.size()) {
                tiles[row][col] = activeTiles.get(row);
            } else {
                tiles[row][col] = null;
            }
        }
    }

    public void findMatches(){
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols - 2; j++) {
                if ((tiles[i][j] != null) && (tiles[i][j+1] != null) && (tiles[i][j+2] != null)) {
                    if (tiles[i][j].getType() == tiles[i][j + 1].getType()) {
                        if (tiles[i][j].getType() == tiles[i][j + 2].getType()) {
                            tiles[i][j] = null;
                            tiles[i][j + 1] = null;
                            tiles[i][j + 2] = null;
                        }
                    }
                }
            }
        }
        for(int i = 0; i < cols; i++){
            for(int j = 0; j < rows - 2; j++){
                if((tiles[j][i] != null) && (tiles[j+1][i] != null) && (tiles[j+2][i] != null)) {
                    if (tiles[j][i].getType() == tiles[j + 1][i].getType()) {
                        if (tiles[j][i].getType() == tiles[j + 2][i].getType()) {
                            tiles[j][i] = null;
                            tiles[j + 1][i] = null;
                            tiles[j + 2][i] = null;
                        }
                    }
                }
            }
        }
    }

    public void dropTiles() {
        for (int col = 0; col < cols; col++) {
            for (int row = rows - 1; row > 0; row--) {
                if (tiles[row][col] == null) {
                    for (int i = row - 1; i >= 0; i--) {
                        if (tiles[i][col] != null) {
                            tiles[row][col] = tiles[i][col];
                            tiles[i][col] = null;
                            break;
                        }
                    }
                }
            }
        }
    }

    public boolean hasMatches(){
        boolean hasMatches = false;
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols - 2; j++) {
                if ((tiles[i][j] != null) && (tiles[i][j+1] != null) && (tiles[i][j+2] != null)) {
                    if (tiles[i][j].getType() == tiles[i][j + 1].getType()) {
                        if (tiles[i][j].getType() == tiles[i][j + 2].getType()) {
                            hasMatches = true;
                            break;
                        }
                    }
                }
            }
        }
        for(int i = 0; i < cols; i++){
            for(int j = 0; j < rows - 2; j++){
                if((tiles[j][i] != null) && (tiles[j+1][i] != null) && (tiles[j+2][i] != null)) {
                    if (tiles[j][i].getType() == tiles[j + 1][i].getType()) {
                        if (tiles[j][i].getType() == tiles[j + 2][i].getType()) {
                            hasMatches = true;
                            break;
                        }
                    }
                }
            }
        }
        return hasMatches;
    }
    public void cleanBoard(){
        dropTiles();
        while(hasMatches() == true){
            findMatches();
            dropTiles();
        }
    }

    public Tile randomTile(){
        int randomVal = (int)(Math.random()*TileType.values().length);
        TileType type = TileType.values()[randomVal];
        Tile tile = new Tile(type);
        return tile;
    }

    public int getRows(){
        return rows;
    }

    public int getCols(){
        return cols;
    }

    public String toDataString(){
        StringJoiner sj = new StringJoiner(",");
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                if(tiles[i][j] == null) {
                    sj.add("NULL");
                }else{
                    sj.add(tiles[i][j].getType().name());
                }
            }
        }
        return sj.toString();
    }

    public void loadFromDataString(String data){
        String[] parts = data.split(",");
        int index = 0;
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++) {
                if (parts[index].equals("NULL")) {
                    tiles[i][j] = null;
                } else {
                    tiles[i][j] = new Tile(TileType.valueOf(parts[index]));
                }
                index++;
            }
        }
    }
}








