package at.tuwien.foop.labyrinth.event;

public class DoorClickedEvent implements Event {

	private int doorId;
	private int doorStatus;
	
	/**
	 * @param doorId ID of the clicked door 
	 * @param doorStatus Status of the door after it has been clicked
	 */
	public DoorClickedEvent(int doorId, int doorStatus) {
		this.doorId = doorId;
		this.doorStatus = doorStatus;
	}

	public int getDoorId() {
		return doorId;
	}

	public void setDoorId(int doorId) {
		this.doorId = doorId;
	}

	public int getDoorStatus() {
		return doorStatus;
	}

	public void setDoorStatus(int doorStatus) {
		this.doorStatus = doorStatus;
	}
}
