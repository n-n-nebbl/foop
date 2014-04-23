package at.tuwien.foop.labyrinth.network;

import java.rmi.Remote;
import java.rmi.RemoteException;

import at.tuwien.foop.labyrinth.event.Event;
import at.tuwien.foop.labyrinth.model.Labyrinth;

public interface LabyrinthServer extends Remote {

	/**
	 * Adds an remote Object to the labyrinth server
	 * @param handler Has to be an already exported Remote-Stub!!! (UnicastcastRemoteObject.export(obj, 0))
	 * @throws RemoteException
	 */
	public void addNetworkEventHandler(NetworkEventHandler handler) throws RemoteException;
	
	/**
	 * Fires given Event on all registered NetworkEventHandlers
	 * @param e event being fired
	 * @throws RemoteException
	 */
	public void distributeEvent(Event e) throws RemoteException;
	
	/**
	 * @return the labyrinth generated on the server
	 * @throws RemoteException
	 */
	public Labyrinth getLabyrinth() throws RemoteException;
}