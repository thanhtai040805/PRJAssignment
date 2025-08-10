package model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CarRatings")
public class CarRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratingID;

    @Column(nullable = false, length = 100)
    private String globalKey; // Liên kết với XeOTo.GlobalKey

    @Column(nullable = false)
    private int userID; // Liên kết với Users.UserID

    @Column(nullable = false)
    private int rating; // 1-5 sao

    @Column(length = 255)
    private String shortComment;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    @Column(nullable = false, length = 20)
    private String status = "Hiện";

    // Getters & Setters
    public Long getRatingID() { return ratingID; }
    public void setRatingID(Long ratingID) { this.ratingID = ratingID; }

    public String getGlobalKey() { return globalKey; }
    public void setGlobalKey(String globalKey) { this.globalKey = globalKey; }

    public int getUserID() { return userID; }
    public void setUserID(int userID) { this.userID = userID; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getShortComment() { return shortComment; }
    public void setShortComment(String shortComment) { this.shortComment = shortComment; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
