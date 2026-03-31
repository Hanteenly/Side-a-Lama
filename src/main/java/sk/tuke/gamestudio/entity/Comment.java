package sk.tuke.gamestudio.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

@Entity
@NamedQuery(name = "Comment.getComments",
        query = "SELECT c FROM Comment c WHERE c.game=:game ORDER BY c.playedOn DESC")
@NamedQuery(name = "Comment.resetComments",
        query = "DELETE FROM Comment")
public class Comment implements Serializable {

    @Id
    @GeneratedValue
    private int ident;

    private String game;
    private String comment;
    private Date playedOn;
    private String player;

    public Comment() {
    }
    public Comment(String game, String player, String comment, Date playedOn) {
        this.game = game;
        this.comment = comment;
        this.playedOn = playedOn;
        this.player = player;
    }

    public int getIdent() {
        return ident;
    }
    public void setIdent(int ident) {
        this.ident = ident;
    }
    public String getGame() {
        return game;
    }
    public void setGame(String game) {
        this.game = game;
    }

    public String getPlayer() {
        return player;
    }
    public void setPlayer(String player) {
        this.player = player;
    }

    public Date getPlayedOn() {
        return playedOn;
    }
    public void setPlayedOn(Date playedOn) {
        this.playedOn = playedOn;
    }

    public String getComments() {
        return comment;
    }
    public void setComments(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "game = " + game + '\''+
                ", player = " + player + '\'' +
                ", comment = " + comment +
                ", playedOn = " + playedOn +
                '}';
    }
}
