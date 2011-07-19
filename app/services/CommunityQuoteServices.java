package services;

import services.exceptions.SymbolInvalidException;
import models.Community;
import models.CommunityQuote;
import models.Quote;

public class CommunityQuoteServices {

	/**
	 * Retrieve a community quote, create if do not exist.
	 * 
	 * @param symbol
	 * @return
	 * @throws SymbolInvalidException
	 */
	public static CommunityQuote retrieveCommunityQuote(String symbol, Community community) throws SymbolInvalidException{
		
		CommunityQuote cummunityQuote = CommunityQuote.findBySymbolAndCommunityId(symbol, community.getId());
		
		if(cummunityQuote == null){
			Quote quote = QuoteServices.findQuote(symbol);
			
			cummunityQuote = new CommunityQuote(quote,community);
			
			cummunityQuote.save();
			
		}
		
		return cummunityQuote;
	}
	
}
