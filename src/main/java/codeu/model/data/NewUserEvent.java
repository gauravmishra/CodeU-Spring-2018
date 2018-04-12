package codeu.model.data;

import java.time.Instant;

public class NewUserEvent extends Event {
    private String userName;
    private String userLink;

    /**
     * Constructs a new event that shows users registration activity.
     *
     * @param userName      name of the user who registered
     * @param userLink      link to the profile of the user who registered
     * @param timeStamp     the time at which this event happened
     */
    public NewUserEvent(String userName, String userLink, Instant timeStamp) {
        this.userName = userName;
        this.userLink = userLink;
        super.timeStamp = timeStamp;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLink() {
        return userLink;
    }

    public void setUserLink(String userLink) {
        this.userLink = userLink;
    }

    @Override
    public String toString() {
        return  this.userName +
                " ( " + this.userLink + " )" +
                " has registered at" +
                " " + super.timeStamp.toString() + "\n";
    }
}
