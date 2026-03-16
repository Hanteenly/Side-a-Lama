package sk.tuke.gamestudio.entity;


import java.util.Date;

public class Rating {
    private String game;
    private String player;
    private int rating;
    private Date rated_date;

    public Rating(String game, String player, int rating, Date rated_date) {
        this.game = game;
        this.player = player;
        this.rating = rating;
        this.rated_date = rated_date;
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
