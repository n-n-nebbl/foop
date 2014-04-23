package at.tuwien.foop.labyrinth.event;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import at.tuwien.foop.labyrinth.model.Door;

@Component
public class DoorClickedEventHandler implements EventHandler<DoorClickedEvent>{

	@Resource(name="doorList")
	private List<Door> doors;
	
	@Override
	public void eventFired(DoorClickedEvent event) {
		System.out.println("DoorEvent: ID(" + event.getDoorId() + "), Status(" + event.getDoorStatus()+")");
	}
	
	public void setDoors(List<Door> doors) {
		this.doors = doors;
	}

	@Override
	public Class<DoorClickedEvent> getEventClass() {
		return DoorClickedEvent.class;
	}
}
