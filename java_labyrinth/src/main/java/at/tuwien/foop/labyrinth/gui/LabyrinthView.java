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
import org.springframework.stereotype.Component;

import at.tuwien.foop.labyrinth.ContextHolder;
import at.tuwien.foop.labyrinth.event.EventBus;
import at.tuwien.foop.labyrinth.event.EventFactory;
import at.tuwien.foop.labyrinth.model.Door;
import at.tuwien.foop.labyrinth.model.Map;
import at.tuwien.foop.labyrinth.model.Mouse;

public class LabyrinthView extends Observable {

	private ClassPathXmlApplicationContext ctx;
	private Map map; 
	private Mouse mouse_blue, mouse_green;	
	private ImageIcon closedDoorImg, openedDoorImg;
	private int clickedButtonID;
	private int mouseID;
	private int doorCounter;
	private Timer timer;

	@Resource(name="doorList")
	private List<Door> doorList = (List<Door>) ContextHolder.getContext().getBean("doorList");


	public LabyrinthView() {
		this.startLabyrinth();
	}

	public void startLabyrinth(){
		JFrame f = new JFrame();
		f.setTitle("Mouse Labyrinth");
		f.add(new LabyrinthComponent());
		f.setSize(620, 640);
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


	public class LabyrinthComponent extends JPanel implements ActionListener {

		public LabyrinthComponent(){
			timer = new Timer(500, new TimerListener());
			timer.start();
			map = new Map(31, "./maps/map.txt");

			mouse_blue = new Mouse(1, 1);
			mouse_green = new Mouse(29, 1);

			closedDoorImg = new ImageIcon("./images/closed_door.png");  
			openedDoorImg = new ImageIcon("./images/opened_door.png");
			doorCounter = 23;
		}

		public void paintComponent(Graphics g){
			super.paintComponent(g);

			for(int y=0; y<31; y++){
				for(int x=0; x<31; x++){
					if(map.getField(x, y).equals("W")){
						g.setColor(Color.black);
						g.fillRect(x*20, y*20, 20, 20);
					} 
					else if(map.getField(x, y).equals("P")){
						g.setColor(Color.white);
						g.fillRect(x*20, y*20, 20, 20);
					}  
					else if(map.getField(x, y).equals("G")){
						g.setColor(Color.yellow);
						g.fillRect(x*20, y*20, 20, 20);
					} 
					else if(map.getField(x, y).equals("D")){						
						if(doorCounter>0){
							final Door d = new Door();
							doorList.add(d);

							final JButton doorButton = new JButton(closedDoorImg);
							doorButton.setName(""+d.getId());
							doorButton.setBounds(x*20, y*20, 20, 20); 
							this.add(doorButton);
							doorCounter--;

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
			}
			g.drawImage(this.createImage("./images/player_blue.png"), mouse_blue.getX()*20, mouse_blue.getY()*20,20,20, null);
			g.drawImage(this.createImage("./images/player_green.png"), mouse_green.getX()*20, mouse_green.getY()*20,20,20, null);
		}

		public Image createImage(String path){
			ImageIcon i = new ImageIcon(path);
			return i.getImage();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			repaint();
		}
		
		/*
		 * Just example Code for a moving Mouse
		 */
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


