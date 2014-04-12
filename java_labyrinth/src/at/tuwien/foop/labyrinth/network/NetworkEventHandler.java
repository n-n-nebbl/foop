package at.tuwien.foop.labyrinth.network;

import java.rmi.Remote;

import at.tuwien.foop.labyrinth.event.Event;

public interface NetworkEventHandler extends Remote {

	public void fireEvent(Event event);
}
