package MomApiPackage.Model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Robin on 30/05/2016.
 */
public class EventStatus implements Comparable<EventStatus>, Serializable {
    private int id;
    private String content;
    private String creationDate;
    private int creatorId;

    public EventStatus(int id, String content, String creationDate, int creatorId) {
        this.id = id;
        this.content = content;
        this.creationDate = creationDate;
        this.creatorId = creatorId;
    }

    public EventStatus(JSONObject o) throws JSONException {
        this.id = o.getInt("pk");
        this.content = o.getString("content");
        this.creationDate = o.getString("date_created");
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

    @Override
    public int compareTo(EventStatus another) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        try {
            return format.parse(another.getCreationDate()).compareTo(format.parse(this.getCreationDate()));
        } catch (ParseException e) {
            Log.d("@", "DateParseError" + e);
            e.printStackTrace();
            return 0;
        }
    }
}
