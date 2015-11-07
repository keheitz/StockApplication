package stockApp;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
/*
 * Stock Broker must be notified of stock creation
 */
public class StockBroker implements Observer{
	//
	private static StockBroker uniqueInstance;
	//collection of stocks to support keyed retrieval of stock objects
	private Map<String,Stock> stocks = new LinkedHashMap<String,Stock>();
		
	private StockBroker() {
		EventHandler.getInstance().register(this, Event.ADD);
	}
	
	//
	public static StockBroker getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new StockBroker();
		}
		
		return uniqueInstance;
	}

	@Override
	public void update(Observable stock, Object symbol) {
		//create new stocks
		Stock newStock = (Stock) stock;
		//add a reference to the created stock to collection of stocks kept by StockBroker
		stocks.put(newStock.getSymbol(), newStock); 
	}
	
	public void setStock(String symbol, Stock stock) {
	}

	public Stock getStock(String symbol) {
		return stocks.get(symbol);
	}
	
	public StockStatus getCurrentStockStatus(String symbol) {
		Stock stock = stocks.get(symbol);
		return stock.getCurrentStockStatus();
	}

}
