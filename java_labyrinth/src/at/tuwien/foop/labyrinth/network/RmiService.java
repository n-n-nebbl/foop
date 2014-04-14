package at.tuwien.foop.labyrinth.network;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RmiService {

	private static final String bindingName = "labyrinthServer";

	public static LabyrinthServer registerToServer(String host) throws RemoteException {
		Registry registry = LocateRegistry.getRegistry(host);
		try {
			LabyrinthServer server = (LabyrinthServer) registry
					.lookup(bindingName);
			return server;
		} catch (NotBoundException ex) {
			System.out.println("Error occured during bind: "
					+ ex.getMessage());
			return null;
		}
	}

	public static boolean startRegistry() throws RemoteException {
		Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
		return (registry != null);
	}
	
	public static void bindLabyrinthServer(LabyrinthServer server) throws RemoteException {
		Registry registry = LocateRegistry.getRegistry();
		LabyrinthServer stub = (LabyrinthServer) UnicastRemoteObject
				.exportObject(server, 0);
		registry.rebind(bindingName, stub);	
	}

	public static void stopRegistry() throws RemoteException {
		Registry registry = LocateRegistry.getRegistry();
		try {
			registry.unbind(bindingName);
		} catch (NotBoundException ex) {
			System.out.println("Error occured during unbind: "
					+ ex.getMessage());
		}
	}
}
