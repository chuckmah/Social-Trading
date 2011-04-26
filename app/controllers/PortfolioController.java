package controllers;

import java.math.BigDecimal;
import java.text.Format;
import java.util.Iterator;
import java.util.List;

import models.CommunityQuote;
import models.Portfolio;
import models.PortfolioEntry;
import models.Quote;

import org.apache.commons.lang.StringUtils;

import play.mvc.Controller;
import services.CommunityQuoteServices;
import services.QuoteServices;
import services.TransactionServices;
import services.exceptions.SymbolInvalidException;
import services.financeInfo.FinanceInfo;
import services.financeInfo.FinanceInfoManager;

public class PortfolioController extends Controller {

	public static void index(String communityName, String portfolioName) {
		Portfolio portfolio = null;
		if (!StringUtils.isEmpty(portfolioName)
				&& !StringUtils.isEmpty(communityName)) {
			portfolio = Portfolio.findByCommunityAndName(communityName,
					portfolioName);
		}

		if (portfolio == null) {
			error("portfolio not found");
		}

		render(portfolio);
	}
	
	public static void edit(String communityName, String portfolioName) {

		Portfolio portfolio = null;
		if (!StringUtils.isEmpty(portfolioName)
				&& !StringUtils.isEmpty(communityName)) {
			portfolio = Portfolio.findByCommunityAndName(communityName,
					portfolioName);
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
		try {
			communityquote = CommunityQuoteServices.retrieveCommunityQuote(symbol,portfolio.community);
		} catch (SymbolInvalidException e1) {
			flash.error("Invalid symbol " + symbol);
			edit(portfolio.community.name, portfolio.name);
		}

		int qty = -1;
		try {
			qty = Integer.parseInt(quantity);
		} catch (NumberFormatException e) {
			flash.error("Wrong quantiy " + quantity);
			edit(portfolio.community.name, portfolio.name);
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
				edit(portfolio.community.name, portfolio.name);
			}

		} else if (type.equals("Sell")) {
			PortfolioEntry porfioEntry = portfolio
					.getPortfolioEntryBySymbol(communityquote.quote.symbol);
			if (porfioEntry == null) {
				flash.error("Your portfolio do not contain any positions with symbol: "
						+ communityquote.quote.symbol);
				edit(portfolio.community.name, portfolio.name);
			} else {
				int shareQty = porfioEntry.shareQty;
				if (qty > 0 && qty <= shareQty) {

					TransactionServices.executeSellTransaction(portfolio,
							communityquote, qty);

				} else {

					flash.error("Wrong quantity (" + quantity
							+ ") the maximum quantiy you can sell is ("
							+ shareQty + ")");
					edit(portfolio.community.name, portfolio.name);
				}
			}
		}

		edit(portfolio.community.name, portfolio.name);
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
