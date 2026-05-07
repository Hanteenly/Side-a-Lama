package sk.tuke.gamestudio.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@NamedQuery(name = "AdditionalComment.getAdditionalComment",
            query = "SELECT a FROM AdditionalComment a WHERE a.comment=:comment ORDER BY a.playedOn DESC")
@NamedQuery(name = "AdditionalComment.resetAdditionalComment",
            query = "DELETE FROM AdditionalComment")
public class AdditionalComment implements Serializable {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @com.fasterxml.jackson.annotation.JsonBackReference
    private AdditionalComment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    @com.fasterxml.jackson.annotation.JsonManagedReference
    private List<AdditionalComment> replies;

    private String commentText;

    private String comment;
    private String additionalComment;
    private Date playedOn;
    private String player;

    public AdditionalComment(){

    }
    public AdditionalComment(String comment, String player, String additionalComment, Date playedOn){
        this.comment = comment;
        this.player = player;
        this.additionalComment = additionalComment;
        this.playedOn = playedOn;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPlayer() {
        return player;
    }
    public void setPlayer(String player) {
        this.player = player;
    }

    public String getAdditionalComment(){
        return additionalComment;
    }
    public void setAdditionalComment(String additionalComment){
        this.additionalComment = additionalComment;
    }

    public Date getPlayedOn() {
        return playedOn;
    }
    public void setPlayedOn(Date playedOn) {
        this.playedOn = playedOn;
    }

    public AdditionalComment getParent() {
        return parent;
    }

    public void setParent(AdditionalComment parent) {
        this.parent = parent;
    }

    public List<AdditionalComment> getReplies() {
        return replies;
    }

    public void setReplies(List<AdditionalComment> replies) {
        this.replies = replies;
    }

    public String toString(){
        return "AdditionalComment{"+
                "commet = " + comment + '\''+
                ", player = " + player + '\'' +
                ", additionalComment = " + additionalComment +
                ", playedOn = " + playedOn +
                '}';
    }
}
