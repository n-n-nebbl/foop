package at.tuwien.foop.labyrinth.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EventBus {

	@SuppressWarnings("rawtypes")
	private Map<Class<? extends Event>, ArrayList<EventHandler>> subscriptions;
	
	public EventBus(){
		subscriptions = new HashMap<>();
	}
	
	@SuppressWarnings("rawtypes")
	public <T extends Event> boolean addEventHandler(EventHandler<T> handler, Class<T> subscribeTo){
		if(!subscriptions.containsKey(subscribeTo)){
			subscriptions.put(subscribeTo, new ArrayList<EventHandler>());
		}
		return subscriptions.get(subscribeTo).add(handler);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Event> void fireEvent(T event){
		for(EventHandler<T> eventHandler : subscriptions.get(event.getClass())){
			eventHandler.eventFired(event);
		}
	}
}
