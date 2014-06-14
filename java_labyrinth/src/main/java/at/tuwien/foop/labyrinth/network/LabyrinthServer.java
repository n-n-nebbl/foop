package at.tuwien.foop.labyrinth.network;

import java.rmi.Remote;
import java.rmi.RemoteException;

import at.tuwien.foop.labyrinth.event.DoorClickedEvent;
import at.tuwien.foop.labyrinth.event.Event;
import at.tuwien.foop.labyrinth.model.Map;

public interface LabyrinthServer extends Remote {

	/**
	 * Adds an remote Object to the labyrinth server
	 * @param handler Has to be an already exported Remote-Stub!!! (handler.export())
	 * @throws RemoteException
	 */
	public void addNetworkEventHandler(NetworkEventHandler handler) throws RemoteException;
	
	/**
	 * 
	 * @param handler 
	 * @throws RemoteException
	 */
	public void removeNetworkEventHandler(NetworkEventHandler handler) throws RemoteException;
	
	/**
	 * Fires given Event on all registered NetworkEventHandlers
	 * @param e event being fired
	 * @throws RemoteException
	 */
	public void raiseDoorEvent(DoorClickedEvent e) throws RemoteException;
	
	/**
	 * @return the labyrinth generated on the server
	 * @throws RemoteException
	 */
	public Map getLabyrinth() throws RemoteException;

	/**
	 * @return whether the game is running
	 * @throws RemoteException
	 */
	public boolean gameIsRunning() throws RemoteException;

	/**
	 * @throws RemoteException
	 */
	public void startGame() throws RemoteException;
}
