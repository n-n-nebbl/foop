package at.tuwien.foop.labyrinth.event;

public class DoorClickedEventHandler implements EventHandler<DoorClickedEvent>{

	@Override
	public void eventFired(DoorClickedEvent event) {
		System.out.println("DoorEvent: ID(" + event.getDoorId() + "), Status(" + event.getDoorStatus()+")");
	}

}
