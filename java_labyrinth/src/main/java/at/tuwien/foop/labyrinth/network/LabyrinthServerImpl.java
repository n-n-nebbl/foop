package at.tuwien.foop.labyrinth.network;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import at.tuwien.foop.labyrinth.event.Event;
import at.tuwien.foop.labyrinth.model.Map;

public class LabyrinthServerImpl implements LabyrinthServer {

	private List<NetworkEventHandler> handlers;
	private Map labyrinth;

	public LabyrinthServerImpl() {
		handlers = new ArrayList<>();
	}

	@Override
	public void addNetworkEventHandler(NetworkEventHandler handler)
			throws RemoteException {
		handlers.add(handler);
	}

	@Override
	public void removeNetworkEventHandler(NetworkEventHandler handler)
			throws RemoteException {
		System.out.println(handlers.remove(handler));
	}

	@Override
	public void distributeEvent(Event event) throws RemoteException {
		/**
		 * TODO: Logic for gameplay
		 */
		for (NetworkEventHandler handler : handlers) {
			handler.fireEvent(event);
		}
	}

	@Override
	public Map getLabyrinth() throws RemoteException {
		return this.labyrinth;
	}

	public void setLabyrinth(Map labyrinth) {
		this.labyrinth = labyrinth;
	}
}
