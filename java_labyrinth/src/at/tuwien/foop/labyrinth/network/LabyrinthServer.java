package at.tuwien.foop.labyrinth.network;

import java.rmi.Remote;

public interface LabyrinthServer extends Remote {

	public void addNetworkEventHandler(NetworkEventHandler handler);
}
