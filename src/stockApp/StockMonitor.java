package stockApp;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
/*
 * Class that monitors and displays the current stock price information about ALL stocks active in the system
 */
public class StockMonitor extends Observable implements Observer{
	
	private static StockMonitor uniqueInstance;
	
	//must be notified both both events, creation AND stock pricing changes
	private StockMonitor() {
		EventHandler.getInstance().register(this, Event.All);
	}
	
	private List<Stock> stocks = new ArrayList<Stock>();
	
	//Create singleton
	public static StockMonitor getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new StockMonitor();
		}
		
		return uniqueInstance;
	}

	@Override
	public void update(Observable stock, Object eventType) {
		Event notificationType = (Event) eventType;
		if (notificationType == Event.ADD) {
			stocks.add((Stock)stock);
		}
		
		this.setChanged();
		this.notifyObservers();
	}
	
	//display for UI
	public String toString(){
		String txtDisplay = "Symbol  -  Time  -  Price";
		
		for(Stock s : stocks){
			txtDisplay += "\n" + s.toString();
		}
		
		return txtDisplay;
	}
}
