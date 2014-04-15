package at.tuwien.foop.labyrinth.model;

public class Mouse extends Entity {

	public static final int DIRECTION_UP = 0;
	public static final int DIRECTION_RIGHT = 1;
	public static final int DIRECTION_DOWN = 2;
	public static final int DIRECTION_LEFT = 3;
	
	private int id;
	private int mouseDirection;
	
	public Mouse() {
		super('>');
		this.id = SequenceGenerator.getNextId("Mouse");
		this.mouseDirection = DIRECTION_RIGHT;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getMouseDirection() {
		return mouseDirection;
	}

	public void setMouseDirection(int mouseDirection) {
		this.mouseDirection = mouseDirection;
		refreshCharacter();
	}

	private void refreshCharacter() {
		switch(mouseDirection) {
		case DIRECTION_UP:
			setCharacter('^');
			break;
		case DIRECTION_RIGHT:
			setCharacter('>');
			break;
		case DIRECTION_DOWN:
			setCharacter('v');
			break;
		case DIRECTION_LEFT:
			setCharacter('<');
			break;
		}
	}
}
