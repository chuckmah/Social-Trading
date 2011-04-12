package pojos;

import java.math.BigDecimal;

import models.PortfolioEntry;

public class PortfolioEntryPOJO {

	private String symbol;	
	private BigDecimal averagePrice;
	private BigDecimal lastPrice;
	private int quantity;
	
	
	public PortfolioEntryPOJO(PortfolioEntry portfolioEntry) {
		this.symbol = portfolioEntry.symbol;
		this.averagePrice = portfolioEntry.averagePrice;
		this.lastPrice = portfolioEntry.lastPrice;
		this.quantity = portfolioEntry.quantity;
		
	}


	public String getSymbol() {
		return symbol;
	}


	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}


	public BigDecimal getAveragePrice() {
		return averagePrice;
	}


	public void setAveragePrice(BigDecimal averagePrice) {
		this.averagePrice = averagePrice;
	}


	public BigDecimal getLastPrice() {
		return lastPrice;
	}


	public void setLastPrice(BigDecimal lastPrice) {
		this.lastPrice = lastPrice;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	
	
}
