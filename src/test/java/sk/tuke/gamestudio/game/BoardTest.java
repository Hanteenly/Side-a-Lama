package sk.tuke.gamestudio.game;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BoardTest {
    @Test
    public void generateBoardTest(){
        Board board = new Board(5, 5);
        assertNotNull(board);
    }

    @Test
    public void tilesAreGenerated(){
        Board board = new Board(5,5);

        for(int row = 0; row < 5; row++){
            for(int col = 0; col < 5; col++){
                assertNotNull(board.getTile(row,col));
            }
        }
    }
}
