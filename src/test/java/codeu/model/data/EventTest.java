package codeu.model.data;

import org.junit.Assert;
import org.junit.Test;

import java.time.Instant;

public class EventTest {
    @Test
    public void testCreateWithLink() {
        Instant creation = Instant.now();
        String link = "test_link";
        Event.EventType eType = Event.EventType.NewConversation;
        Event event = new Event(eType, link, creation);

        Assert.assertEquals(event.getTimeStamp(), creation);
        Assert.assertEquals(event.getLink(), link);
        Assert.assertEquals(event.getType(), eType);
    }

    @Test
    public void testCreateWithoutLink() {
        Instant creation = Instant.now();
        Event.EventType eType = Event.EventType.NewMessage;
        Event event = new Event(eType, creation);

        Assert.assertEquals(event.getTimeStamp(), creation);
        Assert.assertEquals(event.getType(), eType);
        Assert.assertEquals(event.getLink(), "");
    }

}