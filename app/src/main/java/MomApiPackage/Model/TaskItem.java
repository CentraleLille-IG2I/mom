package MomApiPackage.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;

import MomApiPackage.MomDate;

/**
 * Created by Robin on 02/06/2016.
 */
public class TaskItem implements Serializable {
    private int id;
    private String name;
    private boolean completed;
    private MomDate dateCreated;

    public TaskItem(int id, String name, boolean completed, String dateCreated) throws ParseException {
        this.id = id;
        this.name = name;
        this.completed = completed;
        this.dateCreated = new MomDate(dateCreated);
    }

    public TaskItem(JSONObject json) throws JSONException, ParseException {
        id = json.getInt("pk");
        name = json.getString("name");
        completed = parseBoolean(json.getString("completed"));
        dateCreated = new MomDate(json.getString("date_created"));
    }

    private boolean parseBoolean(String txt) {
        if (txt.equals("true"))
            return true;
        else
            return false;
    }

    public MomDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) throws ParseException {
        this.dateCreated.setDate(dateCreated);
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
