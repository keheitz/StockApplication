package stockApp;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
/*
 * 
 */
public class EventHandler implements Observer{

	private static EventHandler uniqueInstance;
	//Collection of observers for various events
	private static EnumMap<Event, List<Observer>> observers = new EnumMap<>(Event.class);
	
	private EventHandler() {}
	
	public static EventHandler getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new EventHandler();
		}
		
		return uniqueInstance;
	}
	//add observer for the type of event they wish to be notified about
	public void register(Observer observer, Event type){
		if(type == Event.All){
			register(observer, Event.ADD);
			register(observer, Event.UPDATE);
		}
		else 
		{
			if(!observers.containsKey(type)){
				observers.put(type, new ArrayList<Observer>());
			}
			observers.get(type).add(observer);
		}
	}
	//remove observer for the type of event for which they've requested to no longer be notified
	public void remove(Observer observer, Event type) {
		if(type == Event.All){
			remove(observer, Event.ADD);
			remove(observer, Event.UPDATE);
		}else if(observers.containsKey(type)){
			for(Observer o : observers.get(type)){
				if(o.equals(observer)){
					observers.get(type).remove(o);
				}
			}
		}
	}

	//update observers regarding notification event
	@Override
	public void update(Observable o, Object arg) {
		if(arg.getClass() != Event.class){
			System.out.print("Unknown event error");
			return;
		}
		Event notificationType = (Event) arg;
		if(notificationType == Event.All){
			this.update(o, Event.ADD);
			this.update(o, Event.UPDATE);
		}
		if(observers.containsKey(notificationType)){
			for(Observer o1 : observers.get(notificationType)){
				o1.update(o, arg);
			}
		}
	}	
}