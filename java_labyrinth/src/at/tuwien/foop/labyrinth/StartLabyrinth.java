package at.tuwien.foop.labyrinth;

import at.tuwien.foop.labyrinth.event.DoorClickedEvent;
import at.tuwien.foop.labyrinth.event.DoorClickedEventHandler;
import at.tuwien.foop.labyrinth.event.EventBus;
import at.tuwien.foop.labyrinth.event.MouseMoveEvent;
import at.tuwien.foop.labyrinth.event.MouseMoveEventHandler;
import at.tuwien.foop.labyrinth.model.Door;

public class StartLabyrinth {

	public static void main(String[] args){
		EventBus bus = new EventBus();
		bus.addEventHandler(new DoorClickedEventHandler(), DoorClickedEvent.class);
		bus.addEventHandler(new MouseMoveEventHandler(), MouseMoveEvent.class);
		
		bus.fireEvent(new MouseMoveEvent());
		bus.fireEvent(new DoorClickedEvent(1, Door.DOOR_OPEN));
		bus.fireEvent(new MouseMoveEvent());
	}
}
