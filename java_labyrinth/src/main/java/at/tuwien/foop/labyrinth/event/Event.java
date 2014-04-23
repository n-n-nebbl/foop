package at.tuwien.foop.labyrinth.event;

import java.io.Serializable;

/**
 * Ein Event informiert �ber eine Zustands�nderung
 * 
 * Es darf nur aus serialisierbaren Attirbuten bestehen und wird
 * beim �bertragen zu den anderen Clients kopiert (call by value)!
 * 
 * Die enthaltenen Informationen sollten absulut und nicht relativ sein:
 * Zustand Koordniate �ndert sich --> neue Koordinaten ganz angeben
 * 
 * @author Alexander Nebel
 */
public interface Event extends Serializable {

}
