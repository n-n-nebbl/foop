package at.tuwien.foop.labyrinth.gui;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import at.tuwien.foop.labyrinth.StartLabyrinth;
import at.tuwien.foop.labyrinth.event.DoorClickedEvent;
import at.tuwien.foop.labyrinth.event.GameEvent;
import at.tuwien.foop.labyrinth.event.MouseMoveEvent;
import at.tuwien.foop.labyrinth.model.Door;
import at.tuwien.foop.labyrinth.model.Map;
import at.tuwien.foop.labyrinth.model.Mouse;

@Component
public class LabyrinthController implements Observer
{
	@Resource(name = "doorList")
	private List<Door> doors;

	@Resource(name = "mouseList")
	private List<Mouse> mouseList;

	@Resource
	private LabyrinthView watched;

	private Mouse ownMouse = null;

	public void control() throws RemoteException
	{
		watched.addObserver(this);
		watched.waitForGameStart();
	}

	// Got a door event from the bus(from the server) -> repaint
	public void gotDoorEvent(DoorClickedEvent event, Door d)
	{
		watched.repaintAll();
	}

	public void gotGameEvent(GameEvent event)
	{
		switch (event.getType())
		{
		case GAMEENDED:
			System.out.println("Game event: " + event.getMessageText());
			watched.waitForGameStart(event.getValue());
			break;
		case INFORMATION:
			System.out.println("Game event: " + event.getMessageText());
			break;
		case GAMESTARTED:

			// Init labyrinth
			Map map;
			try
			{
				map = StartLabyrinth.getLabyrinthServer().getLabyrinth();
			} catch (RemoteException e)
			{
				System.out.println("gotGameEvent(): Error, getting the map.");
				e.printStackTrace();
				return;
			}

			doors.clear();
			for (Door d : map.getDoorList())
			{
				doors.add(d);
			}

			mouseList.clear();
			for (Mouse m : map.getMouseList())
			{
				mouseList.add(m);
			}

			watched.setGameRunning(map);

			for (Mouse m : mouseList)
			{
				if (m.getId() == event.getValue())
				{
					this.ownMouse = m;
				}
			}

			if (ownMouse != null)
			{
				watched.setTitel(ownMouse.getColor().toString());
				System.out.println("Game event: " + event.getMessageText());
			} else
			{
				System.out
						.println("gotGameEvent(): Error, setting mouse at the beginning.");
			}

			break;
		}
	}

	// Got a mouse event from the bus(from the server) -> repaint
	public void gotMouseEvent(MouseMoveEvent event, Mouse m)
	{
		watched.repaintAll();
	}

	// Someone clicked on the door -> send to the server
	@Override
	public void update(Observable o, Object arg)
	{

		// System.out.println("Observer Update Door:  " +
		// watched.getClickedButtonID());

		// Distribute the door event to the server
		try
		{

			Door d = null;

			for (Door door : this.doors)
			{
				if (door.getId() == watched.getClickedButtonID())
				{
					d = door;
				}
			}

			if (d == null)
			{
				return;
			}

			StartLabyrinth
					.getLabyrinthServer()
					.raiseDoorEvent(
							new DoorClickedEvent(
									watched.getClickedButtonID(),
									(d.getDoorStatus() == Door.DOOR_CLOSED) ? Door.DOOR_OPEN
											: Door.DOOR_CLOSED));
		} catch (RemoteException e)
		{
			System.out.println("Error, distributing event.");
			e.printStackTrace();
		}

	}

}
