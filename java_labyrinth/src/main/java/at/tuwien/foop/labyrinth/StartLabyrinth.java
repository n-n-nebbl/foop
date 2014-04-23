package at.tuwien.foop.labyrinth;

import java.rmi.RemoteException;

import org.springframework.beans.BeansException;

import at.tuwien.foop.labyrinth.event.DoorClickedEvent;
import at.tuwien.foop.labyrinth.event.EventBus;
import at.tuwien.foop.labyrinth.event.MouseMoveEvent;
import at.tuwien.foop.labyrinth.model.Door;

public class StartLabyrinth {

	public static void main(String[] args) throws BeansException,
			RemoteException {
		EventBus bus = ContextHolder.getContext().getBean(EventBus.class);

		bus.fireEvent(new MouseMoveEvent());
		bus.fireEvent(new DoorClickedEvent(1, Door.DOOR_OPEN));
		bus.fireEvent(new MouseMoveEvent());

		ContextHolder.getContext().close();
	}
}
