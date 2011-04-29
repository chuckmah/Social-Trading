package controllers;

import java.math.BigDecimal;
import java.text.Format;
import java.util.Iterator;
import java.util.List;

import models.Community;
import models.CommunityQuote;
import models.Portfolio;
import models.PortfolioEntry;
import models.Quote;
import models.User;

import org.apache.commons.lang.StringUtils;

import play.mvc.Controller;
import services.CommunityQuoteServices;
import services.QuoteServices;
import services.TransactionServices;
import services.exceptions.SymbolInvalidException;
import services.financeInfo.FinanceInfo;
import services.financeInfo.FinanceInfoManager;
import play.mvc.With;

@With(Secure.class)
public class PortfolioController extends BaseController {

	public static void index(String communityName, String alias) {
		
    	User user =  (User) renderArgs.get("user");
    	if(user == null || !user.isMember(communityName)){
		   CommunityController.checkmembership(communityName);
    	}
		
		Portfolio portfolio = null;
		if (!StringUtils.isEmpty(alias)
				&& !StringUtils.isEmpty(communityName)) {
			portfolio = Portfolio.findByCommunityAndAlias(communityName,
					alias);
		}

		if (portfolio == null) {
			error("portfolio not found");
		}

		render(portfolio);
	}
	
	public static void edit(String communityName) {

    	User user =  (User) renderArgs.get("user");
    	if(user == null || !user.isMember(communityName)){
		   CommunityController.checkmembership(communityName);
    	}
		
		Portfolio portfolio = null;
		if (!StringUtils.isEmpty(user.alias)
				&& !StringUtils.isEmpty(communityName)) {
			portfolio = Portfolio.findByCommunityAndAlias(communityName,
					user.alias);
		}

		if (portfolio == null) {
			error("portfolio not found");
		}

		render(portfolio);
	}

	public static void addTransaction(Long portfolioId, String symbol,
			String type, String quantity) {


		
		Portfolio portfolio = Portfolio.findById(portfolioId);

		CommunityQuote communityquote = null;
		
		Community community = portfolio.communityUser.community;
	
    	User user =  (User) renderArgs.get("user");
    	if(user == null || !user.isMember(community.name)){
		   CommunityController.checkmembership(community.name);
    	}
		
		try {
			communityquote = CommunityQuoteServices.retrieveCommunityQuote(symbol,community);
		} catch (SymbolInvalidException e1) {
			flash.error("Invalid symbol " + symbol);
			edit(community.name);
		}

		int qty = -1;
		try {
			qty = Integer.parseInt(quantity);
		} catch (NumberFormatException e) {
			flash.error("Wrong quantiy " + quantity);
			edit(community.name);
		}

		BigDecimal price = communityquote.quote.marketPrice;
		BigDecimal currentBalance = portfolio.balance;

		if (type.equals("Buy")) {

			int maxBuy = (int) (currentBalance.doubleValue() / price
					.doubleValue());

			if (qty > 0 && qty <= maxBuy) {

				TransactionServices.executeBuyTransaction(portfolio,
						communityquote, qty);

			} else {
				flash.error("Wrong quantity (" + quantity
						+ ") the max quantiy you can buy is (" + maxBuy
						+ ") at actual price of " + price + " $");
				edit(community.name);
			}

		} else if (type.equals("Sell")) {
			PortfolioEntry porfioEntry = portfolio
					.getPortfolioEntryBySymbol(communityquote.quote.symbol);
			if (porfioEntry == null) {
				flash.error("Your portfolio do not contain any positions with symbol: "
						+ communityquote.quote.symbol);
				edit(community.name);
			} else {
				int shareQty = porfioEntry.shareQty;
				if (qty > 0 && qty <= shareQty) {

					TransactionServices.executeSellTransaction(portfolio,
							communityquote, qty);

				} else {

					flash.error("Wrong quantity (" + quantity
							+ ") the maximum quantiy you can sell is ("
							+ shareQty + ")");
					edit(community.name);
				}
			}
		}

		edit(community.name);
	}

	public static void addQuoteToWatch(String symbol, Long portfolioId) {


		
		Quote quoteToWatch = null;

		try {
			quoteToWatch = QuoteServices.findQuote(symbol);

			if (quoteToWatch == null) {
				quoteToWatch = QuoteServices.addQuote(symbol);
				if (quoteToWatch == null) {
					error("Invalid symbol : " + symbol);
				}
			}
		} catch (SymbolInvalidException e) {
			error("Invalid symbol  " + symbol + " !");
		}
		Portfolio portfolio = Portfolio.findById(portfolioId);
		if (!portfolio.addQuoteToWatch(quoteToWatch)) {
			error("Symbol " + symbol + " already exist in list!");
		}

		portfolio.save();
		renderJSON(quoteToWatch);

	}

	public static void removeQuoteToWatch(Long id, Long portfolioId) {

		Portfolio portfolio = Portfolio.findById(portfolioId);

		List<Quote> watchQuotes = portfolio.watchQuotes;

		int index = 0;
		int toRemove = -1;

		for (Quote quote : watchQuotes) {
			if (quote.id.longValue() == id.longValue()) {
				toRemove = index;
				break;
			} else {
				index++;
			}
		}

		if (toRemove != -1) {
			watchQuotes.remove(toRemove);
			portfolio.save();

		} else {
			error("Quote not found in" + portfolioId);
		}

		renderText("Success");
	}
}
