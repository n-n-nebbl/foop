package at.tuwien.foop.labyrinth.event;

import at.tuwien.foop.labyrinth.model.Mouse;

public class MouseMoveEvent implements Event{

	private int old_x;
	private int old_y;
	private int old_direction;
	
	private int new_x;
	private int new_y;
	private int new_direction;
	
	public MouseMoveEvent() {
		this.old_x = 0;
		this.old_y = 0;
		this.old_direction = Mouse.DIRECTION_RIGHT;
	}

	public int getOld_x() {
		return old_x;
	}

	public void setOld_x(int old_x) {
		this.old_x = old_x;
	}

	public int getOld_y() {
		return old_y;
	}

	public void setOld_y(int old_y) {
		this.old_y = old_y;
	}

	public int getOld_direction() {
		return old_direction;
	}

	public void setOld_direction(int old_direction) {
		this.old_direction = old_direction;
	}

	public int getNew_x() {
		return new_x;
	}

	public void setNew_x(int new_x) {
		this.new_x = new_x;
	}

	public int getNew_y() {
		return new_y;
	}

	public void setNew_y(int new_y) {
		this.new_y = new_y;
	}

	public int getNew_direction() {
		return new_direction;
	}

	public void setNew_direction(int new_direction) {
		this.new_direction = new_direction;
	}
}
