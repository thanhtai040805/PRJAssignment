package model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ReviewCars")
public class ReviewCar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewID;

    @Column(nullable = false, length = 100)
    private String globalKey; // Liên kết với XeOTo(GlobalKey)

    @Column(nullable = false)
    private int userID; // Liên kết với Users(UserID)

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, columnDefinition = "NVARCHAR(MAX)")
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    @Column(nullable = false, length = 20)
    private String status = "Hiện";

    // Getter/setter

    public Long getReviewID() { return reviewID; }
    public void setReviewID(Long reviewID) { this.reviewID = reviewID; }

    public String getGlobalKey() { return globalKey; }
    public void setGlobalKey(String globalKey) { this.globalKey = globalKey; }

    public int getUserID() { return userID; }
    public void setUserID(int userID) { this.userID = userID; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
