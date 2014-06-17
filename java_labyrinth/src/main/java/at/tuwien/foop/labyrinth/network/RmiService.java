package at.tuwien.foop.labyrinth.network;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

@Component
public class RmiService
{
	private final String bindingName = "labyrinthServer";
	private LabyrinthServer boundServer;
	private Registry registry;

	public void bindLabyrinthServer(LabyrinthServer server) throws RemoteException
	{
		LabyrinthServer stub = (LabyrinthServer)UnicastRemoteObject.exportObject(server, 0);
		registry.rebind(bindingName, stub);
		boundServer = server;
	}

	public LabyrinthServer getLabyrinthServer(String host) throws RemoteException
	{
		Registry remoteRegistry = LocateRegistry.getRegistry(host);
		try
		{
			LabyrinthServer server = (LabyrinthServer)remoteRegistry.lookup(bindingName);
			return server;
		}
		catch(NotBoundException ex)
		{
			System.out.println("Error occured during bind: " + ex.getMessage());
			return null;
		}
	}

	public boolean startRegistry() throws RemoteException
	{
		registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
		return (registry != null);
	}

	@PreDestroy
	public void stopRegistry() throws RemoteException
	{
		if(registry == null)
		{
			return;
		}
		try
		{
			registry.unbind(bindingName);
			if(boundServer != null)
			{
				UnicastRemoteObject.unexportObject(boundServer, true);
			}
		}
		catch(NotBoundException nbe)
		{}
		catch(Exception e)
		{
			System.out.println("Error occured during unbinding of server: " + e.getMessage());
		}
		try
		{
			UnicastRemoteObject.unexportObject(registry, true);
		}
		catch(Exception ex)
		{
			System.out.println("Error occured during unbinding of registry: " + ex.getMessage());
		}

	}
}
