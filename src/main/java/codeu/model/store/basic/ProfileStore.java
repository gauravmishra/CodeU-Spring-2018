package codeu.model.store.basic;

import codeu.model.store.persistence.PersistentStorageAgent;
import codeu.model.data.Profile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Store class that uses in-memory data structures to hold values and automatically loads from and
 * saves to PersistentStorageAgent. It's a singleton so all servlet classes can access the same
 * instance.
 */
public class ProfileStore {

  /** Singleton instance of ProfileStore. */
  private static ProfileStore instance;

  /**
   * Returns the singleton instance of ProfileStore that should be shared between all servlet
   * classes. Do not call this function from a test; use getTestInstance() instead.
   */
  public static ProfileStore getInstance() {
    if (instance == null) {
      instance = new ProfileStore(PersistentStorageAgent.getInstance());
    }
    return instance;
  }

  /**
   * Instance getter function used for testing. Supply a mock for PersistentStorageAgent.
   *
   * @param persistentStorageAgent a mock used for testing
   */
  public static ProfileStore getTestInstance(PersistentStorageAgent persistentStorageAgent) {
    return new ProfileStore(persistentStorageAgent);
  }

  /**
   * The PersistentStorageAgent responsible for loading Users from and saving Users to Datastore.
   */
  private PersistentStorageAgent persistentStorageAgent;

  /** The in-memory list of Profiles. */
  private List<Profile> profiles;

  /** This class is a singleton, so its constructor is private. Call getInstance() instead. */
  private ProfileStore(PersistentStorageAgent persistentStorageAgent) {
    this.persistentStorageAgent = persistentStorageAgent;
    profiles = new ArrayList<>();
  }

  /** Load a set of randomly-generated Message objects. */
  public void loadTestData() {
    profiles.addAll(DefaultDataStore.getInstance().getAllProfiles());
  }

  /**
   * Access the Profile object with the given name.
   *
   * @return null if username does not match any existing User.
   */
  public Profile getProfile(String username) {
    // This approach will be pretty slow if we have many users.
    for (Profile profile : profiles) {
      if (profile.getName().equals(username)) {
        return profile;
      }
    }
    return null;
  }

  /**
   * Access the Profile object with the given UUID.
   *
   * @return null if the UUID does not match any existing Profile.
   */
  public Profile getProfile(UUID id) {
    for (Profile profile : profiles) {
      if (profile.getId().equals(id)) {
        return profile;
      }
    }
    return null;
  }

  /** Add a new profile to the current set of profiles known to the application. */
  public void addProfile(Profile profile) {
    // Replaces current profile if present
    UUID id = profile.getId();
    //        Profile oldProfile = null;
    //        for (Profile curProfile : profiles) {
    //            if (curProfile.getId().equals(id)) {
    //                oldProfile = curProfile;
    //            }
    //        }
    //        if(oldProfile != null){
    //            profiles.remove(oldProfile);
    //        }
    for (Profile curProfile : profiles) {
      if (curProfile.getId().equals(id)) {
        curProfile = profile;
        persistentStorageAgent.writeThrough(profile);
        return;
      }
    }
    profiles.add(profile);
    persistentStorageAgent.writeThrough(profile);
  }

  /** Return true if the given profile is known to the application. */
  public boolean doesProfileExist(String username) {
    for (Profile profile : profiles) {
      if (profile.getName().equals(username)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Sets the List of Profiles stored by this ProfileStore. This should only be called once, when
   * the data is loaded from Datastore.
   */
  public void setProfiles(List<Profile> profiles) {
    this.profiles = profiles;
  }
}
