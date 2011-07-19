package controllers;

import org.apache.commons.lang.StringUtils;

import play.mvc.Controller;
import play.mvc.With;

import models.Community;
import models.CommunityQuote;
import models.User;


@With(Secure.class)
public class CommunityQuoteController extends BaseController{

	public static void index(String communityName, String quoteSymbol) {
		

    	checkmembership(communityName);
    	
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
