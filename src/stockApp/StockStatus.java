package stockApp;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StockStatus {

	private Date date;
	private Money money;
	
	public StockStatus(Date date, Money money) {
		this.date = date;
		this.money = money;
	}
	
	public Date getDate() { 
		return date;
	}
	
	public Money getPrice() {
		return money;
	}
	//Format date and money for display in Stock Monitor
	public String toString(){
		return MessageFormat.format("{1} - {0}", money.toString(), new SimpleDateFormat("h:mm:ss").format(date));
	}
	
}
