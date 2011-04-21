package controllers;

import java.math.BigDecimal;
import java.text.Format;
import java.util.Iterator;
import java.util.List;

import models.Portfolio;
import models.PortfolioEntry;
import models.Quote;

import org.apache.commons.lang.StringUtils;

import controllers.financeinfo.FinanceInfo;
import controllers.financeinfo.FinanceInfoManager;


import play.mvc.Controller;


public class PortfolioController extends Controller {


	public static void index(String communityName, String portfolioName ) {

       	Portfolio portfolio = null;
    	if (!StringUtils.isEmpty(portfolioName) && !StringUtils.isEmpty(communityName) ){
    		portfolio = Portfolio.findByCommunityAndName(communityName, portfolioName);
    	}
    	
    	if(portfolio == null){
    		error("portfolio not found");
    	}
    	

       	render(portfolio);
    }
    
	public static void addTransaction(Long portfolioId, String symbol, String type, String quantity){

    	Portfolio portfolio = Portfolio.findById(portfolioId);

		
    	Quote quote = null;
    	
    	quote = QuotesController.findQuote(symbol);
 	
    	if(quote == null){
    		quote = QuotesController.addQuote(symbol);
    		if(quote == null){
    			flash.error("Invalid symbol : " + symbol);
    			index(portfolio.community.name,portfolio.name);
    		}
    	}else{
    		QuotesController.refreshQuote(quote);
    	}
    	
		int qty = -1;
		try{
    		qty = Integer.parseInt(quantity);
		}catch (NumberFormatException e) {
			flash.error("Wrong quantiy " + quantity );
			index(portfolio.community.name,portfolio.name);
		}
		
    	BigDecimal price = quote.marketPrice;
    	BigDecimal currentBalance = portfolio.balance;
    	
    	if(type.equals("Buy")){


        	int maxBuy = (int) (currentBalance.doubleValue() / price.doubleValue());
        	
        	if(qty > 0 && qty <= maxBuy){
        		
        		TransactionsController.executeBuyTransaction(portfolio, quote, qty);
        		
        	}else{
    			flash.error("Wrong quantity (" + quantity +") the max quantiy you can buy is (" + maxBuy+ ") at actual price of " +price + " $" );
    			index(portfolio.community.name,portfolio.name);
        	}
    		
    	}else if(type.equals("Sell")){
    		PortfolioEntry porfioEntry = portfolio.getPosition(quote);
    		if(porfioEntry == null){
    			flash.error("Your portfolio do not contain any positions with symbol: "+ quote.symbol);
    			index(portfolio.community.name,portfolio.name);
    		}else{
    			int shareQty = porfioEntry.shareQty;
    			if(qty > 0 && qty <= shareQty){
    				
    				TransactionsController.executeSellTransaction(portfolio, quote, qty);
    				
    			}else{
        			flash.error("Wrong quantity (" + quantity + ") the maximum quantiy you can sell is (" +shareQty+")") ;
        			index(portfolio.community.name,portfolio.name);
    			}
    		}
    	}
    	

    	
    	index(portfolio.community.name,portfolio.name);
	}
	
    public static void addQuoteToWatch(String symbol, Long portfolioId){

    	Quote quoteToWatch = null;
    	
    	quoteToWatch = QuotesController.findQuote(symbol);
 	
    	if(quoteToWatch == null){
    		quoteToWatch = QuotesController.addQuote(symbol);
    		if(quoteToWatch == null){
        		error("Invalid symbol : " + symbol);
    		}
    	}
    	
    	Portfolio portfolio = Portfolio.findById(portfolioId);
    	if(!portfolio.addQuoteToWatch(quoteToWatch)){
    		error("Symbol " + symbol +" already exist in list!");
    	}

    	portfolio.save();
    	renderJSON(quoteToWatch);
    
    }
    
    
    public static void removeQuoteToWatch(Long id, Long portfolioId){

    	Portfolio portfolio = Portfolio.findById(portfolioId);

    	List<Quote> watchQuotes = portfolio.watchQuotes;

    	int index = 0;
    	int toRemove = -1;
    	
    	for (Quote quote : watchQuotes) {
			if(quote.id.longValue() == id.longValue()){
				toRemove = index;
				break;
			}else{
	    		index++;
			}
		}

    	if(toRemove != -1){
    		watchQuotes.remove(toRemove);
    		portfolio.save();
        	
    	}else{
    		error("Quote not found in" + portfolioId);
    	}
    	
		renderText("Success");
    }
}
