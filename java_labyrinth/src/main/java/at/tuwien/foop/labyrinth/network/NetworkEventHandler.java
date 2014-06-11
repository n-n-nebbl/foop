package at.tuwien.foop.labyrinth.network;

import java.rmi.Remote;
import java.rmi.RemoteException;

import at.tuwien.foop.labyrinth.event.Event;
import at.tuwien.foop.labyrinth.model.Mouse;

public interface NetworkEventHandler extends Remote {

	public void fireEvent(Event event) throws RemoteException;
}
