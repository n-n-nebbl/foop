package at.tuwien.foop.labyrinth.gui;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.tuwien.foop.labyrinth.event.EventFactory;
import at.tuwien.foop.labyrinth.model.Door;
import at.tuwien.foop.labyrinth.model.Mouse;

@Component
public class GUI {

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
