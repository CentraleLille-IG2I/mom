package MomApiPackage.Model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;

import MomApiPackage.MomDate;

/**
 * Created by Robin on 28/05/2016.
 */
public class Event implements Serializable {
    private int id;
    private String name;
    private String description;
    private MomDate date;
    private String place;
    private MomDate creationDate;
    private int creatorId;

    private Rank rank;

    public Event(int id, String name, String description, String date, String place, String creationDate, int creatorId) throws ParseException {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = new MomDate(date);
        this.place = place;
        this.creationDate = new MomDate(creationDate);
        this.creatorId = creatorId;
    }

    public Event(JSONObject json) throws JSONException, ParseException {
        this.id = json.getInt("pk");
        this.name = json.getString("name");
        this.description = json.getString("description");
        this.date = new MomDate(json.getString("date"));
        this.place = json.getString("place_event");
        this.creationDate = new MomDate(json.getString("date_created"));
        this.creatorId = json.getInt("pk_user_created_by");

        if(json.has("rank")) {
            JSONObject o = json.getJSONObject("rank");
            Log.d("@", "rankLength : " + o.length() + "\n" + o);
            if (o.length() == 0)
                rank = null;
            else
                rank = new Rank(o);
        }
        else
            rank = null;
    }

    public boolean canOrganise(User u) {
        if (rank == null){
            if (creatorId == u.getId())
                return true;
            else
                return false; //not suppose to append
        }
        else {
            if (rank.isOrganiser())
                return true;
            else
                return false;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MomDate getDate() {
        return date;
    }

    public void setDate(String date) throws ParseException {
        this.date.setDate(date);
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public MomDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) throws ParseException {
        this.creationDate.setDate(creationDate);
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }
}
