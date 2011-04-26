package controllers;

import play.*;
import play.mvc.*;
import services.PortofolioStatServices;
import services.QuoteServices;

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
    	


    	render(community);
    			
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
    



    

}