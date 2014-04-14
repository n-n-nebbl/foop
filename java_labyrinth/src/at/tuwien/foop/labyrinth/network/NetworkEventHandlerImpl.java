package at.tuwien.foop.labyrinth.network;

import java.rmi.RemoteException;

import at.tuwien.foop.labyrinth.event.Event;
import at.tuwien.foop.labyrinth.event.EventBus;

public class NetworkEventHandlerImpl implements NetworkEventHandler {

	private EventBus eventBus;
	
	public NetworkEventHandlerImpl(EventBus eventBus) {
		this.eventBus = eventBus;
	}
	
	@Override
	public void fireEvent(Event event) throws RemoteException {
		eventBus.fireEvent(event);
	}

}
