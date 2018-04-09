package codeu.model.data;

import java.time.Instant;

public class Event {
    public static enum EventType{
        NewUser,
        Login,
        Logout,
        NewConversation,
        NewMessage
    }

    private EventType type;
    private String link;
    private Instant timeStamp;

    /**
     * Constructs a new event that requires showing a link to the user.
     *
     * @param type          the type of this event
     * @param link          the link to that event
     * @param timeStamp     the time at which this event happened
     */
    public Event(EventType type, String link, Instant timeStamp) {
        this.type = type;
        this.link = link;
        this.timeStamp = timeStamp;
    }

    /**
     * Constructs a new event that doesn't need a link.
     *
     * @param type          the type of this event
     * @param timeStamp     the time at which this event happened
     */
    public Event(EventType type, Instant timeStamp) {
        this.type = type;
        this.timeStamp = timeStamp;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    /**
     *
     * @return a string that points to the link if one exists,
     * otherwise returns an empty string ""
     */
    public String getLink() {
        if(link!=null) {
            return link;
        } else {
            return "";
        }
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Instant getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Instant timeStamp) {
        this.timeStamp = timeStamp;
    }
}