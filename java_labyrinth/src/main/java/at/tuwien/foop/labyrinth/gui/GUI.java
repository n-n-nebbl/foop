package at.tuwien.foop.labyrinth.gui;

import java.util.List;

import javax.annotation.Resource;
import javax.swing.JFrame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.tuwien.foop.labyrinth.event.EventFactory;
import at.tuwien.foop.labyrinth.model.Door;
import at.tuwien.foop.labyrinth.model.Mouse;


public class GUI {
	
	public GUI(){
		JFrame f = new JFrame();
		f.setTitle("Mouse Labyrinth");
		//f.add(new LabyrinthView());
		f.setSize(620, 640);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	

	/*
	 * Just example Code
	 */
	
	@Autowired
	private EventFactory eventFactory;

	@Resource(name="doorList")
	private List<Door> doorList;
	
	@Resource(name="mouseList")
	private List<Mouse> mouseList;
	
	
	
	
	public EventFactory getEventFactory() {
		return eventFactory;
	}

	public void setEventFactory(EventFactory eventFactory) {
		this.eventFactory = eventFactory;
	}

	public void setDoorList(List<Door> doorList) {
		this.doorList = doorList;
	}

	public void setMouseList(List<Mouse> mouseList) {
		this.mouseList = mouseList;
	}
}
