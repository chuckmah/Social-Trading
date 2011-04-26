package controllers;

import org.apache.commons.lang.StringUtils;

import play.mvc.Controller;

import models.Community;
import models.CommunityQuote;

public class CommunityQuoteController extends Controller{

	public static void index(String communityName, String quoteSymbol) {
		
    	Community community = null ;
    	if (!StringUtils.isEmpty(communityName)){
    		community =	Community.findByName(communityName);
    	}
    	
    	if(community == null){
    		error("Community not found");
    	}
    	
		
		CommunityQuote communityQuote = CommunityQuote.findBySymbolAndCommunityId(quoteSymbol,community.id);
		
    	if(communityQuote == null){
    		error("Quote not found");
    	}
		
		render(communityQuote);
	}
}
