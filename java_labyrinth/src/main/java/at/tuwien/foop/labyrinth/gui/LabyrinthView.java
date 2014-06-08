package at.tuwien.foop.labyrinth.gui;
import java.util.*;
import java.util.List;

import javax.annotation.Resource;
import javax.swing.*;

import org.springframework.stereotype.Component;

import at.tuwien.foop.labyrinth.model.Door;

@Component
public class LabyrinthView extends Observable {

	private int clickedButtonID;

	@Resource(name="doorList")
	private List<Door> doorList;
	
	@Resource
	private LabyrinthComponent labyrinthComponent;

	public LabyrinthView() {
		//this.startLabyrinth();
	}

	public void startLabyrinth(){
		JFrame f = new JFrame();
		f.setTitle("Mouse Labyrinth");
		f.add(labyrinthComponent);
		f.setSize(620, 640);
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public int getClickedButtonID(){
		return this.clickedButtonID;
	}

	public void setClickedButtonID(int clickedButtonID){
		this.clickedButtonID = clickedButtonID;
		setChanged();
		notifyObservers();
	}

}


