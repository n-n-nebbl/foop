package at.tuwien.foop.labyrinth.model;

import java.awt.Image;
import java.util.Date;

import javax.swing.ImageIcon;

public class Door extends Entity {

	public static final int DOOR_OPEN = 1;
	public static final int DOOR_CLOSED = 2;

	public static final int DOOR_HORIZONTAL = 3;
	public static final int DOOR_VERTICAL = 4;

	private int id;
	private int doorStatus;
	private int doorDirection;
	private Image img;

	private long lastTimeChanged;

	public Door() {
		super(' ');
		this.id = SequenceGenerator.getNextId("Door");
		this.doorStatus = DOOR_OPEN;
		this.doorDirection = DOOR_HORIZONTAL;
		this.lastTimeChanged = 0;
	}
	
	public Door(String path) {
		super(' ');
		this.id = SequenceGenerator.getNextId("Door");
		this.doorStatus = DOOR_OPEN;
		this.doorDirection = DOOR_HORIZONTAL;
		this.lastTimeChanged = 0;
		ImageIcon i = new ImageIcon(path);
		this.img = i.getImage();
	}
	

	public int getId() {
		return id;
	}

	public int getDoorStatus() {
		return doorStatus;
	}

	public void setDoorStatus(int doorStatus) {
		this.doorStatus = doorStatus;
		refreshCharacter();
	}

	public int getDoorDirection() {
		return doorDirection;
	}

	public void setDoorDirection(int doorDirection) {
		this.doorDirection = doorDirection;
		refreshCharacter();
	}

	public long getLastTimeChanged() {
		return lastTimeChanged;
	}

	public void setLastTimeChanged(long lastTimeChanged) {
		this.lastTimeChanged = lastTimeChanged;
	}
	
	public Image getImg() {
		return img;
	}


	/**
	 * @return the time between now and the last change to the door
	 */
	public long timeSinceLastChange() {
		return new Date().getTime() - lastTimeChanged;
	}

	/**
	 * @param duration
	 *            duration in ms
	 * @return true if duration is greater than or equals the time the last
	 *         change to the door has happend
	 */
	public boolean timeSinceLastChangeGE(long duration) {
		return timeSinceLastChange() >= duration;
	}

	private void refreshCharacter() {
		if (this.doorStatus == DOOR_OPEN) {
			setCharacter('/');
		} else {
			if (this.doorDirection == DOOR_HORIZONTAL) {
				setCharacter('-');
			} else {
				setCharacter('|');
			}
		}
	}
}
