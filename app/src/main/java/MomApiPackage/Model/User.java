package MomApiPackage.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Robin on 27/05/2016.
 */
public class User implements Serializable{
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    public User(int id, String firstName, String lastName, String email, String phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    public User(JSONObject json) throws JSONException {
        this.id = json.getInt("pk");
        this.firstName = json.getString("first_name");
        this.lastName = json.getString("last_name");

        try {
            this.email = json.getString("email");
        } catch (JSONException e) {
            this.email = "";
        }
        try {
            this.phone = json.getString("phone_number");
        } catch (JSONException e) {
            this.phone = "";
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String first_name) {
        this.firstName = first_name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String last_name) {
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
