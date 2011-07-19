package services;

import java.util.List;
import java.util.Map;

import models.Quote;

import org.apache.commons.lang.StringUtils;

import play.Logger;
import services.exceptions.SymbolInvalidException;
import services.financeInfo.FinanceInfoManager;
import services.financeInfo.QuoteInfo;


public class QuoteServices {

	public static void refreshAllQuotes() {

		Logger.debug("Entering QuotesController.refreshAllQuotes()");

		List<Quote> quotes = Quote.findAll();

		Logger.debug("Refreshing " + quotes.size() + " quotes");

		String[] symbols = new String[quotes.size()];

		for (int i = 0; i < quotes.size(); i++) {
			
			if(quotes.get(i) instanceof Quote){
				Quote quote = quotes.get(i);
				symbols[i] = quote.symbol;
			}else{
				Logger.error("unknow quote element");
				return;
			}
			

		}

		Map<String, QuoteInfo> quoteInfos = FinanceInfoManager
				.getQuoteInfo(symbols);

		for (Quote quote : quotes) {
			QuoteInfo quoteInfo = quoteInfos.get(quote.symbol);

			if (quoteInfo != null) {
				boolean hasChanged = false;
				if (!quote.companyName.equals(quoteInfo.getCompanyName())) {
					quote.companyName = quoteInfo.getCompanyName();
					hasChanged = true;
				}
				if (!quote.marketPrice
						.equals(quoteInfo.getLastTradePriceOnly())) {
					quote.marketPrice = quoteInfo.getLastTradePriceOnly();
					hasChanged = true;
				}

				if (hasChanged) {
					quote.save();
				}

			} else {
				Logger.error("Error could not refresh quote: " + quote.symbol);
			}
		}

		Logger.debug("Exiting QuotesController.refreshAllQuotes()");
	}

	public static Quote findQuote(String symbol) throws SymbolInvalidException {

		Quote quote = null;

		quote = Quote.findBySymbol(symbol);

		if (quote == null) {
			quote = addQuote(symbol);

		} else {
			refreshQuote(quote);
		}

		return quote;
	}

	private static Quote refreshQuote(Quote quote) {

		QuoteInfo quoteInfo = FinanceInfoManager.getQuoteInfo(quote.symbol);

		if (quoteInfo != null) {

			quote.companyName = quoteInfo.getCompanyName();
			quote.marketPrice = quoteInfo.getLastTradePriceOnly();

		} else {
			return null;
		}

		quote.save();

		return quote;
	}

	public static Quote addQuote(String symbol) throws SymbolInvalidException {
		Quote quote = null;

		if (StringUtils.isEmpty(symbol)) {
			return null;
		}

		if (quote == null) {

			QuoteInfo quoteInfo = FinanceInfoManager.getQuoteInfo(symbol);

			if (quoteInfo != null) {
				quote = new Quote(symbol);

				quote.companyName = quoteInfo.getCompanyName();
				quote.marketPrice = quoteInfo.getLastTradePriceOnly();

				quote.save();
			} else {
				throw new SymbolInvalidException();
			}
		}

		return quote;

	}

}
