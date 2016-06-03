package MomApiPackage.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;

import MomApiPackage.MomDate;

/**
 * Created by Robin on 01/06/2016.
 */
public class Rank implements Serializable{
    private int id;
    private boolean isAttendee;
    private boolean isOrganiser;
    private boolean isAdmin;
    private String description;
    private MomDate dateCreated;
    private String name;
    private int idEvent;

    public Rank(JSONObject o) throws JSONException, ParseException {
        id = o.getInt("pk");
        isAttendee = parseBoolean(o.getString("is_attendee"));
        isOrganiser = parseBoolean(o.getString("is_organiser"));
        isAdmin = parseBoolean(o.getString("is_admin"));
        description = o.getString("description");
        dateCreated = new MomDate(o.getString("date_created"));
        idEvent = o.getInt("pk_event");
        name = o.getString("name");
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

    public MomDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) throws ParseException {
        this.dateCreated.setDate(dateCreated);
    }

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
