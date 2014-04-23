package at.tuwien.foop.labyrinth.event;

import java.io.Serializable;

/**
 * Ein Event informiert über eine Zustandsänderung
 * 
 * Es darf nur aus serialisierbaren Attirbuten bestehen und wird
 * beim Übertragen zu den anderen Clients kopiert (call by value)!
 * 
 * Die enthaltenen Informationen sollten absulut und nicht relativ sein:
 * Zustand Koordniate ändert sich --> neue Koordinaten ganz angeben
 * 
 * @author Alexander Nebel
 */
public interface Event extends Serializable {

}
