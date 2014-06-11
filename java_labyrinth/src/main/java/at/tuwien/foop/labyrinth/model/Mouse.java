package at.tuwien.foop.labyrinth.model;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Mouse extends Entity {

	private static final long serialVersionUID = -4624485550774716174L;
	public static final int DIRECTION_UP = 0;
	public static final int DIRECTION_RIGHT = 1;
	public static final int DIRECTION_DOWN = 2;
	public static final int DIRECTION_LEFT = 3;
	private static int lastColor = 1;
	
	private int id;
	private int mouseDirection;
	private int x;
	private int y;
	private MouseColor mouseColor;
	
	public enum MouseColor 
	{
		RED(0), GREEN(1), BLUE(2), ORANGE(3), BROWN(4); // Must be inc +1
		public static int maxColor = 4;
		
		private int code;
		
		private MouseColor(int c) 
		{
			 code = c;
		}
		
		public int getCode() 
		{
		  return code;
		}
	}
		
	public Mouse(int x, int y) {
		super('>');
				
		lastColor = (lastColor + 1) % (MouseColor.maxColor);
		this.mouseColor = MouseColor.values()[lastColor];		
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
	
	public MouseColor getColor()
	{
		return this.mouseColor;
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
