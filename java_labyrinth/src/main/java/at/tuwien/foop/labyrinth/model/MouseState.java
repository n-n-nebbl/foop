package at.tuwien.foop.labyrinth.model;

import java.io.Serializable;

import at.tuwien.foop.labyrinth.model.Mouse.MouseDirection;

public class MouseState implements Serializable
{
	private static final long serialVersionUID = 8858297925796196801L;

	private int x;
	private int y;
	private boolean sniffing = false;
	private MouseDirection direction;

	public MouseState(int x, int y, MouseDirection direction, boolean sniffing)
	{
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.sniffing = sniffing;
	}

	public MouseState(Mouse mouse)
	{
		this.x = mouse.getState().getX();
		this.y = mouse.getState().getY();
		this.direction = mouse.getState().getDirection();
	}

	public void forward()
	{
		switch (direction)
		{
		case UP:
			y--;
			break;
		case RIGHT:
			x++;
			break;
		case LEFT:
			x--;
			break;
		case DOWN:
			y++;
			break;
		}
	}

	public boolean getSniffing()
	{
		return this.sniffing;
	}

	public MouseDirection getDirection()
	{
		return direction;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public void setDirection(MouseDirection direction)
	{
		this.direction = direction;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	@Override
	public String toString()
	{
		return x + " " + y + " " + direction;
	}

}
