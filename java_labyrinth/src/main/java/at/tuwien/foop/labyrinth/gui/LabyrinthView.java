package at.tuwien.foop.labyrinth.gui;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Observable;

import javax.annotation.Resource;
import javax.swing.*;

import org.springframework.stereotype.Component;

import at.tuwien.foop.labyrinth.StartLabyrinth;
import at.tuwien.foop.labyrinth.model.Door;
import at.tuwien.foop.labyrinth.model.Map;

@Component
public class LabyrinthView extends Observable {

	private int clickedButtonID;

	@Resource(name="doorList")
	private List<Door> doorList;
	
	@Resource
	private LabyrinthComponent labyrinthComponent;

	private Map labyrinth;
	
	public LabyrinthView() {
	}
	
	public void startLabyrinth(Map labyrinth)
	{		
		this.labyrinth = labyrinth;
		
		JFrame f = new JFrame();
		f.setTitle("Mouse Labyrinth");
		f.add(labyrinthComponent);
		f.setSize(20 * labyrinth.getWidth(), 20 * labyrinth.getHeight() + 20);
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		f.setLayout(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		f.addWindowListener(new ExitWindowAdapter());
	}

	public int getClickedButtonID(){
		return this.clickedButtonID;
	}

	public void setClickedButtonID(int clickedButtonID){
		this.clickedButtonID = clickedButtonID;
		setChanged();
		notifyObservers();
	}
	
	public void repaintAll()
	{
		labyrinthComponent.repaint();
	}

	public Map getLabyrinth()
	{
		return this.labyrinth;
	}

	class ExitWindowAdapter extends WindowAdapter
	{
		// Close the labyrinth and exit
		public void windowClosing(WindowEvent e) {
			
			StartLabyrinth.onExit();
			System.exit(0);
		}
	}

}


