package at.tuwien.foop.labyrinth.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.springframework.stereotype.Component;

import at.tuwien.foop.labyrinth.model.Door;
import at.tuwien.foop.labyrinth.model.Goal;
import at.tuwien.foop.labyrinth.model.Map;
import at.tuwien.foop.labyrinth.model.Mouse;
import at.tuwien.foop.labyrinth.model.Mouse.MouseColor;
import at.tuwien.foop.labyrinth.model.Path;
import at.tuwien.foop.labyrinth.model.Wall;

@Component
public class LabyrinthComponent extends JPanel implements ActionListener
{

	@Resource
	private LabyrinthView labyrinthView;

	@Resource
	private List<Door> doorList;

	@Resource
	private List<Mouse> mouseList;

	private ImageIcon closedDoorImg, openedDoorImg;

	private HashMap<Integer, JButton> doorElementsList = new HashMap<Integer, JButton>();

	public LabyrinthComponent()
	{

		closedDoorImg = new ImageIcon("./images/closed_door.png");
		openedDoorImg = new ImageIcon("./images/opened_door.png");
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		Map map = this.labyrinthView.getStartLabyrinth(); // Map is the start data -> mouseList and doorList contains newer

		// System.out.println("Repainting... " + map.getWidth() + "x" + map.getHeight() + " \nDoors: " + doorList.size());

		for(int y = 0; y < map.getHeight(); y++)
		{
			for(int x = 0; x < map.getWidth(); x++)
			{
				if(map.getField(x, y).getClass() == Wall.class)
				{
					g.setColor(Color.black);
					g.fillRect(x * 20, y * 20, 20, 20);
				}
				else if(map.getField(x, y).getClass() == Path.class)
				{
					g.setColor(Color.white);
					g.fillRect(x * 20, y * 20, 20, 20);
				}
				else if(map.getField(x, y).getClass() == Goal.class)
				{
					g.setColor(Color.yellow);
					g.fillRect(x * 20, y * 20, 20, 20);
				}
				else if(map.getField(x, y).getClass() == Door.class)
				{
					int doorID = ((Door)map.getField(x, y)).getId(); // Get it from the right list
					Door d = null;

					for(Door door : this.doorList)
					{
						if(door.getId() == doorID) d = door;
					}

					if(d == null)
					{
						System.out.println("paintComponent(): Error, door with id<" + doorID + "> not found!");
						return;
					}

					if(!doorElementsList.containsKey(d.getId()))
					{
						final JButton doorButton = new JButton(closedDoorImg);
						doorButton.setName("" + (d).getId());
						doorButton.setBounds(x * 20, y * 20, 20, 20);
						this.add(doorButton);
						this.doorElementsList.put(d.getId(), doorButton);

						doorButton.addActionListener(new ActionListener()
						{
							@Override
							public void actionPerformed(ActionEvent e)
							{
								if(doorButton.getIcon() == closedDoorImg)
								{
									// doorButton.setIcon(openedDoorImg);
									labyrinthView.setClickedButtonID(Integer.parseInt(doorButton.getName()));
								}
								else
								{
									// doorButton.setIcon(closedDoorImg);
									labyrinthView.setClickedButtonID(Integer.parseInt(doorButton.getName()));
								}
							}
						});

					}

					if(d.getDoorStatus() == Door.DOOR_OPEN) doorElementsList.get(d.getId()).setIcon(openedDoorImg);
					else doorElementsList.get(d.getId()).setIcon(closedDoorImg);

				}
			}
		}

		// From the game data, not the initial map
		for(Mouse m : mouseList)
			g.drawImage(this.createMouseImage(m.getColor()), m.getX() * 20, m.getY() * 20, 20, 20, null);

		this.setLayout(null);
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
	public void actionPerformed(ActionEvent e)
	{
		repaint();
	}
}
