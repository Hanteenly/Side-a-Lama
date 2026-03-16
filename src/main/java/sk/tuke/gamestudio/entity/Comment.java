package sk.tuke.gamestudio.entity;


import java.util.Date;

public class Comment {
    private String game;
    private String comment;
    private Date playedOn;
    private String player;

    public Comment(String game, String player, String comment, Date playedOn) {
        this.game = game;
        this.comment = comment;
        this.playedOn = playedOn;
        this.player = player;
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
