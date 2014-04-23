package at.tuwien.foop.labyrinth.event;

import org.springframework.stereotype.Component;

import at.tuwien.foop.labyrinth.model.Door;
import at.tuwien.foop.labyrinth.model.Mouse;

@Component
public class EventFactory {

	/**
	 * 
	 * @param mouse Mouse which moves
	 * @param moveDirection Direction of the movement (eg: Mouse.DIRECTION_UP)
	 * @return
	 */
	public MouseMoveEvent createMouseMoveEvent(Mouse mouse, int moveDirection) {
		MouseMoveEvent event = new MouseMoveEvent();
		event.setOld_direction(mouse.getMouseDirection());
		event.setOld_x(mouse.getX());
		event.setOld_y(mouse.getY());
		
		int newX, newY;
		newX = mouse.getX();
		newY = mouse.getY();
		
		switch(moveDirection) {
		case Mouse.DIRECTION_UP:
			newY--;
			break;
		case Mouse.DIRECTION_RIGHT:
			newX++;
			break;
		case Mouse.DIRECTION_LEFT:
			newX--;
			break;
		case Mouse.DIRECTION_DOWN:
			newY--;
			break;
		}
		
		event.setNew_direction(moveDirection);
		event.setNew_x(newX);
		event.setNew_y(newY);
		
		return event;
	}
	
	/**
	 * 
	 * @param door Door which has been clicked on
	 * @return
	 */
	public DoorClickedEvent createDoorClickedEvent(Door door) {
		//TODO: Check if DoorStatus can be changed (check lastChange or something like that)
		int newDoorStatus = (door.getDoorStatus() == Door.DOOR_CLOSED) ? Door.DOOR_OPEN : Door.DOOR_CLOSED;
		
		DoorClickedEvent event = new DoorClickedEvent(door.getId(), newDoorStatus);
		return event;
	}
}
