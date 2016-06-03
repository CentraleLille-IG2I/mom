package MomApiPackage.Model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import MomApiPackage.MomDate;

/**
 * Created by Robin on 30/05/2016.
 */
public class EventStatus implements Comparable<EventStatus>, Serializable {
    private int id;
    private String content;
    private MomDate creationDate;
    private int creatorId;

    public EventStatus(int id, String content, String creationDate, int creatorId) throws ParseException {
        this.id = id;
        this.content = content;
        this.creationDate = new MomDate(creationDate);
        this.creatorId = creatorId;
    }

    public EventStatus(JSONObject o) throws JSONException, ParseException {
        this.id = o.getInt("pk");
        this.content = o.getString("content");
        this.creationDate = new MomDate(o.getString("date_created"));
        this.creatorId = o.getInt("pk_user_created_by");
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

    @Override
    public int compareTo(EventStatus another) {
        return creationDate.compareTo(another.getCreationDate());
    }
}
