package at.tuwien.foop.labyrinth;


import java.net.BindException;
import java.rmi.ConnectException;
import java.rmi.RemoteException;

import javax.swing.SwingUtilities;

import org.springframework.beans.BeansException;

import at.tuwien.foop.labyrinth.event.DoorClickedEvent;
import at.tuwien.foop.labyrinth.event.EventBus;
import at.tuwien.foop.labyrinth.event.GameEvent;
import at.tuwien.foop.labyrinth.event.GameEvent.GameEventType;
import at.tuwien.foop.labyrinth.event.MouseMoveEvent;
import at.tuwien.foop.labyrinth.gui.LabyrinthController;
import at.tuwien.foop.labyrinth.gui.LabyrinthView;
import at.tuwien.foop.labyrinth.model.Door;
import at.tuwien.foop.labyrinth.network.LabyrinthServer;
import at.tuwien.foop.labyrinth.network.LabyrinthServerImpl;
import at.tuwien.foop.labyrinth.network.NetworkEventHandler;
import at.tuwien.foop.labyrinth.network.RmiService;

public class StartLabyrinth {

	private static LabyrinthServerImpl server = null;
	private static LabyrinthServer remote = null;
	private static EventBus localEventBus = null;
	private static RmiService rmiService = null;
	private static NetworkEventHandler networkEventHandlerConnectedToEventBus = null;
	
	public static void main(String[] args) throws BeansException, RemoteException {
				
		EventBus bus = ContextHolder.getContext().getBean(EventBus.class);

		bus.fireEvent(new MouseMoveEvent());
		bus.fireEvent(new MouseMoveEvent());
		bus.fireEvent(new DoorClickedEvent(0, Door.DOOR_CLOSED));
		bus.fireEvent(new MouseMoveEvent());
		bus.fireEvent(new GameEvent(GameEventType.INFORMATION, "Game initialising..."));
		
		rmiService = ContextHolder.getContext().getBean(RmiService.class);
			
		// Local host mode only! Can be changed!
		
		try // Trying to start the server
		{		
			rmiService.startRegistry();
			server = new LabyrinthServerImpl();
			rmiService.bindLabyrinthServer(server);
		}
		catch(java.rmi.server.ExportException e) // If there is already a server -> client mode
		{
			System.out.println("Server already running, client mode.");
			//e.printStackTrace();
		}
		
		try
		{
			remote = rmiService.getLabyrinthServer("localhost");
		}
		catch(ConnectException e)
		{
			System.out.println("Error, connecting to the server.");			
			e.printStackTrace();
		}
		

		if(remote.gameIsRunning())
		{
			System.out.println("Error, game is already running...");
			onExit();
			return;
		}
		
		// Start the control :)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {                                           
                LabyrinthController controller = ContextHolder.getContext().getBean(LabyrinthController.class);
                try {
					controller.control();
				} catch (RemoteException e) {
					System.out.println("Error, getting the labyrinth from the server.");	
					e.printStackTrace();
				}
            }
        });  


		// Connects the bus
		networkEventHandlerConnectedToEventBus = (NetworkEventHandler) ContextHolder.getContext().getBean("networkEventHandlerStub");
		
		try
		{
			remote.addNetworkEventHandler(networkEventHandlerConnectedToEventBus);
		}
		catch(RemoteException r)
		{
			System.out.println("Error, adding to the player list: " + r.toString());
		}
	}
	
	public static LabyrinthServer getLabyrinthServer()
	{
		return remote;
	}
	
	public static void onExit()
	{
		try {
			remote.removeNetworkEventHandler(networkEventHandlerConnectedToEventBus);
		} catch (RemoteException e1) {
			System.out.println("Error, removing network event handler.");
			e1.printStackTrace();
		}
		
		// Todo: rmitest:
		if(rmiService != null)
		{
			try {
				rmiService.stopRegistry();
			} 
			catch (RemoteException e) {
				System.out.println("Error, stopping RMI registry.");
				e.printStackTrace();
			}
		}
		
		ContextHolder.getContext().close();
	}
}
