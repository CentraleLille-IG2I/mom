package MomApiPackage.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Robin on 02/06/2016.
 */
public class TaskComment implements Serializable {
    private int id;
    private String content;
    private String dateCreated;
    private int creatorId;

    public TaskComment(int id, String name, String dateCreated, int creatorId) {
        this.id = id;
        this.content = name;
        this.dateCreated = dateCreated;
        this.creatorId = creatorId;
    }

    public TaskComment(JSONObject json) throws JSONException {
        id = json.getInt("pk");
        content = json.getString("content");
        dateCreated = json.getString("date_created");
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

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
}
