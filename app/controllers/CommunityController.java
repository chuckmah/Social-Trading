package controllers;

import play.*;
import play.mvc.*;

import java.math.BigDecimal;
import java.util.*;

import org.apache.commons.lang.StringUtils;

import models.*;

public class CommunityController extends Controller {

    public static void index(String communityName) {
    	
    	Community community = null ;
    	if (!StringUtils.isEmpty(communityName)){
    		community =	Community.findByName(communityName);
    	}
    	
    	if(community == null){
    		error("Community not found");
    	}
    	
    	
    	QuotesController.refreshAllQuotes();
    	refreshCommunity(community);
    	List<Quote> quotes = refreshStocksInfo();
    	
    	render(community,quotes);
    			
    }


    public static void addComment(String communityName, String comment){
    	
    	Community community = null ;
    	if (!StringUtils.isEmpty(communityName)){
    		community =	Community.findByName(communityName);
    	}
    	
    	if(community == null){
    		error("Community not found");
    	}
    	
    	
    	//Comment comment = new Comment(community, author, content)
    }
    
	private static List<Quote> refreshStocksInfo() {
		
		List<Quote> quotes = Quote.findAll();
		for (Quote quote : quotes) {
			
			QuoteStat stats = quote.stats;
			
			if(quote.stats == null){
				stats = new QuoteStat();
				stats.quote = quote;
				quote.stats = stats;
			}
			
			stats.totalOccurence = PortfolioEntry.findByQuote(quote).size();
			quote.save();
		}
		
		return quotes;
	} 


	private static void refreshCommunity(Community community) {
		//refresh all portfolio stats;
		
		List<Portfolio> portfolios = community.portfolios;
		
		BigDecimal startingBalace = community.startingBalance;
		
		for (Portfolio portfolio : portfolios) {
			
			
			PortfolioStat stats = portfolio.stats;
			if(stats == null){
				stats = new PortfolioStat();
				stats.portfolio = portfolio;
				portfolio.stats = stats;
			}
			
			PortofolioStatController.updateStat(stats,startingBalace);
			
			
			portfolio.save();
		}
		
	}
    

}