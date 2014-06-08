package at.tuwien.foop.labyrinth.event;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import at.tuwien.foop.labyrinth.model.Door;

@Component
public class DoorClickedEventHandler implements EventHandler<DoorClickedEvent> {

	@Resource(name = "doorList")
	private List<Door> doorList;

	@Override
	public void eventFired(DoorClickedEvent event) {
		System.out.println("DoorEvent: ID(" + event.getDoorId() + "), Status("
				+ event.getDoorStatus() + ")");
		Door d = findDoorWithId(event.getDoorId());
		if (d == null) {
			System.out.println("Door not found: id(" + event.getDoorId() + ")");
			return;
		} else {
			System.out.println("Door found: id(" + event.getDoorId() + ")");
		}
		d.setDoorStatus(event.getDoorStatus());
	}

	public void setDoors(List<Door> doorList) {
		this.doorList = doorList;
	}

	@Override
	public Class<DoorClickedEvent> getEventClass() {
		return DoorClickedEvent.class;
	}

	private Door findDoorWithId(int id) {
		for (Door d : doorList) {
			if (d.getId() == id) {
				return d;
			}
		}
		return null;
	}
}
