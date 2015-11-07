package stockApp;

public class Money {
    private String currency;
    private double value;

    public Money(String currency, double price) {
        this.currency = currency;
        this.value = price;
    }
    
    //Format money object as currency symbol + value 
    @Override
    public String toString() {
        return this.currency + " " + this.value;
    }
    
    //Future update: add currency 
}

