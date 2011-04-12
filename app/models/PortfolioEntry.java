package models;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class PortfolioEntry extends Model {

    @ManyToOne
    public Portfolio portfolio;
	
	@Required
	public String symbol;
	
	public BigDecimal averagePrice;
	public BigDecimal lastPrice;
	public int quantity;
	
	public PortfolioEntry(String symbol, Portfolio portfolio){
		this.portfolio = portfolio;
		this.symbol = symbol;
		this.averagePrice = new BigDecimal(0);
		this.lastPrice = new BigDecimal(1);
		this.quantity = 0;
	}
	
    public String toString()  {
        return "Symbol(" + symbol + ")";
    }
	
}
