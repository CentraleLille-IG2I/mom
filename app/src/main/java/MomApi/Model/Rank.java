package MomApi.Model;

/**
 * Created by richou on 02/06/16.
 */
public class Rank {
    private int id;
    private String name;
    private String description;
    private int idEvent;
    private boolean isAttendee, isOrganiser, isAdmin;
    private String dateCreated;

    public Rank(int id, String name, String description, int idEvent, boolean isAttendee, boolean isOrganiser, boolean isAdmin, String dateCreated) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.idEvent = idEvent;
        this.isAttendee = isAttendee;
        this.isOrganiser = isOrganiser;
        this.isAdmin = isAdmin;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    public boolean isAttendee() {
        return isAttendee;
    }

    public void setIsAttendee(boolean isAttendee) {
        this.isAttendee = isAttendee;
    }

    public boolean isOrganiser() {
        return isOrganiser;
    }

    public void setIsOrganiser(boolean isOrganiser) {
        this.isOrganiser = isOrganiser;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
