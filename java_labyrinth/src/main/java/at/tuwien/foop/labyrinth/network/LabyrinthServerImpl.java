package at.tuwien.foop.labyrinth.network;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.swing.Timer;

import at.tuwien.foop.labyrinth.event.DoorClickedEvent;
import at.tuwien.foop.labyrinth.event.GameEvent;
import at.tuwien.foop.labyrinth.event.GameEvent.GameEventType;
import at.tuwien.foop.labyrinth.event.MouseMoveEvent;
import at.tuwien.foop.labyrinth.gui.LabyrinthComponent;
import at.tuwien.foop.labyrinth.model.Door;
import at.tuwien.foop.labyrinth.model.Map;
import at.tuwien.foop.labyrinth.model.Mouse;
import at.tuwien.foop.labyrinth.model.Mouse.MouseDirection;
import at.tuwien.foop.labyrinth.model.MouseState;

public class LabyrinthServerImpl implements LabyrinthServer
{

	private class GameRoundTimer implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			try
			{
				for (Mouse mouse : getLabyrinth().getMouseList())
				{
					MouseState state = nextMove(mouse);

					mouse.setOldState(mouse.getState());
					mouse.setState(state);

					MouseMoveEvent event = new MouseMoveEvent(mouse);

					for (NetworkEventHandler handler : handlers)
					{
						handler.fireEvent(event);
					}

					if (getLabyrinth().getField(mouse.getX(), mouse.getY())
							.isGoal()) // Game ended
					{
						stopGame(mouse);
						return;
					}
				}
			} catch (RemoteException e1)
			{
				System.out
						.println("actionPerformed(): Error, calling the labyrinth");
				e1.printStackTrace();
			}
		}

		private MouseState nextMove(Mouse mouse) throws RemoteException
		{
			List<MouseDirection> directions = new ArrayList<MouseDirection>(
					Arrays.asList(MouseDirection.values()));
			java.util.Map<MouseDirection, MouseState> possibleMoves = Collections
					.synchronizedMap(new HashMap<MouseDirection, MouseState>());

			int random;
			synchronized (possibleMoves)
			{

				for (MouseDirection direction : directions)
				{
					MouseState move = new MouseState(mouse.getX(),
							mouse.getY(), direction);
					move.forward();

					if (getLabyrinth().getField(move.getX(), move.getY())
							.isFree())
					{
						possibleMoves.put(direction, move);
					}
				}
				if (mouse.getOldState() != null)
				{
					// MouseState newDirection =
					// possibleMoves.get(mouse.getState().getDirection());
					// // when old direction possible -> move on
					// if(newDirection != null)
					// {
					// System.out.println(1 + " old dir " +
					// mouse.getState().getDirection());
					// return newDirection;
					// }

					// when there is another opportunity -> remove move to old
					// position
					if (possibleMoves.size() > 1)
					{
						List<MouseState> possibleMovesList = Collections
								.synchronizedList(new ArrayList<MouseState>(
										possibleMoves.values()));
						synchronized (possibleMovesList)
						{
							for (MouseState state : possibleMovesList)
							{
								if (state.getX() == mouse.getOldState().getX()
										&& state.getY() == mouse.getOldState()
												.getY())
								{
									System.out.println(2);
									possibleMoves.remove(state.getDirection());
								}
							}
						}
					}
				}
				System.out.println(Arrays.toString(possibleMoves.values()
						.toArray()));
				random = new Random().nextInt(possibleMoves.size());
			}

			return new ArrayList<MouseState>(possibleMoves.values())
					.get(random);
		}
	}

	@Resource
	private LabyrinthComponent labyrinthComponent;

	private List<NetworkEventHandler> handlers;
	private Map labyrinth;
	private Timer timer;

	private boolean running = false;

	public LabyrinthServerImpl()
	{
		handlers = new ArrayList<>();

		this.labyrinth = new Map("./maps/map.txt");
	}

	@Override
	public void addNetworkEventHandler(NetworkEventHandler handler)
			throws RemoteException
	{

		if (gameIsRunning())
		{
			throw new RemoteException("Game is running!");
		}

		for (NetworkEventHandler h : handlers)
		{
			h.fireEvent(new GameEvent(GameEventType.INFORMATION,
					"Another player joined the game. We are "
							+ (handlers.size() + 1) + " players now."));
		}

		handlers.add(handler);
	}

	@Override
	public boolean gameIsRunning() throws RemoteException
	{
		return this.running;
	}

	@Override
	public Map getLabyrinth() throws RemoteException
	{
		return this.labyrinth;
	}

	// TODO: nur prüfen, nicht setzen
	@Override
	public void raiseDoorEvent(DoorClickedEvent event) throws RemoteException
	{
		if (!gameIsRunning())
		{
			return;
		}

		for (Door d : labyrinth.getDoorList())
		{
			if (d.getId() == event.getDoorId())
			{
				int status = (d.getDoorStatus() == Door.DOOR_CLOSED) ? Door.DOOR_OPEN
						: Door.DOOR_CLOSED;

				if (status != event.getDoorStatus())
				{
					return;
				}

				event.setDoorStatus(status);
				d.setDoorStatus(status);

				// Distribute to all people
				for (NetworkEventHandler handler : handlers)
				{
					handler.fireEvent(event);
				}
			}
		}
	}

	@Override
	public void removeNetworkEventHandler(NetworkEventHandler handler)
			throws RemoteException
	{
		System.out.println("removeNetworkEventHandler():"
				+ handlers.remove(handler));
	}

	public void setLabyrinth(Map labyrinth)
	{
		this.labyrinth = labyrinth;
	}

	// TODO: wenn das spiel bereits läuft -> ...
	@Override
	public void startGame()
	{
		if (this.running)
		{
			return;
		}

		this.running = true;
		timer = new Timer(100, new GameRoundTimer());
		timer.start();

		ArrayList<Mouse> mouseList = new ArrayList<Mouse>();

		// Adds all mouses
		for (int i = 0; i < handlers.size(); i++)
		{
			mouseList.add(labyrinth.addMouse());
		}

		// Sends an event to get the labyrinth
		for (int i = 0; i < handlers.size(); i++)
		{
			try
			{
				handlers.get(i).fireEvent(
						new GameEvent(GameEventType.GAMESTARTED,
								"Game started by a player. You got the "
										+ mouseList.get(i).getColor()
												.toString() + " mouse.",
								mouseList.get(i).getId()));
			} catch (RemoteException e)
			{
				System.out.println("startGame(): Error sending events, "
						+ e.toString());
				e.printStackTrace();
			}
		}
	}

	private void stopGame(Mouse mouseWon) throws RemoteException
	{
		this.timer.stop();
		this.running = false;

		for (NetworkEventHandler handler : handlers)
		{
			handler.fireEvent(new GameEvent(GameEvent.GameEventType.GAMEENDED,
					"Game ended, player <" + mouseWon.getColor() + "> won!",
					mouseWon.getId()));
		}
	}
}
