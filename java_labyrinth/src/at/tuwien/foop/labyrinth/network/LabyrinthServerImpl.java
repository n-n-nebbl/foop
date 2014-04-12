package at.tuwien.foop.labyrinth.network;

import java.util.ArrayList;
import java.util.List;

import at.tuwien.foop.labyrinth.event.Event;

public class LabyrinthServerImpl implements LabyrinthServer {

	private List<NetworkEventHandler> handlers;
	
	public LabyrinthServerImpl() {
		handlers = new ArrayList<>();
	}
	
	@Override
	public void addNetworkEventHandler(NetworkEventHandler handler) {
		handlers.add(handler);
	}
	
	public void distributeEvent(Event event) {
		for(NetworkEventHandler handler : handlers) {
			handler.fireEvent(event);
		}
	}
}
