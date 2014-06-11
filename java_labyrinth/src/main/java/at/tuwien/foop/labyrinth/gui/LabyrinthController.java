package at.tuwien.foop.labyrinth.gui;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import at.tuwien.foop.labyrinth.ContextHolder;
import at.tuwien.foop.labyrinth.StartLabyrinth;
import at.tuwien.foop.labyrinth.event.DoorClickedEvent;
import at.tuwien.foop.labyrinth.event.EventBus;
import at.tuwien.foop.labyrinth.event.MessageEvent;
import at.tuwien.foop.labyrinth.event.MouseMoveEvent;
import at.tuwien.foop.labyrinth.model.Door;
import at.tuwien.foop.labyrinth.model.Map;
import at.tuwien.foop.labyrinth.model.Mouse;

@Component
public class LabyrinthController implements Observer {

	@Resource
	private EventBus bus;

	@Resource(name="doorList")
	private List<Door> doors;
	
	@Resource(name="mouseList")
	private List<Mouse> mouseList;

	@Resource
	private LabyrinthView watched;

	public void control() throws RemoteException{
		watched.addObserver(this);
		
		// Init labyrinth
		Map map = StartLabyrinth.getLabyrinthServer().getLabyrinth();
		
		for(Door d : map.getDoorList())
			doors.add(d);

		for(Mouse m : map.getMouseList())
			mouseList.add(m);

		watched.startLabyrinth(map);
	}
	
	// Got a door event from the bus(from the server) -> repaint
	public void gotDoorEvent(DoorClickedEvent event, Door d)
	{	
		watched.repaintAll();
	}

	// Got a mouse event from the bus(from the server) -> repaint
	public void gotMouseEvent(MouseMoveEvent event, Mouse m)
	{	
		watched.repaintAll();		
	}


	public void gotTextMessageEvent(MessageEvent event) 
	{
		// Print: draw
		System.out.println("Got message event: " + event.getMessageText());
	}
	
	// Someone clicked on the door -> send to the server
	@Override
	public void update(Observable o, Object arg) {		
					
		//System.out.println("Observer Update Door:  " + watched.getClickedButtonID());
		
		// Distribute the door event to the server
		try {
			// Status will be set by the server
			StartLabyrinth.getLabyrinthServer().raiseDoorEvent(new DoorClickedEvent(watched.getClickedButtonID(), -1));
		} 
		catch (RemoteException e)
		{
			System.out.println("Error, distributing event.");
			e.printStackTrace();
		}
		
	}

}



