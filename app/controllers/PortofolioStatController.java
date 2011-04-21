package controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import models.Portfolio;
import models.PortfolioEntry;
import models.PortfolioStat;
import models.Quote;

public class PortofolioStatController {

	public static void updateStat(PortfolioStat stats, BigDecimal startingBalace) {
		
		
		Portfolio portfolio = stats.portfolio;
		
		stats.entriesTotal =portfolio.portfolioEntries.size();
		stats.transactionTotal = portfolio.transactions.size();
		
		//Calculate the market value of a portfolio
		BigDecimal marketValue = portfolio.balance;
		
		List<PortfolioEntry> entries = portfolio.portfolioEntries;
		
		for (PortfolioEntry portfolioEntry : entries) {
			Quote quote = portfolioEntry.quote;
			BigDecimal quotePrice = quote.marketPrice;
			BigDecimal qty = new BigDecimal(portfolioEntry.shareQty);

			//calculate market value
			BigDecimal entryMarketValue = quotePrice.multiply(qty);
			marketValue = marketValue.add(entryMarketValue);

		}
		
		marketValue= marketValue.setScale(2,RoundingMode.HALF_UP);
		
		stats.marketValue = marketValue;
		
		//calculate change
		BigDecimal change = (marketValue.subtract(startingBalace)).divide(startingBalace, 4, RoundingMode.HALF_UP);
	
		change = change.multiply(new BigDecimal(100));
		change = change.setScale(2,RoundingMode.HALF_UP);
		
		if(startingBalace.doubleValue() < marketValue.doubleValue()){
			stats.marketValueChange = "+" + change.toPlainString() + "%";
		}else{
			stats.marketValueChange = change.toPlainString() + "%";
		}
	}

}
