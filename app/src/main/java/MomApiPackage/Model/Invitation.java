package MomApiPackage.Model;

import com.example.richou.mom.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

import MomApiPackage.Model.*;
import MomApiPackage.MomDate;

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

    private int id;
    private Status status;
    private String content;
    private MomDate dateCreated;
    private int eventId;
    private int creatorId;
    private User invited;
    private int rankId;

    public Invitation(int id, Status status, String content, String dateCreated, int eventId, int creatorId, User invited, int rankId) throws ParseException {
        this.id = id;
        this.status = status;
        this.content = content;
        this.dateCreated = new MomDate(dateCreated);
        this.eventId = eventId;
        this.creatorId = creatorId;
        this.invited = invited;
        this.rankId = rankId;
    }

    public Invitation(JSONObject o) throws JSONException, ParseException {
        this.id = o.getInt("pk");
        this.status = getStatusFromString(o.getString("status"));
        this.content = o.getString("content");
        this.dateCreated = new MomDate(o.getString("date_created"));
        this.eventId = o.getInt("pk_event");
        this.creatorId = o.getInt("pk_user_created_by");
        //this.invited = o.getInt("pk_user_invited");
        this.rankId = o.getInt("pk_rank");

           /* ‘pk’ : <invitation_pk>,
            ‘content’ : <invitation_content>,
            ‘status’ : <invitation_status>,
            ‘date_created’ : <invitation_date_created>,
            ‘pk_event’ : <invitation_event_pk>,
            ‘pk_rank’ : <invitation_rank_pk>
            ‘pk_user_created_by’ : <invitation_author_user_pk>,
            ‘pk_user_invited’ : <invitation_user_pk>*/

    }

    public static Status getStatusFromString(String s) {
        switch (s) {
            case "A":
                return Status.ACCEPTED;
            case "P":
                return Status.PENDING;
            case "R":
                return Status.REFUSED;
            default:
                return Status.UNKNOWN;
        }
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

    public String getStatusString() {
        if (status == Status.ACCEPTED)
            return "A";
        else if (status == Status.PENDING)
            return "P";
        else if (status == Status.REFUSED)
            return "R";
        else
            return null;
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

    public MomDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) throws ParseException {
        this.dateCreated.setDate(dateCreated);
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
