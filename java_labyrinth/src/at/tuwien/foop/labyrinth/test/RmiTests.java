package at.tuwien.foop.labyrinth.test;

import static org.mockito.Mockito.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import at.tuwien.foop.labyrinth.event.EventBus;
import at.tuwien.foop.labyrinth.event.MouseMoveEvent;
import at.tuwien.foop.labyrinth.event.MouseMoveEventHandler;
import at.tuwien.foop.labyrinth.network.LabyrinthServer;
import at.tuwien.foop.labyrinth.network.LabyrinthServerImpl;
import at.tuwien.foop.labyrinth.network.NetworkEventHandler;
import at.tuwien.foop.labyrinth.network.NetworkEventHandlerImpl;
import at.tuwien.foop.labyrinth.network.RmiService;

public class RmiTests {

	LabyrinthServerImpl server;
	LabyrinthServer remote;
	EventBus localEventBus;

	@Before
	public void setUpTest() throws RemoteException {
		RmiService.startRegistry();
		server = new LabyrinthServerImpl();
		RmiService.bindLabyrinthServer(server);
		remote = RmiService.registerToServer("localhost");

		localEventBus = new EventBus();
	}

	@After
	public void tearDownTest() throws RemoteException {
		RmiService.stopRegistry();
	}

	@Test
	public void testRmi() throws RemoteException {
		//Client side
		MouseMoveEvent event = new MouseMoveEvent();
		MouseMoveEventHandler mouseHandler = mock(MouseMoveEventHandler.class);
		NetworkEventHandler handler = new NetworkEventHandlerImpl(localEventBus);
		
		localEventBus.addEventHandler(mouseHandler, MouseMoveEvent.class);
		//send remote object reference to server-side
		remote.addNetworkEventHandler((NetworkEventHandler) UnicastRemoteObject
				.exportObject(handler, 0));
		
		//server side (fire event)
		server.distributeEvent(event);

		//check if event is fired on right object on client side
		// any() used, because the event gets serialized and sent over the
		// network --> not equals event created here
		verify(mouseHandler).eventFired(any(MouseMoveEvent.class));
	}

}
