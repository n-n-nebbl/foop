package at.tuwien.foop.labyrinth.gui;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;

import javax.swing.Timer;
import javax.swing.*;

import at.tuwien.foop.labyrinth.model.Labyrinth;
import at.tuwien.foop.labyrinth.model.Mouse;


public class LabyrinthView extends JPanel implements ActionListener, ImageObserver{

	private Labyrinth f; 
	private Mouse mouse_blue, mouse_green;
	
	private boolean doorClosed = true;

	private Timer timer;
	//private Timer timer2 = new Timer(500, new TimerListener());

	public LabyrinthView() {
		
		f = new Labyrinth(31, "./maps/map.txt");
		mouse_blue = new Mouse(1, 1, "./images/player_blue.png");
		mouse_green = new Mouse(29, 1, "./images/player_green.png");

		addKeyListener(new Al());

		setFocusable(true);
		
		//for the KeyListener
		timer = new Timer(100, this);
		timer.setRepeats(true);
		timer.start();
		
		//		timer2.start();
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);

		for(int y=0; y<31; y++){
			for(int x=0; x<31; x++){
				if(f.getLabyrinth(x, y).equals("W")){
					g.setColor(Color.black);
					g.fillRect(x*20, y*20, 20, 20);
				} 
				else if(f.getLabyrinth(x, y).equals("P")){
					g.setColor(Color.white);
					g.fillRect(x*20, y*20, 20, 20);
				}  
				else if(f.getLabyrinth(x, y).equals("G")){
					g.setColor(Color.yellow);
					g.fillRect(x*20, y*20, 20, 20);
				} 
				else if(f.getLabyrinth(x, y).equals("D")){
					final Icon closedDoorImg = new ImageIcon("./images/closed_door.png");  
					final Icon openedDoorImg = new ImageIcon("./images/opened_door.png");
				
					final JButton doorButton = new JButton(closedDoorImg);
					doorButton.setBounds(x*20, y*20, 20, 20); 
					this.add(doorButton);
					
					doorButton.addActionListener(new ActionListener(){  
						public void actionPerformed(ActionEvent e) {  
							doorButton.setIcon(doorButton.getIcon() == closedDoorImg ? openedDoorImg : closedDoorImg);
						}  
					}); 

				}
			}
		}
		g.drawImage(mouse_blue.getImg(), mouse_blue.getX()*20, mouse_blue.getY()*20,20,20, null);
		g.drawImage(mouse_green.getImg(), mouse_green.getX()*20, mouse_green.getY()*20,20,20, null);
		repaint();
	}
	

	public class Al extends KeyAdapter{

		public void keyPressed(KeyEvent e){
			int keycode = e.getKeyCode();

//			if(keycode == KeyEvent.VK_W){
//				if(f.getLabyrinth(mouse_blue.getX(), mouse_blue.getY()-1).equals("P"))
//					mouse_blue.moves(0, -1);
//			}
//			if(keycode == KeyEvent.VK_A){
//				if(f.getLabyrinth(mouse_blue.getX()-1, mouse_blue.getY()).equals("P"))
//					mouse_blue.moves(-1, 0);
//			}
//			if(keycode == KeyEvent.VK_S){
//				if(f.getLabyrinth(mouse_blue.getX(), mouse_blue.getY()+1).equals("P"))
//					mouse_blue.moves(0, 1);
//			}
//			if(keycode == KeyEvent.VK_D){
//				if(f.getLabyrinth(mouse_blue.getX()+1, mouse_blue.getY()).equals("P"))
//					mouse_blue.moves(1, 0);
//			}
//			System.out.println("Mouse Position: " + mouse_blue.getX() + " " + mouse_blue.getY());
//
//			if(mouse_blue.getX()==11 && mouse_blue.getY()==27)
//				System.out.println("FOUND CHEESEEEEEE!");

		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}

//	public void findCheese(){
//		while(mouse_blue.getX()!=11 && mouse_blue.getY()!=27){
//			System.out.println("FOUND CHEESEEEEEE!");
//		}
//	}

	//	private class TimerListener implements ActionListener
	//	{
	//		public void actionPerformed(ActionEvent e)
	//		{
	//			
	//			if(mouse.getTileX()!=11 && mouse.getTileY()!=27){
	//				if(!m.getMap(mouse.getTileX()+1, mouse.getTileY()).equals("W") && !visitedPath.contains(new Path(mouse.getTileX()+1, mouse.getTileY()))){
	//					mouse.moves(1, 0);
	//					visitedPath.add(new Path(mouse.getTileX(), mouse.getTileY()));
	//					System.out.println("MOUSE " + mouse.getTileX() + " " + mouse.getTileY());
	//				}
	//				else if(!m.getMap(mouse.getTileX(), mouse.getTileY()+1).equals("W") && !visitedPath.contains(new Path(mouse.getTileX(), mouse.getTileY()+1))){
	//					mouse.moves(0, 1);
	//					visitedPath.add(new Path(mouse.getTileX(), mouse.getTileY()));
	//					System.out.println("MOUSE " + mouse.getTileX() + " " + mouse.getTileY());
	//				}
	//				else if(!m.getMap(mouse.getTileX()-1, mouse.getTileY()).equals("W") && !visitedPath.contains(new Path(mouse.getTileX()-1, mouse.getTileY()))){
	//					mouse.moves(-1, 0);
	//					visitedPath.add(new Path(mouse.getTileX(), mouse.getTileY()));
	//					System.out.println("MOUSE " + mouse.getTileX() + " " + mouse.getTileY());
	//				}
	//			}
	//			
	//			System.out.println(visitedPath.size());
	//		
	//			repaint();
	//
	//
	//		}
}





