// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package codeu.model.store.persistence;

import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.data.Event;
import codeu.model.data.LoginLogoutEvent;
import codeu.model.data.NewConversationEvent;
import codeu.model.data.NewMessageEvent;
import codeu.model.data.NewUserEvent;
import codeu.model.data.Profile;
import codeu.model.store.persistence.PersistentDataStoreException;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.FilterOperator;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.awt.image.BufferedImage;

/**
 * This class handles all interactions with Google App Engine's Datastore service. On startup it
 * sets the state of the applications's data objects from the current contents of its Datastore. It
 * also performs writes of new of modified objects back to the Datastore.
 */
@SuppressWarnings("unchecked")
public class PersistentDataStore {

  // Handle to Google AppEngine's Datastore service.
  private DatastoreService datastore;

  /**
   * Constructs a new PersistentDataStore and sets up its state to begin loading objects from the
   * Datastore service.
   */
  public PersistentDataStore() {
    datastore = DatastoreServiceFactory.getDatastoreService();
  }

  /**
   * Loads all User objects from the Datastore service and returns them in a List.
   *
   * @throws PersistentDataStoreException if an error was detected during the load from the
   *     Datastore service
   */
  public List<User> loadUsers() throws PersistentDataStoreException {

    List<User> users = new ArrayList<>();

    // Retrieve all users from the datastore.
    Query query = new Query("chat-users");
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      try {
        UUID uuid = UUID.fromString((String)entity.getProperty("uuid"));
        String userName = (String)entity.getProperty("username");
        String password = (String)entity.getProperty("password");
        Instant creationTime = Instant.parse((String)entity.getProperty("creation_time"));
        String following = (String)entity.getProperty("following");
        User user = new User(uuid, userName, password, creationTime, following);

        users.add(user);
      } catch (Exception e) {
        // In a production environment, errors should be very rare. Errors which may
        // occur include network errors, Datastore service errors, authorization errors,
        // database entity definition mismatches, or service mismatches.
        throw new PersistentDataStoreException(e);
      }
    }

    return users;
  }

  /**
   * Loads all Conversation objects from the Datastore service and returns them in a List.
   *
   * @throws PersistentDataStoreException if an error was detected during the load from the
   *     Datastore service
   */
  public List<Conversation> loadConversations() throws PersistentDataStoreException {

    List<Conversation> conversations = new ArrayList<>();

    // Retrieve all conversations from the datastore.
    Query query = new Query("chat-conversations");
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      try {
        UUID uuid = UUID.fromString((String) entity.getProperty("uuid"));
        UUID ownerUuid = UUID.fromString((String) entity.getProperty("owner_uuid"));
        String title = (String) entity.getProperty("title");
        Instant creationTime = Instant.parse((String) entity.getProperty("creation_time"));
        Conversation conversation = new Conversation(uuid, ownerUuid, title, creationTime);
        conversations.add(conversation);
      } catch (Exception e) {
        // In a production environment, errors should be very rare. Errors which may
        // occur include network errors, Datastore service errors, authorization errors,
        // database entity definition mismatches, or service mismatches.
        throw new PersistentDataStoreException(e);
      }
    }

    return conversations;
  }

  /**
   * Loads all Message objects from the Datastore service and returns them in a List.
   *
   * @throws PersistentDataStoreException if an error was detected during the load from the
   *     Datastore service
   */
  public List<Message> loadMessages() throws PersistentDataStoreException {

    List<Message> messages = new ArrayList<>();

    // Retrieve all messages from the datastore.
    Query query = new Query("chat-messages");
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      try {
        UUID uuid = UUID.fromString((String) entity.getProperty("uuid"));
        UUID conversationUuid = UUID.fromString((String) entity.getProperty("conv_uuid"));
        UUID authorUuid = UUID.fromString((String) entity.getProperty("author_uuid"));
        Instant creationTime = Instant.parse((String) entity.getProperty("creation_time"));
        String content = (String) entity.getProperty("content");
        Message message = new Message(uuid, conversationUuid, authorUuid, content, creationTime);
        messages.add(message);
      } catch (Exception e) {
        // In a production environment, errors should be very rare. Errors which may
        // occur include network errors, Datastore service errors, authorization errors,
        // database entity definition mismatches, or service mismatches.
        throw new PersistentDataStoreException(e);
      }
    }

    return messages;
  }
  /*
  * Loads all Event objects from the DataStore service and returns them in a List.
  *
  * @throws PersistentDataStoreException if an error was detected during the load from the
  * DataStore service
  */

  public List<Event> loadEvents() throws PersistentDataStoreException {
    List<Event> events = new ArrayList<>();
    Query query = new Query("chat-events");
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity: results.asIterable()) {
      try {
        // Loads in each variable
        Instant creationTime = Instant.parse((String) entity.getProperty("creation_time"));
        String eventType = (String) entity.getProperty("event_type");
        String userName = (String) entity.getProperty("username");
        String userLink = (String) entity.getProperty("userlink");
        String conversationName = (String) entity.getProperty("conversation-name");
        String conversationLink = (String) entity.getProperty("conversation-link");
        boolean inOrOut = (boolean) entity.getProperty("inOrOut");
        // Depending on what type of event type it is; add different types of subclasses to event list.
        if (eventType == "login-event") {
          Event event = new LoginLogoutEvent(userName, userLink, creationTime, eventType, inOrOut);
          events.add(event);
        } 
        else if (eventType == "conversation-event") {
          Event event = new NewConversationEvent(creationTime, eventType, conversationName, conversationLink);
          events.add(event);
        }
        else if (eventType == "register-event") {
          Event event = new NewUserEvent(userName, userLink, creationTime, eventType);
          events.add(event);
        }
        else if (eventType == "message-event") {
          Event event = new NewMessageEvent(userName, userLink, creationTime, eventType, conversationName, conversationLink);
          events.add(event);
        } 
      }catch (Exception e) {
          // In a production environment, errors should be very rare. Errors which may
          // occur include network errors, Datastore service errors, authorization errors,
          // database entity definition mismatches, or service mismatches.
          throw new PersistentDataStoreException(e);
      }
    }
    return events;
  }

  /**
   * Loads all Profile objects from the Datastore service and returns them in a List.
   *
   * @throws PersistentDataStoreException if an error was detected during the load from the
   *     Datastore service
   */
  public List<Profile> loadProfiles() throws PersistentDataStoreException {

    List<Profile> profiles = new ArrayList<>();

    // Retrieve all profiles from the datastore.
    Query query = new Query("chat-profiles");
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      try {
        UUID uuid = UUID.fromString((String) entity.getProperty("uuid"));
        Instant creationTime = Instant.parse((String) entity.getProperty("creation_time"));
        String about = (String) entity.getProperty("about");
        List<Message> messages = (List<Message>) entity.getProperty("message_history");
        BufferedImage photo = (BufferedImage) entity.getProperty("photo");
        Profile profile = new Profile(uuid, creationTime, about, messages, photo);
        profiles.add(profile);
      } catch (Exception e) {
        // In a production environment, errors should be very rare. Errors which may
        // occur include network errors, Datastore service errors, authorization errors,
        // database entity definition mismatches, or service mismatches.
        throw new PersistentDataStoreException(e);
      }
    }

    return profiles;
  }

  /** Write a User object to the Datastore service. */
  public void writeThrough(User user) {
    Entity userEntity = new Entity("chat-users");
    userEntity.setProperty("uuid", user.getId().toString());
    userEntity.setProperty("username", user.getName());
    userEntity.setProperty("password", user.getPassword());
    userEntity.setProperty("creation_time", user.getCreationTime().toString());
    userEntity.setProperty("following", user.getFollowingUsersString());
    datastore.put(userEntity);
  }

  /** Write a Message object to the Datastore service. */
  public void writeThrough(Message message) {
    Entity messageEntity = new Entity("chat-messages");
    messageEntity.setProperty("uuid", message.getId().toString());
    messageEntity.setProperty("conv_uuid", message.getConversationId().toString());
    messageEntity.setProperty("author_uuid", message.getAuthorId().toString());
    messageEntity.setProperty("content", message.getContent());
    messageEntity.setProperty("creation_time", message.getCreationTime().toString());
    datastore.put(messageEntity);
  }

  /** Write a Conversation object to the Datastore service. */
  public void writeThrough(Conversation conversation) {
    Entity conversationEntity = new Entity("chat-conversations");
    conversationEntity.setProperty("uuid", conversation.getId().toString());
    conversationEntity.setProperty("owner_uuid", conversation.getOwnerId().toString());
    conversationEntity.setProperty("title", conversation.getTitle());
    conversationEntity.setProperty("creation_time", conversation.getCreationTime().toString());
    datastore.put(conversationEntity);
  }
  
  /** Writes an Event object to the DataStore service.*/
  public void writeThrough(Event event) {
    Entity eventEntity = new Entity("chat-events");
    eventEntity.setProperty("creation_time", event.getTimeStamp().toString());
    eventEntity.setProperty("event_type", event.getEventType().toString());

    if (event.getEventType() == "login-event"){
      LoginLogoutEvent tempEvent = (LoginLogoutEvent) event;
      eventEntity.setProperty("username", tempEvent.getUserName().toString());
      eventEntity.setProperty("inOrOut", tempEvent.isInOrOut());
      eventEntity.setProperty("userlink", tempEvent.getUserLink().toString());
    }
    else if (event.getEventType() == "conversation-event") {
      NewConversationEvent tempEvent = (NewConversationEvent) event;
      eventEntity.setProperty("conversation-name", tempEvent.getConversationName().toString());
      eventEntity.setProperty("conversation-link", tempEvent.getConversationLink().toString());
    }
    else if (event.getEventType() == "register-event") {
      NewUserEvent tempEvent = (NewUserEvent) event;
      eventEntity.setProperty("username", tempEvent.getUserName().toString());
      eventEntity.setProperty("userlink", tempEvent.getUserLink().toString());
    }
    else if (event.getEventType() == "message-event") {
      NewMessageEvent tempEvent = (NewMessageEvent) event;
      eventEntity.setProperty("username", tempEvent.getUserName().toString());
      eventEntity.setProperty("userlink", tempEvent.getUserLink().toString());
      eventEntity.setProperty("conversation-name", tempEvent.getConversationName().toString());
      eventEntity.setProperty("conversation-link", tempEvent.getConversationLink().toString());
    }
  }

  /** Write a Profile object to the Datastore service. */
  public void writeThrough(Profile profile) {
    // Delete profile in entity if already present
    Filter idFilter = new FilterPredicate("uuid", FilterOperator.EQUAL, profile.getId().toString());
    Query query = new Query("chat-profiles").setFilter(idFilter);
    PreparedQuery results = datastore.prepare(query);
    for (Entity entity : results.asIterable()) {
      datastore.delete(entity.getKey());
    }
    Entity profileEntity = new Entity("chat-profiles", profile.getId().toString());
    profileEntity.setProperty("uuid", profile.getId().toString());
    profileEntity.setProperty("creation_time", profile.getCreationTime().toString());
    profileEntity.setProperty("about", profile.getAbout());
    profileEntity.setProperty("message_history", profile.getMessages());
    profileEntity.setProperty("photo", profile.getPhoto());
    datastore.put(profileEntity);
  }
}
