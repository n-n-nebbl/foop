package at.tuwien.foop.labyrinth.event;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import at.tuwien.foop.labyrinth.gui.LabyrinthController;
import at.tuwien.foop.labyrinth.model.Mouse;

@Component
public class GameEventHandler implements EventHandler<GameEvent> {

	@Resource
	private LabyrinthController labyrinthController;	
	
	@Override
	public void eventFired(GameEvent event) {
		labyrinthController.gotGameEvent(event);
	}

	@Override
	public Class<GameEvent> getEventClass() {
		return GameEvent.class;
	}
}
