package MomApiPackage.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Robin on 01/06/2016.
 */
public class Rank implements Serializable{
    private int id;
    private boolean isAttendee;
    private boolean isOrganiser;
    private boolean isAdmin;
    private String description;
    private String dateCreated;
    private int idEvent;

    public Rank(JSONObject o) throws JSONException {
        id = o.getInt("pk");
        isAttendee = parseBoolean(o.getString("is_attendee"));
        isOrganiser = parseBoolean(o.getString("is_organiser"));
        isAdmin = parseBoolean(o.getString("is_admin"));
        description = o.getString("description");
        dateCreated = o.getString("date_created");
        idEvent = o.getInt("pk_event");
    }

    private boolean parseBoolean(String txt) {
        if (txt.equals("true"))
            return true;
        else
            return false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAttendee() {
        return isAttendee;
    }

    public void setIsAttendee(boolean isAttendee) {
        this.isAttendee = isAttendee;
    }

    public boolean isOrganiser() {
        return isOrganiser;
    }

    public void setIsOrganiser(boolean isOrganiser) {
        this.isOrganiser = isOrganiser;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }
}
