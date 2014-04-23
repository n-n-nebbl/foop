package at.tuwien.foop.labyrinth.event;

public interface EventHandler<T extends Event> {

	public void eventFired(T event);
	
	public Class<T> getEventClass();
}
