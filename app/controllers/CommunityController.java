package controllers;

import play.*;
import play.mvc.*;
import services.PortofolioStatServices;
import services.QuoteServices;

import java.math.BigDecimal;
import java.util.*;

import org.apache.commons.lang.StringUtils;

import models.*;


@With(Secure.class)
public class CommunityController extends BaseController {


    public static void index(String communityName) {
    	
    	checkmembership(communityName);

    	Community community = null ;
    	if (!StringUtils.isEmpty(communityName)){
    		community =	Community.findByName(communityName);
    	}
    	
    	if(community == null){
    		error("Community not found");
    	}
    	
    	render(community);

    }


    
    
    public static void join(String communityName) {
    	User user =  (User) renderArgs.get("user");
    	if(user != null){
    		if(user.isMember(communityName)){
    			error("Already a member of this community");
    		}
     	}
    	
    	Community community = null ;
    	if (!StringUtils.isEmpty(communityName)){
    		community =	Community.findByName(communityName);
    	}
    	
    	CommunityUser communityUser = new CommunityUser();
    	communityUser.user = user;
    	communityUser.community = community;
    	
    	Portfolio portfolio = new Portfolio(communityUser, community.startingBalance);

    	communityUser.portfolio = portfolio;

    	PortofolioStatServices.updateStat(portfolio.stats);
    	communityUser.save();
    	
    	//Redirect to Edit your porfolio
    	PortfolioController.edit(communityName);

     }
    

    public static void addComment(String communityName, String comment){
    	
    	checkmembership(communityName);
    	
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