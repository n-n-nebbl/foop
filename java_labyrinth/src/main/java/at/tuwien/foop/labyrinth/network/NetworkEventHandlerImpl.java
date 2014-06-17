package at.tuwien.foop.labyrinth.network;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.tuwien.foop.labyrinth.event.Event;
import at.tuwien.foop.labyrinth.event.EventBus;

@Component
public class NetworkEventHandlerImpl implements NetworkEventHandler
{

	@Autowired
	private EventBus eventBus;

	public NetworkEventHandlerImpl()
	{}

	@Override
	public void fireEvent(Event event) throws RemoteException
	{
		eventBus.fireEvent(event);
	}

	/**
	 * Is used by spring and should not be called manually
	 * 
	 * @return stub of this object (remote proxy)
	 * @throws RemoteException
	 */
	public NetworkEventHandler export() throws RemoteException
	{
		return (NetworkEventHandler)UnicastRemoteObject.exportObject(this, 0);
	}

	/**
	 * Is called by spring on shutdown and should not be called manually
	 */
	@PreDestroy
	public void unexport()
	{
		try
		{
			UnicastRemoteObject.unexportObject(this, true);
		}
		catch(Exception e)
		{
			System.out.println("unexport(): Error during unexport of NetworkEventHandler");
		}
	}
}
