package MomApiPackage.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Robin on 02/06/2016.
 */
public class TaskItem implements Serializable {
    private int id;
    private String name;
    private boolean completed;
    private String dateCreated;

    public TaskItem(int id, String name, boolean completed, String dateCreated) {
        this.id = id;
        this.name = name;
        this.completed = completed;
        this.dateCreated = dateCreated;
    }

    public TaskItem(JSONObject json) throws JSONException {
        id = json.getInt("pk");
        name = json.getString("name");
        completed = parseBoolean(json.getString("completed"));
        dateCreated = json.getString("date_created");
    }

    private boolean parseBoolean(String txt) {
        if (txt.equals("true"))
            return true;
        else
            return false;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
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

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }


}
