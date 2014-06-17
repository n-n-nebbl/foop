package at.tuwien.foop.labyrinth.event;

import at.tuwien.foop.labyrinth.model.Mouse;
import at.tuwien.foop.labyrinth.model.MouseState;

public class MouseMoveEvent implements Event
{
	private static final long serialVersionUID = 6999511844096197163L;

	private int mouseID;

	private MouseState oldState;
	private MouseState newState;

	public MouseMoveEvent(Mouse mouse)
	{
		this.mouseID = mouse.getId();
		this.oldState = mouse.getOldState();
		this.newState = mouse.getState();
	}

	public int getMouseID()
	{
		return mouseID;
	}

	public MouseState getNewState()
	{
		return newState;
	}

	public MouseState getOldState()
	{
		return oldState;
	}

	public void setMouseID(int mouseID)
	{
		this.mouseID = mouseID;
	}

	public void setNewState(MouseState newState)
	{
		this.newState = newState;
	}

	public void setOldState(MouseState oldState)
	{
		this.oldState = oldState;
	}

	@Override
	public String toString()
	{
		return oldState.toString() + newState.toString();
	}

}
