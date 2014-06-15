package at.tuwien.foop.labyrinth.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Observable;

import javax.annotation.Resource;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.springframework.stereotype.Component;

import at.tuwien.foop.labyrinth.StartLabyrinth;
import at.tuwien.foop.labyrinth.model.Door;
import at.tuwien.foop.labyrinth.model.Map;
import at.tuwien.foop.labyrinth.model.Mouse;

@Component
public class LabyrinthView extends Observable
{

	private int clickedButtonID;

	@Resource(name = "doorList")
	private List<Door> doorList;

	@Resource(name = "mouseList")
	private List<Mouse> mouseList;

	@Resource
	private LabyrinthComponent labyrinthComponent;

	private Map labyrinth;

	private JFrame f;

	public LabyrinthView()
	{
		waitForGameStart();
	}

	public void setTitel(String text)
	{
		if (f != null)
		{
			if (text.isEmpty())
				f.setTitle("Mouse Labyrinth");
			else
				f.setTitle("Mouse Labyrinth - " + text);
		}
	}

	public int getClickedButtonID()
	{
		return this.clickedButtonID;
	}

	public void setClickedButtonID(int clickedButtonID)
	{
		this.clickedButtonID = clickedButtonID;
		setChanged();
		notifyObservers();
	}

	public void repaintAll()
	{
		labyrinthComponent.repaint();
	}

	public Map getStartLabyrinth()
	{
		return this.labyrinth;
	}

	public void waitForGameStart()
	{
		this.waitForGameStart(-1);
	}

	public void waitForGameStart(int wonID)
	{
		if (wonID != -1 && f != null)
		{
			for (Mouse m : mouseList)
			{
				if (m.getId() == wonID)
					JOptionPane.showMessageDialog(f,
							"Game ended, player <" + m.getColor() + "> won!",
							"Game ended", JOptionPane.PLAIN_MESSAGE);
			}

		}

		if (f != null)
			f.setVisible(false);

		// Start window
		f = new JFrame();
		f.setSize(300, 100);
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		f.setLayout(null);
		this.setTitel("Waiting for start...");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.addWindowListener(new ExitWindowAdapter());

		JButton startButton = new JButton("Start game!");
		startButton.setBounds(5, 5, 290, 50);

		// Todo: MVC Concept?
		startButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					if (StartLabyrinth.getLabyrinthServer().gameIsRunning())
					{
						System.out
								.println("LabyrinthView(): Error, game already running.");
					} else
						StartLabyrinth.getLabyrinthServer().startGame();
				} catch (RemoteException e1)
				{
					System.out
							.println("LabyrinthView(): Error, distributing event.");
					e1.printStackTrace();
				}
			}
		});

		f.add(startButton);
	}

	public void setGameRunning(Map labyrinth)
	{
		if (f != null)
			f.setVisible(false);

		this.labyrinth = labyrinth;
		f = new JFrame();
		f.add(labyrinthComponent);
		f.setSize(20 * labyrinth.getWidth(), 20 * labyrinth.getHeight() + 20);
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		f.setLayout(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		f.addWindowListener(new ExitWindowAdapter());
		this.setTitel("");
	}

	class ExitWindowAdapter extends WindowAdapter
	{
		// Close the labyrinth and exit
		public void windowClosing(WindowEvent e)
		{

			StartLabyrinth.onExit();
			System.exit(0);
		}
	}

}
