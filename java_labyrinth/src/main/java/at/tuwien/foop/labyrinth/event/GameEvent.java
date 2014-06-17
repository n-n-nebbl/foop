package at.tuwien.foop.labyrinth.event;

public class GameEvent implements Event
{
	public enum GameEventType
	{
		INFORMATION,
		GAMESTARTED,
		GAMEENDED
	}

	private static final long serialVersionUID = -4830989760860134179L;

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

	public GameEventType getType()
	{
		return this.type;
	}

	public int getValue()
	{
		return this.value;
	}
}
