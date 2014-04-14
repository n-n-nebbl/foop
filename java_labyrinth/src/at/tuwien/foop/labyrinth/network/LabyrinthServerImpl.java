package at.tuwien.foop.labyrinth.network;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import at.tuwien.foop.labyrinth.event.Event;

public class LabyrinthServerImpl implements LabyrinthServer {

	private List<NetworkEventHandler> handlers;
	
	public LabyrinthServerImpl() {
		handlers = new ArrayList<>();
	}
	
	@Override
	public void addNetworkEventHandler(NetworkEventHandler handler) throws RemoteException{
		handlers.add(handler);
	}
	
	public void distributeEvent(Event event) throws RemoteException {
		for(NetworkEventHandler handler : handlers) {
			handler.fireEvent(event);
		}
	}
}
