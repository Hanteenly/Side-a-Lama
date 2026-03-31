package sk.tuke.gamestudio.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

@Entity
@NamedQuery(name = "Rating.getAllRatings",
        query = "SELECT rating FROM Rating rating")
@NamedQuery(name = "Rating.getRating",
        query = "SELECT r FROM Rating r WHERE r.game=:game AND r.player=:player")
@NamedQuery(name = "Rating.getAverageRating",
        query = "SELECT AVG(r.rating) FROM Rating r WHERE r.game=:game")
@NamedQuery(name = "Rating.resetRatings",
        query = "DELETE FROM Rating")

public class Rating implements Serializable {
    @Id
    @GeneratedValue
    private int ident;

    private String game;
    private String player;
    private int rating;
    private Date rated_date;

    public Rating() {

    }
    public Rating(String game, String player, int rating, Date rated_date) {
        this.game = game;
        this.player = player;
        this.rating = rating;
        this.rated_date = rated_date;
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

    public int getRating(){
        return rating;
    }
    public void setRating(int rating){
        this.rating = rating;
    }

    public Date getRated_date(){
        return rated_date;
    }
    public void setRated_date(Date rated_date){
        this.rated_date = rated_date;
    }

}
