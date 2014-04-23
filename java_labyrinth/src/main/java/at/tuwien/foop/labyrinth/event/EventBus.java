package at.tuwien.foop.labyrinth.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventBus {

	@SuppressWarnings("rawtypes")
	private Map<Class<? extends Event>, ArrayList<EventHandler>> subscriptions = new HashMap<>();
	
	public EventBus() {
	}
	
	public <T extends Event> EventBus(List<EventHandler<T>> initEventHandlers) {
		for(EventHandler<T> eh : initEventHandlers) {
			addEventHandler(eh);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public <T extends Event> boolean addEventHandler(EventHandler<T> handler){
		if(!subscriptions.containsKey(handler.getEventClass())){
			subscriptions.put(handler.getEventClass(), new ArrayList<EventHandler>());
		}
		return subscriptions.get(handler.getEventClass()).add(handler);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Event> void fireEvent(T event){
		for(EventHandler<T> eventHandler : subscriptions.get(event.getClass())){
			eventHandler.eventFired(event);
		}
	}
}
