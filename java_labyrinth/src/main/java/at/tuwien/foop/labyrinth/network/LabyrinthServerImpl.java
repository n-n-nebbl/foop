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
import at.tuwien.foop.labyrinth.event.MouseMoveEvent;
import at.tuwien.foop.labyrinth.model.Door;
import at.tuwien.foop.labyrinth.model.Map;
import at.tuwien.foop.labyrinth.model.Mouse;

public class LabyrinthServerImpl implements LabyrinthServer {

	private List<NetworkEventHandler> handlers;
	private Map labyrinth;
	
	public LabyrinthServerImpl() {
		handlers = new ArrayList<>();
		
		this.labyrinth = new Map("./maps/map.txt");
	}

	@Override
	public void addNetworkEventHandler(NetworkEventHandler handler)
			throws RemoteException {
		handlers.add(handler);
	}

	@Override
	public void removeNetworkEventHandler(NetworkEventHandler handler)
			throws RemoteException {
		System.out.println("removeNetworkEventHandler():" + handlers.remove(handler));
	}
	
	@Override
	public void raiseDoorEvent(DoorClickedEvent event) throws RemoteException 
	{
			
			//event.setDoorStatus(door.getDoorStatus() == Door.DOOR_CLOSED) ? Door.DOOR_OPEN : Door.DOOR_CLOSED);
			
			System.out.println("distributeEvent(): " + event.toString());
			System.out.println("distributeEvent(): to " + handlers.size() + " handlers.");
							
						
			
			// Distribute to all people
			for (NetworkEventHandler handler : handlers) 
			{			
				handler.fireEvent(event);
			}
		/*
			MouseMoveEvent createMouseMoveEvent(Mouse mouse, int moveDirection, int newX, int newY) {
			MouseMoveEvent event = new MouseMoveEvent();
			event.setOld_direction(mouse.getMouseDirection());
			event.setOld_x(mouse.getX());
			event.setOld_y(mouse.getY());
			
			//int newX, newY;
			newX = mouse.getX();
			newY = mouse.getY();
			
			switch(moveDirection) {
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
			
			return event;
		}
		 */
	}
	
	/*
	 * 
	 * 

	private Timer timer;
	timer = new Timer(500, new TimerListener());
	timer.start();
		private class TimerListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			//mouse move on the path "P"
			if(map.getField(mouse_blue.getX()+1, mouse_blue.getY()).equals("P")){
				mouse_blue.setX(mouse_blue.getX()+1); //go right
			} else if (map.getField(mouse_blue.getX(), mouse_blue.getY()+1).equals("P")){
				mouse_blue.setY(mouse_blue.getY()+1); //go down
			} 
			repaint();
		}

	}
	 */
	

	@Override
	public Map getLabyrinth() throws RemoteException {
		return this.labyrinth;
	}

	public void setLabyrinth(Map labyrinth) {
		this.labyrinth = labyrinth;
	}
}
