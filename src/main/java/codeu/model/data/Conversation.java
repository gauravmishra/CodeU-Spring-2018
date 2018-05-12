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

package codeu.model.data;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Class representing a conversation, which can be thought of as a chat room. Conversations are
 * created by a User and contain Messages.
 */
public class Conversation {
  public final UUID id;
  public final UUID owner;
  public final UUID recipient;
  public final Instant creation;
  public final String title;
//  public List<UUID> users;

  /**
   * Constructs a new Conversation.
   *
   * @param id the ID of this Conversation
   * @param owner the ID of the User who created this Conversation
   * @param title the title of this Conversation
   * @param creation the creation time of this Conversation
   */
  public Conversation(UUID id, UUID owner, UUID recipient, String title, Instant creation) {
    this.id = id;
    this.owner = owner;
    this.recipient = recipient;
    this.creation = creation;
    this.title = title;

//    this.addUser(owner);
//    this.addUser(recipient);
  }

  /** Returns the ID of this Conversation. */
  public UUID getId() {
    return id;
  }

  /** Returns the ID of the User who created this Conversation. */
  public UUID getOwnerId() {
    return owner;
  }

  /** Returns the ID of the User who created this Conversation. */
  public UUID getRecipientId() {
    return recipient;
  }

  /** Returns the title of this Conversation. */
  public String getTitle() {
    return title;
  }

  /** Returns the creation time of this Conversation. */
  public Instant getCreationTime() {
    return creation;
  }

  /** Returns the creation time of this Conversation. */
//  public List<UUID> getUsers() {
//    return users;
//  }
//
//  public void addUser(UUID u) {
//    users.add(u);
//  }
//
//  public void removeUser(UUID u) {
//    users.remove(u);
//  }

  public boolean hasUser(UUID u) {

    //    return this.users.contains(u);

    if (u.equals(owner)) {
      return true;
    }

    if (u.equals(recipient)) {
      return true;
    }

    return false;
  }
}
