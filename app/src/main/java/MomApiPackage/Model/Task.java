package MomApiPackage.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;

import MomApiPackage.MomDate;

/**
 * Created by Robin on 01/06/2016.
 */
public class Task implements Serializable{
    private int id;
    private String name;
    private MomDate creationDate;
    private int creatorId;

    public Task(int id, String name, String creationDate, int creatorId) throws ParseException {
        this.id = id;
        this.name = name;
        this.creationDate = new MomDate(creationDate);
        this.creatorId = creatorId;
    }

    public Task(JSONObject json) throws JSONException, ParseException {
        id = json.getInt("pk");
        name = json.getString("name");
        creationDate = new MomDate(json.getString("date_created"));
        creatorId = json.getInt("pk_user_created_by");
    }

    public int getId() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
