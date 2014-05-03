package at.tuwien.foop.labyrinth.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Scanner;

public class Labyrinth implements Serializable {

	private String path, field[];
	private int size;
	private Scanner sc;
	
	public Labyrinth(int size, String path) {
		this.size = size;
		this.path = path;
		this.field = new String[size];
		this.loadLabyrinth();
	}

	public void loadLabyrinth(){
		try {
			sc = new Scanner(new File(this.path));
			
			while(sc.hasNext()){
				for(int i=0; i<this.size; i++){
					field[i] = sc.next();
				}
			}
			sc.close();

		} catch (FileNotFoundException e) {
			System.out.println("Error loading Field!");
		}
	}
	
	public String getLabyrinth(int x, int y){
		return field[y].substring(x,x+1);
	}

	public int getSize() {
		return size;
	}
}
