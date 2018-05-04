package codeu.model.data;

import java.time.*;
import java.time.Instant;
import java.time.format.DateTimeFormatter;


public class LoginLogoutEvent extends Event {
    public static boolean LOGGED_OUT = false;
    public static boolean LOGGED_IN = true;
    private String userName;
    private String userLink;
    private boolean inOrOut;

    /**
     * Constructs a new event that shows users login/logout activity
     *
     * @param userName      name of the user who logged in/logged out
     * @param userLink      link to the profile of the user who logged in/logged out
     * @param timeStamp     the time at which this event happened
     * @param eventType             the type of the instance of event
     * @param inOrOut       determines if the user logged in or logged out
     */
    public LoginLogoutEvent(String userName, String userLink, Instant timeStamp, String eventType, boolean inOrOut) {
        this.userName = userName;
        this.userLink = userLink;
        super.timeStamp = timeStamp;
        super.eventType = eventType;
        this.inOrOut = inOrOut;
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

    public boolean isInOrOut() {
        return inOrOut;
    }

    public void setInOrOut(boolean inOrOut) {
        this.inOrOut = inOrOut;
    }

    @Override
    public String toString() {
        LocalDateTime localdatetime = LocalDateTime.ofInstant(super.timeStamp,ZoneId.systemDefault());
        DateTimeFormatter datetimeformatter = DateTimeFormatter.ofPattern("dd/MM/yy h:mm:ss a");
        String time = localdatetime.format(datetimeformatter); 
        if(this.inOrOut) {
            return time + ": " + "<a href=\"/user/" + this.userName + "\">" + this.userName + "</a>" +
                " has logged in.\n"; 
        } else {
            return time + ": " + "<a href=\"/user/" + this.userName + "\">" + this.userName + "</a>" +
                " has logged out.\n"; 
        }
    }
}
