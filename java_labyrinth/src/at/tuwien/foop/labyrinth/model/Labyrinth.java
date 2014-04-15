package at.tuwien.foop.labyrinth.model;

import java.io.Serializable;

public class Labyrinth implements Serializable {

	private String[][] labyrinth;
	
	public Labyrinth(int width, int height) {
		this.labyrinth = new String[width][height];
	}

	//TODO: load or generate labyrinth
	
	public String[][] getLabyrinth() {
		return labyrinth;
	}

	public void setLabyrinth(String[][] labyrinth) {
		this.labyrinth = labyrinth;
	}
}
