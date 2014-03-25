package at.tuwien.foop.labyrinth.event;

public class MouseMoveEventHandler implements EventHandler<MouseMoveEvent>{

	@Override
	public void eventFired(MouseMoveEvent event) {
		System.out.println("MouseEvent!");
	}

}
