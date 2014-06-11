package at.tuwien.foop.labyrinth.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import at.tuwien.foop.labyrinth.StartLabyrinth;

//the labyrinth class laods a .txt file including the map

public class Map implements Serializable {

	private static final long serialVersionUID = -2240504228054362955L;
	private String path;
	private ArrayList<ArrayList<Entity>> field = new ArrayList<ArrayList<Entity>>();
	private int width = 0;
	private int height = 0;
	private ArrayList<Door> doorList = new ArrayList<Door>();
	private ArrayList<Mouse> mouseList = new ArrayList<Mouse>();
	private Goal goal = null;
	int paths = 0;
		
	public Map(String path) {
		this.path = path;
		this.loadLabyrinth();
	}

	public void loadLabyrinth(){
						
		try
		{
			Scanner sc = new Scanner(new File(this.path));
			int width = -1;
						
			while(sc.hasNext())
			{
				String line = sc.next().trim();
				
				// Empty line
				if(line.length() <= 0)
				{
					break;
				}
				else if(width != -1 && line.length() != width)
				{
					System.out.println("loadLabyrinth(): Error, line lenght is not equal for all.");
					sc.close();
					StartLabyrinth.onExit();
					System.exit(0);
					return;
				}
				// Got a line
				else
				{
					ArrayList<Entity> currentLine = new ArrayList<Entity>();
					
					for(int i = 0; i < line.length(); i++)
					{
						char c = line.charAt(i);
						
						switch(c)
						{
							case 'W':
								currentLine.add(new Wall());
								break;
							case 'P':
								currentLine.add(new Path());
								this.paths++;
								break;
							case 'G':
								this.goal = new Goal();
								currentLine.add(this.goal);
								break;
							case 'D':
								Door d = new Door();
								doorList.add(d);
								currentLine.add(d);
								break;
							default:
								System.out.println("loadLabyrinth(): Strange char in labyrinth file.");
								sc.close();
								StartLabyrinth.onExit();
								System.exit(0);
								return;						
						}
					}

					this.field.add(currentLine);
					this.height++;
					width = this.width = currentLine.size();
					
				}
			}
			sc.close();

		} catch (FileNotFoundException e) {
			System.out.println("Error loading Field!");
			StartLabyrinth.onExit();
			System.exit(0);
			return;				
		}		
		
		
		if(goal == null)
		{
			System.out.println("Error, no goal in the map field!");
			StartLabyrinth.onExit();
			System.exit(0);
			return;				
		}	

		// No places
		if(paths <= 0)
		{
			System.out.println("Error, no entries for mouses existing!");
			StartLabyrinth.onExit();
			System.exit(0);
			return;			
		}		
	}
	
	public void addMouse()
	{		
		Random randomGenerator = new Random();
		
		// Place the mouses somewhere -> random till we get a P. There is a P(logic in loadLabyrinth)
		while(true)
		{
			int y = randomGenerator.nextInt(this.field.size());
			ArrayList<Entity> currentLine = field.get(y);
			int x = randomGenerator.nextInt(currentLine.size());
			Entity e = currentLine.get(x);
			
			if(e.getCharacter() == 'P')
			{
				this.mouseList.add(new Mouse(x, y));
				return;
			}
		}
	}
	
	public Entity getField(int x, int y){
		return field.get(y).get(x);
	}

	public Mouse getMouseByID(int id){
		for (Mouse m : mouseList) {
			if (m.getId() == id) {
				return m;
			}
		}
		
		return null;
	}
		
	public Door getDoorByID(int id){
		for (Door d : doorList) {
			if (d.getId() == id) {
				return d;
			}
		}
		
		return null;
	}

	public ArrayList<Door> getDoorList()
	{
		return this.doorList;
	}

	public ArrayList<Mouse> getMouseList()
	{
		return this.mouseList;
	}

	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
}
