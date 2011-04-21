package controllers;

import java.util.List;
import java.util.Map;

import models.Community;
import models.Quote;

import org.apache.commons.lang.StringUtils;

import controllers.financeinfo.FinanceInfo;
import controllers.financeinfo.FinanceInfoManager;
import controllers.financeinfo.QuoteInfo;
import play.Logger;
import play.mvc.Controller;

public class QuotesController extends Controller {

	
	public static void refreshAllQuotes(){
		
		Logger.debug("Entering QuotesController.refreshAllQuotes()");
		
	  	List<Quote> quotes = Quote.findAll();
	  	
		Logger.debug("Refreshing "+quotes.size()+" quotes" );
	  	
	  	String[]  symbols = new String[quotes.size()];
	  	
	  	for (int i = 0; i < quotes.size(); i++) {
	  		Quote quote =  quotes.get(i);
	  		symbols[i] = quote.symbol;
		}
	  	
	  	Map<String, QuoteInfo> quoteInfos = FinanceInfoManager.getQuoteInfo(symbols);
	  	
	  	for (Quote  quote: quotes) {
	  		QuoteInfo quoteInfo = quoteInfos.get(quote.symbol);
	  		
	  		if(quoteInfo != null){
	  			boolean hasChanged = false;
	  			if(!quote.companyName.equals(quoteInfo.getCompanyName())){
		  			quote.companyName = quoteInfo.getCompanyName();
		  			hasChanged = true;
	  			}
	  			if(!quote.marketPrice.equals(quoteInfo.getLastTradePriceOnly())){
		  			quote.marketPrice = quoteInfo.getLastTradePriceOnly();
		  			hasChanged = true;
	  			}
	  			
	  			if(hasChanged){
		  			quote.save();		
	  			}
	  			
	  		}else{
	  			Logger.error("Error could not refresh quote: " + quote.symbol );
	  		}
		}
	  	
		Logger.debug("Exiting QuotesController.refreshAllQuotes()");
	}
	
	public static Quote findQuote(String symbol){
		
		Quote quote = null;
		
    	quote = Quote.findBySymbol(symbol);
    	
    	return quote;
	}
	
	
	public static Quote refreshQuote(Quote quote){

		QuoteInfo quoteInfo = FinanceInfoManager.getQuoteInfo(quote.symbol);

		if(quoteInfo != null){
			
			quote.companyName = quoteInfo.getCompanyName();
			quote.marketPrice = quoteInfo.getLastTradePriceOnly();
			
			
		}else{
			return null;
		}
		
		quote.save();
		
		return quote;
	}
	
	public static Quote addQuote(String symbol){
		Quote quote = null;
    	
    	if(StringUtils.isEmpty(symbol)){
    		return null;
    	}
    	
    	if(quote == null){
    		
    		QuoteInfo quoteInfo = FinanceInfoManager.getQuoteInfo(symbol);

    		if(quoteInfo != null){
    			quote = new Quote(symbol);
    			
    			quote.companyName = quoteInfo.getCompanyName();
    			quote.marketPrice = quoteInfo.getLastTradePriceOnly();
    			
    			quote.save();
    		}else{
    			return null;
    		}
    	}
    	
    	return quote;
		
	}
	
}
