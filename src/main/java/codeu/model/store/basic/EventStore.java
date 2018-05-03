package codeu.model.store.basic;

import codeu.model.data.Event;
import codeu.model.store.persistence.PersistentStorageAgent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;


public class EventStore {
	/** Singleton instance of EventStore. */
	private static EventStore instance;

	/** Redefine compare function for Comparator for each event by comparing timestamps. */
	private Comparator<Event> eventComparator = new Comparator<Event>() {
		@Override
		public int compare (Event firstEvent, Event secondEvent) {
			return firstEvent.getTimeStamp().compareTo(secondEvent.getTimeStamp());

		}
	};
	/** 
	* Returns the singleton instance of UserStore that should be shared between all servlet classes.
	* Do not call function from a test; use getTestInstance() instead.
	*/
	public static EventStore getInstance() {
		if (instance == null) {
			instance = new EventStore(PersistentStorageAgent.getInstance());
		}
		return instance;
	}

	/**
	* Instance getter function used for tests. Supply a mock for PersistentStorageAgent.
	*
	* @param persistenStorageAgent is mock used for testing
	*/

	public static EventStore getTestInstance(PersistentStorageAgent persistentStorageAgent) {
		return new EventStore(persistentStorageAgent);
	}

	/** The PersistentStorageAgent responsible for loading in Events and saving Events to the DataStore. */
	private PersistentStorageAgent persistentStorageAgent;
	private List<Event> events;

	/** This class is a singleton, so its constructor is private. Call getInstance() instead. */
	private EventStore(PersistentStorageAgent persistentStorageAgent) {
		this.persistentStorageAgent = persistentStorageAgent;
		events = new ArrayList<>();
	}

	// in progress method to load set of randomly-generated event objects
	public void loadTestData() {
		events.addAll(DefaultDataStore.getInstance().getAllEvents());
	}

	/** Returns all the events of this given instance. */
	public List<Event> getAllEvents() {
		return events;
	}

	/** Returns all the events of this given instance sorted using eventComparator. */
	public List<Event> getAllEventsSorted() {
		events.sort(eventComparator);
		return events;
	}

	/** Adds an event to the the current set of events known to application. */
	public void addEvent(Event event) {
		events.add(event);
		persistentStorageAgent.writeThrough(event);
	}

	/**
	* Sets the List of Events stored by this EventStore. Should be called
	* only once when data is loaded from DataStore.
	*/
	public void setEvents(List<Event> events) {
		this.events = events;
	}

}
