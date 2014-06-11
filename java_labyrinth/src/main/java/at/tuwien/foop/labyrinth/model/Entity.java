package at.tuwien.foop.labyrinth.model;

import java.io.Serializable;

public abstract class Entity implements Serializable {

	private static final long serialVersionUID = 4724077628496390543L;
	
	protected char c;
	
	public Entity(char c) {
		this.c = c;
	}
	
	@Override
	public String toString() {
		return ""+c;
	}

	public char getCharacter() {
		return c;
	}

	public void setCharacter(char c) {
		this.c = c;
	}
	
}
