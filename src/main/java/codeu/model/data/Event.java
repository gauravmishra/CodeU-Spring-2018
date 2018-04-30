package codeu.model.data;

import java.time.Instant;

public abstract class Event {
    protected Instant timeStamp;
    // Added eventType to distinguish between different events
    public String eventType;

    public Instant getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Instant timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getEventType(){
    	return eventType;
    }

    public void setEventType(String eventType){
    	this.eventType = eventType;
    }


}