package at.tuwien.foop.labyrinth.event;

import at.tuwien.foop.labyrinth.model.Mouse;

public class GameEvent implements Event
{
	public enum GameEventType
	{
		INFORMATION,
		GAMESTARTED,
		GAMEENDED
	}

	private GameEventType type;
	private String messageText;
	private int value;

	public GameEvent(GameEventType type, String text)
	{
		this.messageText = text;
		this.type = type;
	}

	public GameEvent(GameEventType type, String text, int value)
	{
		this.messageText = text;
		this.value = value;
		this.type = type;
	}

	public String getMessageText()
	{
		return this.messageText;
	}

	public int getValue()
	{
		return this.value;
	}

	public GameEventType getType()
	{
		return this.type;
	}
}
