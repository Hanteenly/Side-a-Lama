package sk.tuke.gamestudio.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@NamedQuery(name = "GameState.getGameState",
        query = "SELECT g FROM GameState g WHERE g = g")
@NamedQuery(name = "GameState.load",
        query = "SELECT g FROM GameState g WHERE g.GameName = :GameName")
@NamedQuery(name = "GameState.resetGameStates",
        query = "DELETE FROM GameState")
public class GameState implements Serializable {

    @Id
    @GeneratedValue
    private int ident;

    private String GameName;
    private String Player1;
    private String Player2;
    private String CurrentPlayer;
    private int score1;
    private int score2;
    private String board_data;

    public GameState() {

    }
    public GameState(String GameName, String Player1, String Player2, String CurrentPlayer, int score1, int score2, String board_data) {
        this.GameName = GameName;
        this.Player1 = Player1;
        this.Player2 = Player2;
        this.CurrentPlayer = CurrentPlayer;
        this.score1 = score1;
        this.score2 = score2;
        this.board_data = board_data;
    }

    public int getIdent() {
        return ident;
    }
    public void setIdent(int ident) {
        this.ident = ident;
    }
    public String getGameName() {
        return GameName;
    }

    public String getPlayer1() {
        return Player1;
    }

    public String getPlayer2() {
        return Player2;
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
                + "GameName = " + GameName + '\''
                + ", Player1 = " + Player1 + '\''
                + ", Player2 = " + Player2 + '\''
                + ", CurrentPlayer = " + CurrentPlayer + '\''
                + ", score1 = " + score1 + '\''
                + ", score2 = " + score2 + '\''
                + ", board_data = " + board_data +
                '}';
    }
}
