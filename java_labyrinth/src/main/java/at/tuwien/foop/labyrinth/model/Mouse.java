package at.tuwien.foop.labyrinth.model;

public class Mouse extends Entity
{

	public enum MouseColor
	{
		RED(2), GREEN(1), BLUE(0), ORANGE(3), BROWN(4); // Must be inc +1
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

	public enum MouseDirection
	{
		UP(0), RIGHT(1), DOWN(2), LEFT(3);

		@SuppressWarnings("unused")
		private int code;

		private MouseDirection(int c)
		{
			this.code = c;
		}

		public MouseDirection getNext()
		{
			switch (this)
			{
			case UP:
				return RIGHT;
			case RIGHT:
				return DOWN;
			case DOWN:
				return LEFT;
			case LEFT:
				return UP;

			}
			return UP;
		}

	}

	private static final long serialVersionUID = -4624485550774716174L;
	private static int lastColor = 1;
	private int id;
	private int timesToSniff = -1;
	private Mouse sniffPartner = null;
	private MouseState state;
	private MouseState oldState;
	private MouseColor mouseColor;

	public Mouse(int x, int y)
	{
		super('>');

		lastColor = (lastColor + 1) % (MouseColor.maxColor); // TODO: fix order
		this.mouseColor = MouseColor.values()[lastColor];
		this.id = SequenceGenerator.getNextId("Mouse");
		this.state = new MouseState(x, y, MouseDirection.RIGHT);
	}

	public MouseColor getColor()
	{
		return this.mouseColor;
	}

	public int getTimesToSniff()
	{
		return this.timesToSniff;
	}

	public void setTimesToSniff(int value)
	{
		this.timesToSniff = value;
	}

	public void setSniffPartner(Mouse sniffPartner)
	{
		this.sniffPartner = sniffPartner;
	}

	public Mouse getSniffPartner()
	{
		return this.sniffPartner;
	}

	public int getId()
	{
		return id;
	}

	public MouseColor getMouseColor()
	{
		return mouseColor;
	}

	public MouseState getOldState()
	{
		return oldState;
	}

	public MouseState getState()
	{
		return state;
	}

	public int getX()
	{
		return state.getX();
	}

	public int getY()
	{
		return state.getY();
	}

	private void refreshCharacter()
	{
		switch (state.getDirection())
		{
		case UP:
			setCharacter('^');
			break;
		case RIGHT:
			setCharacter('>');
			break;
		case DOWN:
			setCharacter('v');
			break;
		case LEFT:
			setCharacter('<');
			break;
		}
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public void setMouseColor(MouseColor mouseColor)
	{
		this.mouseColor = mouseColor;
	}

	public void setOldState(MouseState oldState)
	{
		this.oldState = oldState;
	}

	public void setState(MouseState state)
	{
		this.state = state;
	}

}
