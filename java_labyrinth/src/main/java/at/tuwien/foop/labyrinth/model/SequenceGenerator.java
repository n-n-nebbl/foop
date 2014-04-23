package at.tuwien.foop.labyrinth.model;

import java.util.HashMap;

public class SequenceGenerator {

	private static HashMap<String, Integer> sequences = new HashMap<>();
	
	public synchronized static int getNextId(String key) {
		int value = 0;
		if(sequences.containsKey(key)) {
			value = sequences.get(key);
		}
		sequences.put(key, value + 1);
		return value;
	}
}
