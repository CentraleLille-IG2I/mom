package MomApi.Model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Robin on 27/05/2016.
 */
public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    public User(int id, String first_name, String last_name, String email, String phone) {
        this.id = id;
        this.firstName = first_name;
        this.lastName = last_name;
        this.email = email;
        this.phone = phone;
    }

    public User(JSONObject json) throws JSONException {
        id = json.getInt("pk");
        firstName = json.getString("first_name");
        lastName = json.getString("last_name");
        email = json.getString("email");
        phone = json.getString("phone_number");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return firstName;
    }

    public void setFirst_name(String first_name) {
        this.firstName = first_name;
    }

    public String getLast_name() {
        return lastName;
    }

    public void setLast_name(String last_name) {
        this.lastName = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
