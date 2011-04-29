package services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import models.Community;
import models.CommunityUser;
import models.Portfolio;
import models.PortfolioEntry;
import models.PortfolioStat;
import models.Quote;

public class PortofolioStatServices {
	
	
	public static void updateCommunityStat(Community community){
		List<CommunityUser> communityUsers = community.communityUsers;
		
		
		for (CommunityUser communityUser : communityUsers) {
			
			Portfolio portfolio = communityUser.portfolio;
			PortfolioStat stats = portfolio.stats;

			
			PortofolioStatServices.updateStat(stats);
			
			
			portfolio.save();
		}
	}
	
	public static void updateStat(PortfolioStat stats) {
		
		Portfolio portfolio = stats.portfolio;
	
		stats.entriesTotal =portfolio.portfolioEntries.size();
		stats.transactionTotal = portfolio.transactions.size();
		
		//Calculate the market value of a portfolio
		BigDecimal marketValue = portfolio.balance;
		
		List<PortfolioEntry> entries = portfolio.portfolioEntries;
		
		for (PortfolioEntry portfolioEntry : entries) {
			Quote quote = portfolioEntry.communityQuote.quote;
			BigDecimal quotePrice = quote.marketPrice;
			BigDecimal qty = new BigDecimal(portfolioEntry.shareQty);

			//calculate market value
			BigDecimal entryMarketValue = quotePrice.multiply(qty);
			marketValue = marketValue.add(entryMarketValue);
		}
		
		marketValue= marketValue.setScale(2,RoundingMode.HALF_UP);
		
		stats.marketValue = marketValue;
		
		BigDecimal 	startingBalance =	portfolio.communityUser.community.startingBalance;
		
		//calculate change
		BigDecimal change = (marketValue.subtract(startingBalance)).divide(startingBalance, 4, RoundingMode.HALF_UP);
	
		change = change.multiply(new BigDecimal(100));
		change = change.setScale(2,RoundingMode.HALF_UP);
		
		if(startingBalance.doubleValue() < marketValue.doubleValue()){
			stats.marketValueChange = "+" + change.toPlainString() + "%";
		}else{
			stats.marketValueChange = change.toPlainString() + "%";
		}
	}

}
