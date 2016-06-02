package MomApiPackage.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Robin on 01/06/2016.
 */
public class Task implements Serializable{
    private int id;
    private String name;
    private String creationDate;
    private int creatorId;

    public Task(int id, String name, String creationDate, int creatorId) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
        this.creatorId = creatorId;
    }

    public Task(JSONObject json) throws JSONException {
        id = json.getInt("pk");
        name = json.getString("name");
        creationDate = json.getString("date_created");
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
