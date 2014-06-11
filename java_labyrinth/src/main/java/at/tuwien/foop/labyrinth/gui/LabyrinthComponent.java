package at.tuwien.foop.labyrinth.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.annotation.Resource;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.springframework.stereotype.Component;

import at.tuwien.foop.labyrinth.model.Door;
import at.tuwien.foop.labyrinth.model.Map;
import at.tuwien.foop.labyrinth.model.Mouse;
import at.tuwien.foop.labyrinth.model.Mouse.MouseColor;

@Component
public class LabyrinthComponent extends JPanel implements ActionListener {

	@Resource
	private LabyrinthView labyrinthView;
	
	@Resource
	private List<Door> doorList;
	
	@Resource
	private List<Mouse> mouseList;
	
	private ImageIcon closedDoorImg, openedDoorImg;

	
	public LabyrinthComponent(){

		closedDoorImg = new ImageIcon("./images/closed_door.png");  
		openedDoorImg = new ImageIcon("./images/opened_door.png");
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);

		Map map = this.labyrinthView.getLabyrinth();
		
		System.out.println("Repainting... " + map.getWidth() + "x" + map.getHeight() + " \nDoors: " + doorList.size());
				
		for(int y = 0; y < map.getHeight(); y++)
		{
			for(int x = 0; x < map.getWidth(); x++)
			{
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
				else if(map.getField(x, y).equals("D") && map.getField(x, y).getClass() == Door.class)
				{		
					Door d = (Door)map.getField(x, y);
				
					// Todo: don't create two times, get them from the right list(local doorlist not server map list which u only get one time -> updated through server events :))...
					final JButton doorButton = new JButton(closedDoorImg);
					doorButton.setName(""+(d).getId());
					doorButton.setBounds(x*20, y*20, 20, 20); 
					this.add(doorButton);

					doorButton.addActionListener(new ActionListener(){  
						public void actionPerformed(ActionEvent e) {  
							if(doorButton.getIcon()==closedDoorImg){
								doorButton.setIcon(openedDoorImg);
								labyrinthView.setClickedButtonID(Integer.parseInt(doorButton.getName()));
							} else {
								doorButton.setIcon(closedDoorImg);
								labyrinthView.setClickedButtonID(Integer.parseInt(doorButton.getName()));
							}
						}  
					}); 
				}
				}
			}
	
		for(Mouse m : map.getMouseList())
			g.drawImage(this.createMouseImage(m.getColor()), m.getX()*20, m.getY()*20,20,20, null);
	
	}

	public Image createMouseImage(MouseColor m)
	{
		ImageIcon i;
		
		switch(m)
		{
			case RED:
				i = new ImageIcon("./images/player_red.png");
			break;
			case GREEN:
				i = new ImageIcon("./images/player_green.png");
			break;
			case BLUE:
				i = new ImageIcon("./images/player_blue.png");
			break;
			case ORANGE:
				i = new ImageIcon("./images/player_orange.png");
			break;
			case BROWN:
				i = new ImageIcon("./images/player_brown.png");
			break;
				default:
					System.out.println("createMouseImage(): Error, color not found!");
					return null;
		}
		return i.getImage();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}
}
