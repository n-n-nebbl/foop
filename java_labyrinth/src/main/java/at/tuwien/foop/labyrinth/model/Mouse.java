package at.tuwien.foop.labyrinth.model;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Mouse extends Entity {

	public static final int DIRECTION_UP = 0;
	public static final int DIRECTION_RIGHT = 1;
	public static final int DIRECTION_DOWN = 2;
	public static final int DIRECTION_LEFT = 3;
	
	private int id;
	private int mouseDirection;
	private int x;
	private int y;
	
	public Mouse(int x, int y) {
		super('>');
		this.id = SequenceGenerator.getNextId("Mouse");
		this.x = x;
		this.y = y;
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

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
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
