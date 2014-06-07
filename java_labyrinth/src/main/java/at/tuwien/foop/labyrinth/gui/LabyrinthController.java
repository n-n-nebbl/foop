package at.tuwien.foop.labyrinth.gui;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.annotation.Resource;

import at.tuwien.foop.labyrinth.ContextHolder;
import at.tuwien.foop.labyrinth.event.DoorClickedEvent;
import at.tuwien.foop.labyrinth.event.EventBus;
import at.tuwien.foop.labyrinth.model.Door;
 
public class LabyrinthController implements Observer {

	LabyrinthView watched;
	EventBus bus;
	

	@Resource(name="doorList")
	private List<Door> doors = (List<Door>) ContextHolder.getContext().getBean("doorList");


	public void control(){
		bus = ContextHolder.getContext().getBean(EventBus.class);
		
		watched = new LabyrinthView();
		watched.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		for(Door d : doors){
			if(d.getDoorStatus()==1){
				d.setDoorStatus(2); //DOOR_CLOSED
			} else {
				d.setDoorStatus(1); //DOOR_OPENED
			}
		}
		System.out.println("Observer Update:  " + watched.getClickedButtonID());
		bus.fireEvent(new DoorClickedEvent(watched.getClickedButtonID(), doors.get(watched.getClickedButtonID()).getDoorStatus()));
	}

}



