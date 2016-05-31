package MomApi.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Robin on 28/05/2016.
 */
public class Event implements Serializable {
    private int id;
    private String name;
    private String description;
    private String date;
    private String place;
    private String creationDate;
    private int creatorId;

    public Event(int id, String name, String description, String date, String place, String creationDate, int creatorId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.place = place;
        this.creationDate = creationDate;
        this.creatorId = creatorId;
    }

    public Event(JSONObject json) throws JSONException {
        this.id = json.getInt("pk");
        this.name = json.getString("name");
        this.description = json.getString("description");
        this.date = json.getString("date");
        this.place = json.getString("place_event");
        this.creationDate = json.getString("date_created");
        this.creatorId = json.getInt("pk_user_created_by");
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

    public String getDatre() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }
}
