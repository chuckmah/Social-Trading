package controllers;

import java.util.List;

import org.apache.commons.lang.StringUtils;


import models.Portfolio;
import models.PortfolioEntry;
import play.mvc.Controller;
import pojos.PortfolioEntryPOJO;

public class PortfolioController extends Controller {

	


	public static void index(String communityName, String portfolioName ) {

       	Portfolio portfolio = null;
    	if (!StringUtils.isEmpty(portfolioName) && !StringUtils.isEmpty(communityName) ){
    		portfolio = Portfolio.findByCommunityAndName(communityName, portfolioName);
    	}
    	
    	if(portfolio == null){
    		error("portfolio not found");
    	}
    	
    	List<PortfolioEntry> portfolioEntries = null;
    	
       	render(portfolio,portfolioEntries);
    }
    
    public static void addPortfolioEntry(String symbol, Long portfolioId){
    	
    	Portfolio portfolio = Portfolio.findById(portfolioId);
    	
    	PortfolioEntry portfolioEntry = new PortfolioEntry(symbol,portfolio).save();
    	
    	PortfolioEntryPOJO portfolioEntryPOJO = new PortfolioEntryPOJO(portfolioEntry);
    	renderJSON(portfolioEntryPOJO);
    
    }
    
}
