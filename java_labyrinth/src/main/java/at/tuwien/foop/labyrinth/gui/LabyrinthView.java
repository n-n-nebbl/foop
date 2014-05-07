package at.tuwien.foop.labyrinth.gui;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.annotation.Resource;
import javax.swing.Timer;
import javax.swing.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import at.tuwien.foop.labyrinth.event.EventFactory;
import at.tuwien.foop.labyrinth.model.Door;
import at.tuwien.foop.labyrinth.model.Labyrinth;
import at.tuwien.foop.labyrinth.model.Mouse;


public class LabyrinthView extends Observable {

	private ClassPathXmlApplicationContext ctx;
	private Labyrinth lab; 
	private Mouse mouse_blue, mouse_green;	
	private Timer timer;
	private ImageIcon closedDoorImg, openedDoorImg;
	private int clickedButtonID;
	private Door d;
	private JButton doorButton;

	@Resource(name="doorList")
	private List<Door> doors;

	public LabyrinthView() {
		this.startLabyrinth();
	}

	public void startLabyrinth(){
		JFrame f = new JFrame();
		f.setTitle("Mouse Labyrinth");
		f.add(new LabyrinthComponent());
		f.setSize(620, 640);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * InnerClass
	 */
	public class LabyrinthComponent extends JPanel implements ActionListener {

		public LabyrinthComponent(){

			ctx = new ClassPathXmlApplicationContext("application-context.xml");
			doors = (List<Door>) ctx.getBean("doorList");

			lab = new Labyrinth(31, "./maps/map.txt");
			mouse_blue = new Mouse(1, 1);
			mouse_green = new Mouse(29, 1);
			closedDoorImg = new ImageIcon("./images/closed_door.png");  
			openedDoorImg = new ImageIcon("./images/opened_door.png");
			doorButton = new JButton(closedDoorImg);
			clickedButtonID = 0;
			d = new Door();
			timer = new Timer(100, this);
			timer.setRepeats(true);
			timer.start();

		}
		
		public Image createImage(String path){
			ImageIcon i = new ImageIcon(path);
			return i.getImage();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			repaint();
		}

		/**
		 * creates the graphical components for the map and set it
		 */
		public void paintComponent(Graphics g){
			super.paintComponent(g);

			for(int y=0; y<31; y++){
				for(int x=0; x<31; x++){
					if(lab.getLabyrinth(x, y).equals("W")){
						g.setColor(Color.black);
						g.fillRect(x*20, y*20, 20, 20);
					} 
					else if(lab.getLabyrinth(x, y).equals("P")){
						g.setColor(Color.white);
						g.fillRect(x*20, y*20, 20, 20);
					}  
					else if(lab.getLabyrinth(x, y).equals("G")){
						g.setColor(Color.yellow);
						g.fillRect(x*20, y*20, 20, 20);
					} 
					else if(lab.getLabyrinth(x, y).equals("D")){	
						Door d = new Door();
						doors.add(d);
						final JButton doorButton = new JButton(closedDoorImg);
						doorButton.setName(""+d.getId());
						doorButton.setBounds(x*20, y*20, 20, 20); 
						this.add(doorButton);
						doorButton.addActionListener(new ActionListener(){  
							public void actionPerformed(ActionEvent e) {  
								if(doorButton.getIcon()==closedDoorImg){
									doorButton.setIcon(openedDoorImg);
									setClickedButtonID(Integer.parseInt(doorButton.getName()));
								} else {
									doorButton.setIcon(closedDoorImg);
									setClickedButtonID(Integer.parseInt(doorButton.getName()));
								}
							}  
						}); 
					}
				}
			}
			g.drawImage(this.createImage("./images/player_blue.png"), mouse_blue.getX()*20, mouse_blue.getY()*20,20,20, null);
			g.drawImage(this.createImage("./images/player_green.png"), mouse_green.getX()*20, mouse_green.getY()*20,20,20, null);

		}

	}

	public int getClickedButtonID(){
		return this.clickedButtonID;
	}

	public void setClickedButtonID(int clickedButtonID){
		this.clickedButtonID = clickedButtonID;
		setChanged();
		notifyObservers();
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






