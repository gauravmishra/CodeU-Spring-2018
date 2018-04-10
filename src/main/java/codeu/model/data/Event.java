package codeu.model.data;

import java.time.Instant;

public abstract class Event {
    protected Instant timeStamp;

    public Instant getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Instant timeStamp) {
        this.timeStamp = timeStamp;
    }
}