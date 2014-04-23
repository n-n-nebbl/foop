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
import at.tuwien.foop.labyrinth.network.NetworkEventHandler;
import at.tuwien.foop.labyrinth.network.RmiService;

public class RmiTest {

	ClassPathXmlApplicationContext ctx;

	LabyrinthServerImpl server;
	LabyrinthServer remote;
	EventBus localEventBus;
	RmiService rmiService;
	NetworkEventHandler stub;

	@Before
	public void setUpTest() throws RemoteException {
		ctx = new ClassPathXmlApplicationContext("application-context.xml");
		localEventBus = ctx.getBean(EventBus.class);
		rmiService = ctx.getBean(RmiService.class);

		rmiService.startRegistry();
		server = new LabyrinthServerImpl();
		rmiService.bindLabyrinthServer(server);
		remote = rmiService.getLabyrinthServer("localhost");
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

		localEventBus.addEventHandler(mouseHandler);
		// send remote object reference to server-side
		stub = (NetworkEventHandler) ctx.getBean("networkEventHandlerStub");
		remote.addNetworkEventHandler(stub);

		// server side (fire event)
		server.distributeEvent(event);

		// check if event is fired on right object on client side
		// any(MouseMoveEvent.class) used, because the event gets serialized and
		// sent over the network --> not equals event created here
		verify(mouseHandler).eventFired(any(MouseMoveEvent.class));
	}

	@Test
	public void testRemoveFromServer() throws RemoteException {
		// Client side
		MouseMoveEvent event = new MouseMoveEvent();
		MouseMoveEventHandler mouseHandler = mock(MouseMoveEventHandler.class);
		when(mouseHandler.getEventClass()).thenReturn(MouseMoveEvent.class);

		localEventBus.addEventHandler(mouseHandler);
		
		// send remote object reference to server-side
		stub = (NetworkEventHandler) ctx.getBean("networkEventHandlerStub");
		remote.addNetworkEventHandler(stub);
		remote.removeNetworkEventHandler(stub);
		

		// server side (fire event)
		server.distributeEvent(event);

		// check if event is fired on right object on client side
		// any(MouseMoveEvent.class) used, because the event gets serialized and
		// sent over the network --> not equals event created here
		verify(mouseHandler, times(0)).eventFired(any(MouseMoveEvent.class));
	}
}
