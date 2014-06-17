package at.tuwien.foop.labyrinth.event;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import at.tuwien.foop.labyrinth.gui.LabyrinthController;
import at.tuwien.foop.labyrinth.model.Mouse;

@Component
public class MouseMoveEventHandler implements EventHandler<MouseMoveEvent>
{
	@Resource
	private LabyrinthController labyrinthController;

	@Resource(name = "mouseList")
	private List<Mouse> mouseList;

	@Override
	public void eventFired(MouseMoveEvent event)
	{
		System.out.println("MouseEvent!");
		Mouse m = findMouseWithId(event.getMouseID());
		if(m == null)
		{
			System.out.println("Mouse not found: id(" + event.getMouseID() + ")");
			return;
		}
		// else {
		// System.out.println("Mouse found: id(" + event.getMouseID());
		// }
		m.setState(event.getNewState());
		m.setOldState(event.getOldState());

		labyrinthController.gotMouseEvent(event, m);
	}

	private Mouse findMouseWithId(int id)
	{
		for(Mouse m : mouseList)
		{
			if(m.getId() == id)
			{
				return m;
			}
		}
		return null;
	}

	@Override
	public Class<MouseMoveEvent> getEventClass()
	{
		return MouseMoveEvent.class;
	}

	public void setMouseList(List<Mouse> mouseList)
	{
		this.mouseList = mouseList;
	}
}
