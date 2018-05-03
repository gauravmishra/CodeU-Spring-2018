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
     * @param eventType             the type of the instance of event
     */
    public NewUserEvent(String userName, String userLink, Instant timeStamp, String eventType) {
        this.userName = userName;
        this.userLink = userLink;
        super.timeStamp = timeStamp;
        super.eventType = eventType;
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
