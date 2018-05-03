package codeu.model.data;

import codeu.model.store.basic.UserStore;
import java.time.Instant;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
import java.awt.image.BufferedImage;

/** Class representing a created profile. */
public class Profile {
    private final UUID id;
    private final Instant creation;
    private final String name;
    private String password;
    private String about;
    private List<Message> messages = new ArrayList<Message>();
    private BufferedImage photo;

    /**
     * Constructs a new Profile.
     *
     * @param id       the ID of the User who's Profile it is
     * @param creation the creation time of this Profile
     * @param about    about me section
     * @param messages message history
     * @param photo    User's profile photo
     */
    public Profile(UUID id, Instant creation, String about,
                   List<Message> messages, BufferedImage photo) {
        UserStore userStore = UserStore.getInstance();
        this.id = id;
        this.creation = creation;
        this.name = (userStore.getUser(id)).getName();
        this.password = (userStore.getUser(id)).getPassword();
        this.about = about;
        this.messages = messages;
        this.photo = photo;
    }

    /**
     * Returns the ID of the User whose profile it is.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Returns the creation time of this Profile.
     */
    public Instant getCreationTime() {
        return creation;
    }

    /**
     * Gets the name of the User whose Profile it is.
     */
    public String getName() { return name;}

    /**
     * Reset password for the User whose profile it is
     */
    public void setPassword(String Password){
        UserStore.getInstance().getUser(id).setPassword(password);
    }

    /**
     * Get password for the User whose profile it is
     */
    public String getPassword(){
        return password;
    }

    /**
     * Sets a new about section for the Profile
     */
    public void setAbout(String about) { this.about = about; }

    /**
     * Returns the current about section of this Profile.
     */
    public String getAbout() {
        return about;
    }

    /**
     * Updates the message history of the User whose Profile it is
     */
    public void setMessages(List<Message> messages) { this.messages =
            messages; }

    /**
     * Returns the message history of the User whose Profile it is
     */
    public List<Message> getMessages() {
        return messages;
    }

    /**
     * Sets a new profile photo
     */
    public void setPhoto(BufferedImage photo) { this.photo = photo; }

    /**
     * Gets the current profile photo
     */
    public BufferedImage getPhoto() { return photo; }
}