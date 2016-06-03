package MomApiPackage.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;

import MomApiPackage.MomDate;

/**
 * Created by Robin on 02/06/2016.
 */
public class TaskComment implements Serializable {
    private int id;
    private String content;
    private MomDate dateCreated;
    private int creatorId;

    public TaskComment(int id, String name, String dateCreated, int creatorId) throws ParseException {
        this.id = id;
        this.content = name;
        this.dateCreated = new MomDate(dateCreated);
        this.creatorId = creatorId;
    }

    public TaskComment(JSONObject json) throws JSONException, ParseException {
        id = json.getInt("pk");
        content = json.getString("content");
        dateCreated = new MomDate(json.getString("date_created"));
        creatorId = json.getInt("pk_user_created_by");
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
