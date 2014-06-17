package at.tuwien.foop.labyrinth.model;

import java.io.Serializable;

public abstract class Entity implements Serializable
{

	private static final long serialVersionUID = 4724077628496390543L;

	protected char c;

	public Entity(char c)
	{
		this.c = c;
	}

	public char getCharacter()
	{
		return c;
	}

	public boolean isFree()
	{
		return isPath() || isGoal() || isOpenDoor();
	}

	public boolean isGoal()
	{
		return this.getClass() == Goal.class;
	}

	private boolean isOpenDoor()
	{
		return (this.getClass() == Door.class ? ((Door)this).isOpen() : false);
	}

	public boolean isPath()
	{
		return this.getClass() == Path.class;
	}

	public boolean isWall()
	{
		return this.getClass() == Wall.class;
	}

	public void setCharacter(char c)
	{
		this.c = c;
	}

	@Override
	public String toString()
	{
		return "" + c;
	}

}
