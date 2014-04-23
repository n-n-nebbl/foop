package at.tuwien.foop.labyrinth.network;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import at.tuwien.foop.labyrinth.event.Event;
import at.tuwien.foop.labyrinth.model.Labyrinth;

public class LabyrinthServerImpl implements LabyrinthServer {

	private List<NetworkEventHandler> handlers;
	private Labyrinth labyrinth;
	
	public LabyrinthServerImpl() {
		handlers = new ArrayList<>();
	}
	
	@Override
	public void addNetworkEventHandler(NetworkEventHandler handler) throws RemoteException{
		handlers.add(handler);
	}
	
	@Override
	public void distributeEvent(Event event) throws RemoteException {
		for(NetworkEventHandler handler : handlers) {
			handler.fireEvent(event);
		}
	}

	@Override
	public Labyrinth getLabyrinth() throws RemoteException {
		return this.labyrinth;
	}

	public void setLabyrinth(Labyrinth labyrinth) {
		this.labyrinth = labyrinth;
	}
}
