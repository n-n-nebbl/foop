package at.tuwien.foop.labyrinth.gui;

import java.util.Observable;
import java.util.Observer;

import at.tuwien.foop.labyrinth.ContextHolder;
import at.tuwien.foop.labyrinth.event.DoorClickedEvent;
import at.tuwien.foop.labyrinth.event.EventBus;
import at.tuwien.foop.labyrinth.model.Door;

public class LabyrinthController implements Observer {

	LabyrinthView watched;
	EventBus bus;

	public void control(){
		bus = ContextHolder.getContext().getBean(EventBus.class);
		
		watched = new LabyrinthView();
		watched.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("Observer Update:  " + watched.getClickedButtonID());
		bus.fireEvent(new DoorClickedEvent(watched.getClickedButtonID(), Door.DOOR_OPEN));
	}

}



