package at.tuwien.foop.labyrinth.network;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.swing.Timer;

import at.tuwien.foop.labyrinth.event.DoorClickedEvent;
import at.tuwien.foop.labyrinth.event.DoorClickedEventHandler;
import at.tuwien.foop.labyrinth.event.Event;
import at.tuwien.foop.labyrinth.event.GameEvent;
import at.tuwien.foop.labyrinth.event.GameEvent.GameEventType;
import at.tuwien.foop.labyrinth.event.MouseMoveEvent;
import at.tuwien.foop.labyrinth.model.Door;
import at.tuwien.foop.labyrinth.model.Map;
import at.tuwien.foop.labyrinth.model.Mouse;

public class LabyrinthServerImpl implements LabyrinthServer {

	private List<NetworkEventHandler> handlers;
	private Map labyrinth;
	private Timer timer;
	private boolean running = false;
	
	public LabyrinthServerImpl() {
		handlers = new ArrayList<>();
		
		this.labyrinth = new Map("./maps/map.txt");
	}

	@Override
	public void addNetworkEventHandler(NetworkEventHandler handler)
			throws RemoteException {
		
		if(gameIsRunning())
			throw new RemoteException("Game is running!");
		

		for (NetworkEventHandler h : handlers) 
		{
			h.fireEvent(new GameEvent(GameEventType.INFORMATION, "Another player joined the game. We are " + (handlers.size() +1) + " players now."));
		}
		
		handlers.add(handler);
	}

	@Override
	public void removeNetworkEventHandler(NetworkEventHandler handler)
			throws RemoteException {
		System.out.println("removeNetworkEventHandler():" + handlers.remove(handler));
	}

	@Override
	public boolean gameIsRunning() throws RemoteException 
	{
		return this.running;
	}
	
	// TODO: wenn das spiel bereits läuft -> ...
	public void startGame()
	{
		if(this.running)
			return;
		
		this.running = true;
		timer = new Timer(1000, new GameRoundTimer());
		timer.start();
		
		ArrayList<Mouse> mouseList = new ArrayList<Mouse>();
		
		// Adds all mouses
		for(int i = 0; i < handlers.size(); i++) 
			mouseList.add(labyrinth.addMouse());		
				
		// Sends an event to get the labyrinth
		for (int i = 0; i < handlers.size(); i++) 
		{
			try {
				handlers.get(i).fireEvent(new GameEvent(GameEventType.GAMESTARTED, "Game started by a player. You got the " + mouseList.get(i).getColor().toString() + " mouse.", mouseList.get(i).getId()));
			} catch (RemoteException e) {
				System.out.println("startGame(): Error sending events, " + e.toString());
				e.printStackTrace();
			}
		}
	}
	
	// TODO: nur prüfen, nicht setzen
	@Override
	public void raiseDoorEvent(DoorClickedEvent event) throws RemoteException 
	{
		if(!gameIsRunning())
			return;
		
		for(Door d : labyrinth.getDoorList())
		{
			if(d.getId() == event.getDoorId())
			{
				int status = (d.getDoorStatus() == Door.DOOR_CLOSED) ? Door.DOOR_OPEN : Door.DOOR_CLOSED;
				
				if(status != event.getDoorStatus()) // Drop this
					return;
				
				event.setDoorStatus(status);
				d.setDoorStatus(status);
				
				//System.out.println("distributeEvent(): " + event.toString());
				//System.out.println("distributeEvent(): to " + handlers.size() + " handlers.");
					
				// Distribute to all people
				for (NetworkEventHandler handler : handlers) 
				{			
					handler.fireEvent(event);
				}
			}
		}
	}
		

	private class GameRoundTimer implements ActionListener
	{		
		public void actionPerformed(ActionEvent e)
		{	
			try 
			{
				for(Mouse mouse : getLabyrinth().getMouseList())
				{
					int moveDirection = Mouse.DIRECTION_DOWN; // Todo: spielelogik -> was tun?
					
					MouseMoveEvent event = new MouseMoveEvent();
					event.setMouseID(mouse.getId());
					event.setOld_direction(mouse.getMouseDirection());
					event.setOld_x(mouse.getX());
					event.setOld_y(mouse.getY());
					
					int newX, newY;
					newX = mouse.getX();
					newY = mouse.getY();
					
					switch(moveDirection) 
					{
						case Mouse.DIRECTION_UP:
							newY--;
							break;
						case Mouse.DIRECTION_RIGHT:
							newX++;
							break;
						case Mouse.DIRECTION_LEFT:
							newX--;
							break;
						case Mouse.DIRECTION_DOWN:
							newY++;
							break;
					}
					
					event.setNew_direction(moveDirection);
					event.setNew_x(newX);
					event.setNew_y(newY);
					
					mouse.setX(newX);
					mouse.setY(newY);
					mouse.setMouseDirection(moveDirection);
					
					for (NetworkEventHandler handler : handlers) 
					{			
						handler.fireEvent(event);
					}
				}
			} catch (RemoteException e1) {
				System.out.println("actionPerformed(): Error, calling the labyrinth");
				e1.printStackTrace();
			}
		}
	
	}
	
	@Override
	public Map getLabyrinth() throws RemoteException {
		return this.labyrinth;
	}

	public void setLabyrinth(Map labyrinth) {
		this.labyrinth = labyrinth;
	}
}
