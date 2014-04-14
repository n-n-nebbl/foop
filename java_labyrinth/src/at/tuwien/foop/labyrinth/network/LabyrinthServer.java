package at.tuwien.foop.labyrinth.network;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LabyrinthServer extends Remote {

	/**
	 * Adds an remote Object to the labyrinth server
	 * @param handler Has to be an already exported Remote-Stub!!! (UnicastcastRemoteObject.export(obj, 0))
	 * @throws RemoteException
	 */
	public void addNetworkEventHandler(NetworkEventHandler handler) throws RemoteException;
}
