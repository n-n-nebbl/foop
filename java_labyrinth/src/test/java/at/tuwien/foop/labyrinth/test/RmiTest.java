package at.tuwien.foop.labyrinth.test;

import static org.mockito.Mockito.*;

import java.rmi.RemoteException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import at.tuwien.foop.labyrinth.event.EventBus;
import at.tuwien.foop.labyrinth.event.MouseMoveEvent;
import at.tuwien.foop.labyrinth.event.MouseMoveEventHandler;
import at.tuwien.foop.labyrinth.network.LabyrinthServer;
import at.tuwien.foop.labyrinth.network.LabyrinthServerImpl;
import at.tuwien.foop.labyrinth.network.NetworkEventHandlerImpl;
import at.tuwien.foop.labyrinth.network.RmiService;

public class RmiTest {

	ClassPathXmlApplicationContext ctx;
	
	LabyrinthServerImpl server;
	LabyrinthServer remote;
	EventBus localEventBus;
	RmiService rmiService;

	@Before
	public void setUpTest() throws RemoteException {
		ctx = new ClassPathXmlApplicationContext("application-context.xml");
		rmiService = ctx.getBean(RmiService.class);
		rmiService.startRegistry();
		server = new LabyrinthServerImpl();
		rmiService.bindLabyrinthServer(server);
		remote = rmiService.getLabyrinthServer("localhost");

		localEventBus = new EventBus();
	}

	@After
	public void tearDownTest() throws RemoteException {
		rmiService.stopRegistry();
		ctx.close();
	}

	@Test
	public void testRmi() throws RemoteException {
		// Client side
		MouseMoveEvent event = new MouseMoveEvent();
		MouseMoveEventHandler mouseHandler = mock(MouseMoveEventHandler.class);
		when(mouseHandler.getEventClass()).thenReturn(MouseMoveEvent.class);
		
		NetworkEventHandlerImpl handler = new NetworkEventHandlerImpl(
				localEventBus);

		localEventBus.addEventHandler(mouseHandler);
		// send remote object reference to server-side
		remote.addNetworkEventHandler(handler.export());

		// server side (fire event)
		server.distributeEvent(event);

		// check if event is fired on right object on client side
		// any(MouseMoveEvent.class) used, because the event gets serialized and
		// sent over the network --> not equals event created here
		verify(mouseHandler).eventFired(any(MouseMoveEvent.class));
	}

}
