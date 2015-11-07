package stockApp;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Stock extends Observable{
	
	private String symbol;
	//Stock keeps ArrayList of its statuses over time
	private List<StockStatus> statuses = new ArrayList<StockStatus>();
	
	public Stock(String symbol, StockStatus status) {
		//add status on creation
		statuses.add(status);
		this.symbol = symbol;
		this.addObserver(EventHandler.getInstance());
		this.setChanged();
		this.notifyObservers(Event.ADD);
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	//add new status on stock update
	public void addStatus (StockStatus stockStatus) {
		statuses.add(stockStatus);
		this.setChanged();
		notifyObservers(Event.UPDATE);
	}
	
	//get most recently added status for this stock
	public StockStatus getCurrentStockStatus() {
		return statuses.get(statuses.size()-1);
	}
	
	public String toString(){
		return MessageFormat.format("{0} - {1}", symbol, this.getCurrentStockStatus().toString());
	}
}
