package at.tuwien.foop.labyrinth.gui;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import at.tuwien.foop.labyrinth.ContextHolder;
import at.tuwien.foop.labyrinth.event.DoorClickedEvent;
import at.tuwien.foop.labyrinth.event.EventBus;
import at.tuwien.foop.labyrinth.event.MouseMoveEvent;
import at.tuwien.foop.labyrinth.model.Door;
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

	public void control(){
		watched.addObserver(this);
		watched.startLabyrinth();
	}

	@Override
	public void update(Observable o, Object arg) {		
		
		//clickedDoor_DoorClickedEvent
		for(Door d : doors){
			if(d.getDoorStatus()==1){
				d.setDoorStatus(2); //DOOR_CLOSED
			} else {
				d.setDoorStatus(1); //DOOR_OPENED
			}
		}
		System.out.println("Observer Update Door:  " + watched.getClickedButtonID());
		bus.fireEvent(new DoorClickedEvent(watched.getClickedButtonID(), doors.get(watched.getClickedButtonID()).getDoorStatus()));
	}

}



