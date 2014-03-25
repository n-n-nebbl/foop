package at.tuwien.foop.labyrinth;

import at.tuwien.foop.labyrinth.event.DoorEvent;
import at.tuwien.foop.labyrinth.event.DoorEventHandler;
import at.tuwien.foop.labyrinth.event.EventBus;
import at.tuwien.foop.labyrinth.event.MouseMoveEvent;
import at.tuwien.foop.labyrinth.event.MouseMoveEventHandler;

public class StartLabyrinth {

	public static void main(String[] args){
		EventBus bus = new EventBus();
		bus.addEventHandler(new DoorEventHandler(), DoorEvent.class);
		bus.addEventHandler(new MouseMoveEventHandler(), MouseMoveEvent.class);
		
		bus.fireEvent(new MouseMoveEvent());
		bus.fireEvent(new DoorEvent());
		bus.fireEvent(new MouseMoveEvent());
	}
}
