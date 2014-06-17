package at.tuwien.foop.labyrinth.model;

import java.awt.Image;
import java.util.Date;

public class Door extends Entity
{

	private static final long serialVersionUID = -3570584812440078263L;
	public static final int DOOR_OPEN = 1;
	public static final int DOOR_CLOSED = 2;

	public static final int DOOR_HORIZONTAL = 3;
	public static final int DOOR_VERTICAL = 4;

	private int id;
	private int doorStatus;
	private int doorDirection;
	private Image img;

	private long lastTimeChanged;

	public Door()
	{
		super(' ');
		this.id = SequenceGenerator.getNextId("Door");
		this.doorStatus = DOOR_CLOSED;
		this.doorDirection = DOOR_HORIZONTAL;
		this.lastTimeChanged = 0;
	}

	public int getDoorDirection()
	{
		return doorDirection;
	}

	public int getDoorStatus()
	{
		return doorStatus;
	}

	public int getId()
	{
		return id;
	}

	public Image getImg()
	{
		return img;
	}

	public long getLastTimeChanged()
	{
		return lastTimeChanged;
	}

	public boolean isOpen()
	{
		return doorStatus == DOOR_OPEN;
	}

	private void refreshCharacter()
	{
		if(this.doorStatus == DOOR_OPEN)
		{
			setCharacter('/');
		}
		else
		{
			if(this.doorDirection == DOOR_HORIZONTAL)
			{
				setCharacter('-');
			}
			else
			{
				setCharacter('|');
			}
		}
	}

	public void setDoorDirection(int doorDirection)
	{
		this.doorDirection = doorDirection;
		refreshCharacter();
	}

	public void setDoorStatus(int doorStatus)
	{
		this.doorStatus = doorStatus;
		refreshCharacter();
	}

	public void setLastTimeChanged(long lastTimeChanged)
	{
		this.lastTimeChanged = lastTimeChanged;
	}

	/**
	 * @return the time between now and the last change to the door
	 */
	public long timeSinceLastChange()
	{
		return new Date().getTime() - lastTimeChanged;
	}

	/**
	 * @param duration
	 *            duration in ms
	 * @return true if duration is greater than or equals the time the last change to the door has happend
	 */
	public boolean timeSinceLastChangeGE(long duration)
	{
		return timeSinceLastChange() >= duration;
	}
}
