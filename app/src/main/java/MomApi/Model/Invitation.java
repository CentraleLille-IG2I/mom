package MomApi.Model;

import com.example.richou.mom.R;

/**
 * Created by richou on 02/06/16.
 */
public class Invitation {
    public enum Status {
        ACCEPTED,
        PENDING,
        REFUSED,
        UNKNOWN
    }

    public static Status getStatusFromString(String s) {
        switch (s) {
            case "A":
                return Status.ACCEPTED;
            case "P":
                return Status.PENDING;
            case "R":
                return Status.REFUSED;
            default: return Status.UNKNOWN;
        }
    }

    private int id;
    private Status status;
    private String content;
    private String dateCreated;
    private int eventId;
    private int creatorId;
    private User invited;
    private int rankId;

    public Invitation(int id, Status status, String content, String dateCreated, int eventId, int creatorId, User invited, int rankId) {
        this.id = id;
        this.status = status;
        this.content = content;
        this.dateCreated = dateCreated;
        this.eventId = eventId;
        this.creatorId = creatorId;
        this.invited = invited;
        this.rankId = rankId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public int getStatusStringResourceId() {
        switch (status) {
            case ACCEPTED: return R.string.Invitation_accepted;
            case PENDING: return R.string.Invitation_pending;
            case REFUSED: return R.string.Invitation_refused;
            case UNKNOWN:
            default: return R.string.Invitation_unknown;
        }
    }

    public void setStatusByString(String status) {
        switch (status) {
            case "A":
                this.status = Status.ACCEPTED;
                break;
            case "P":
                this.status = Status.PENDING;
                break;
            case "R":
                this.status = Status.REFUSED;
                break;
            default: this.status = Status.UNKNOWN;
        }
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public User getInvited() {
        return invited;
    }

    public void setInvited(User invited) {
        this.invited = invited;
    }

    public int getRankId() {
        return rankId;
    }

    public void setRankId(int rankId) {
        this.rankId = rankId;
    }
}
