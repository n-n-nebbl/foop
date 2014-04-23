package at.tuwien.foop.labyrinth.event;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import at.tuwien.foop.labyrinth.model.Mouse;

@Component
public class MouseMoveEventHandler implements EventHandler<MouseMoveEvent>{

	@Resource(name="mouseList")
	private List<Mouse> mouseList;
	
	@Override
	public void eventFired(MouseMoveEvent event) {
		System.out.println("MouseEvent!");
	}

	public void setMouseList(List<Mouse> mouseList) {
		this.mouseList = mouseList;
	}

	@Override
	public Class<MouseMoveEvent> getEventClass() {
		return MouseMoveEvent.class;
	}
}
