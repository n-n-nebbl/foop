package at.tuwien.foop.labyrinth;

import java.rmi.RemoteException;

import javax.swing.SwingUtilities;

import org.springframework.beans.BeansException;

import at.tuwien.foop.labyrinth.event.EventBus;
import at.tuwien.foop.labyrinth.event.GameEvent;
import at.tuwien.foop.labyrinth.event.GameEvent.GameEventType;
import at.tuwien.foop.labyrinth.gui.Dialogs;
import at.tuwien.foop.labyrinth.gui.LabyrinthController;
import at.tuwien.foop.labyrinth.network.LabyrinthServer;
import at.tuwien.foop.labyrinth.network.LabyrinthServerImpl;
import at.tuwien.foop.labyrinth.network.NetworkEventHandler;
import at.tuwien.foop.labyrinth.network.RmiService;

public class StartLabyrinth
{

	private static LabyrinthServerImpl server = null;
	private static LabyrinthServer remote = null;
	private static RmiService rmiService = null;
	private static NetworkEventHandler networkEventHandlerConnectedToEventBus = null;

	public static LabyrinthServer getLabyrinthServer()
	{
		return remote;
	}

	public static void main(String[] args) throws BeansException,
			RemoteException
	{
		EventBus bus = ContextHolder.getContext().getBean(EventBus.class);

		bus.fireEvent(new GameEvent(GameEventType.INFORMATION,
				"Game initialising..."));

		rmiService = ContextHolder.getContext().getBean(RmiService.class);

		// Local host mode only! Can be changed!

		boolean serverMode = Dialogs.showServerOrClientDialog();

		if (serverMode)
		{
			// Trying to start the server
			try
			{
				rmiService.startRegistry();
				server = new LabyrinthServerImpl();
				rmiService.bindLabyrinthServer(server);
			} catch (java.rmi.server.ExportException e) // If there is already a
														// server -> client mode
			{
				System.out.println("Server already running, client mode.");
				// e.printStackTrace();
			}
		}

		try
		{
			if (serverMode)
				remote = rmiService.getLabyrinthServer("localhost");
			else
			{
				String address = Dialogs.showIPDialog();

				if (address == null || address.isEmpty())
				{
					onExit();
					return;
				}

				remote = rmiService.getLabyrinthServer(address);
			}
		} catch (Exception e)
		{
			System.out.println("Error, connecting to the server.");
			e.printStackTrace();
			onExit();
			return;
		}

		if (remote.gameIsRunning())
		{
			System.out.println("Error, game is already running...");
			onExit();
			return;
		}

		// Start the control :)
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				System.out.println("Starting.");

				LabyrinthController controller = ContextHolder.getContext()
						.getBean(LabyrinthController.class);
				try
				{
					controller.control();
				} catch (RemoteException e)
				{
					System.out
							.println("Error, getting the labyrinth from the server.");
					e.printStackTrace();
				}
			}
		});

		// Connects the bus
		networkEventHandlerConnectedToEventBus = (NetworkEventHandler) ContextHolder
				.getContext().getBean("networkEventHandlerStub");

		try
		{
			remote.addNetworkEventHandler(networkEventHandlerConnectedToEventBus);
		} catch (RemoteException r)
		{
			System.out.println("Error, adding to the player list: "
					+ r.toString());
		}
	}

	public static void onExit()
	{
		try
		{
			if (remote != null)
				remote.removeNetworkEventHandler(networkEventHandlerConnectedToEventBus);
		} catch (RemoteException e1)
		{
			System.out.println("Error, removing network event handler.");
			e1.printStackTrace();
		}

		ContextHolder.getContext().close();
	}
}
