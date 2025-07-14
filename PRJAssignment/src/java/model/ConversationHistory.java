package model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "ConversationHistory")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConversationHistory.findAll", query = "SELECT c FROM ConversationHistory c"),
    @NamedQuery(name = "ConversationHistory.findByHistoryID", query = "SELECT c FROM ConversationHistory c WHERE c.historyID = :historyID"),
    @NamedQuery(name = "ConversationHistory.findByMessageRole", query = "SELECT c FROM ConversationHistory c WHERE c.messageRole = :messageRole"),
    @NamedQuery(name = "ConversationHistory.findByMessageContent", query = "SELECT c FROM ConversationHistory c WHERE c.messageContent = :messageContent"),
    @NamedQuery(name = "ConversationHistory.findByCreatedAt", query = "SELECT c FROM ConversationHistory c WHERE c.createdAt = :createdAt"),
    @NamedQuery(name = "ConversationHistory.findBySessionID", query = "SELECT c FROM ConversationHistory c WHERE c.sessionID = :sessionID"),
    @NamedQuery(name = "ConversationHistory.findByUserID", query = "SELECT c FROM ConversationHistory c WHERE c.userID = :userID")
})
public class ConversationHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HistoryID")
    private Integer historyID;

    @Column(name = "UserID", nullable = false)
    private Integer userID;

    @Column(name = "MessageRole", nullable = false, length = 20)
    private String messageRole;

    @Column(name = "MessageContent", nullable = false, columnDefinition = "NVARCHAR(MAX)")
    private String messageContent;

    @Column(name = "CreatedAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "SessionID", length = 100)
    private String sessionID;

    public ConversationHistory() {
    }

    public ConversationHistory(Integer historyID) {
        this.historyID = historyID;
    }

    public ConversationHistory(Integer historyID, String messageRole, String messageContent, Integer userID) {
        this.historyID = historyID;
        this.messageRole = messageRole;
        this.messageContent = messageContent;
        this.userID = userID;
    }

    public Integer getHistoryID() {
        return historyID;
    }

    public void setHistoryID(Integer historyID) {
        this.historyID = historyID;
    }

    public String getMessageRole() {
        return messageRole;
    }

    public void setMessageRole(String messageRole) {
        this.messageRole = messageRole;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (historyID != null ? historyID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ConversationHistory)) {
            return false;
        }
        ConversationHistory other = (ConversationHistory) object;
        if ((this.historyID == null && other.historyID != null) || (this.historyID != null && !this.historyID.equals(other.historyID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.ConversationHistory[ historyID=" + historyID + " ]";
    }
}
