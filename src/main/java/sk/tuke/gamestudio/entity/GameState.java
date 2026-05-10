package sk.tuke.gamestudio.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NamedQueries({
        @NamedQuery(name = "GameState.load",
                query = "SELECT g FROM GameState g WHERE g.gameName = :gameName ORDER BY g.ident DESC"),
        @NamedQuery(name = "GameState.resetGameStates",
                query = "DELETE FROM GameState g"),
        @NamedQuery(name = "GameState.getGameStates",
                query = "SELECT borderSize FROM GameState g WHERE g.gameName = :gameName ORDER BY g.ident DESC")
})
public class GameState implements Serializable {

    @Id
    @GeneratedValue
    private int ident;

    private String gameName;
    private String player1;
    private String player2;
    private String CurrentPlayer;
    private int score1;
    private int score2;

    @Lob
    private String board_data;
    private int borderSize;
    public GameState() {

    }
    public GameState(String GameName, String player1, String player2, String CurrentPlayer, int score1, int score2, String board_data, int borderSize) {
        this.gameName = GameName;
        this.player1 = player1;
        this.player2 = player2;
        this.CurrentPlayer = CurrentPlayer;
        this.score1 = score1;
        this.score2 = score2;
        this.board_data = board_data;
        this.borderSize = borderSize;
    }

    public int getIdent() {
        return ident;
    }
    public void setIdent(int ident) {
        this.ident = ident;
    }
    public String getGameName() {
        return gameName;
    }

    public String getPlayer1() {
        return player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public String getCurrentPlayer() {
        return CurrentPlayer;
    }

    public int getScore1() {
        return score1;
    }

    public int getScore2() {
        return score2;
    }

    public String getBoard_data() {
        return board_data;
    }

    public String toString(){
        return "GameState{"
                + "GameName = " + gameName + '\''
                + ", Player1 = " + player1 + '\''
                + ", Player2 = " + player2 + '\''
                + ", CurrentPlayer = " + CurrentPlayer + '\''
                + ", score1 = " + score1 + '\''
                + ", score2 = " + score2 + '\''
                + ", board_data = " + board_data +
                '}';
    }

    public void setGameName(String gameName) { this.gameName = gameName; }
    public void setPlayer1(String player1) { this.player1 = player1; }
    public void setPlayer2(String player2) { this.player2 = player2; }
    public void setCurrentPlayer(String currentPlayer) { this.CurrentPlayer = currentPlayer; }
    public void setScore1(int score1) { this.score1 = score1; }
    public void setScore2(int score2) { this.score2 = score2; }
    public void setBoard_data(String board_data) { this.board_data = board_data; }
    public void setBorderSize(int borderSize) { this.borderSize = borderSize; }
    public int getBorderSize() { return this.borderSize; }
}
