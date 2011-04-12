package controllers;

import play.*;
import play.mvc.*;

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
    
    public static void addPortofolio(String communityName, String name) {
    	
  	
    	if(StringUtils.isEmpty(name)){
            flash.error("Portfolio name can not be empty");
            index(communityName);
    	}
    	
    	
    	Community community = null ;
    	if (!StringUtils.isEmpty(communityName)){
    		community =	Community.findByName(communityName);
    	}
    	
    	if(community == null){
    		error("Community not found");
    	}

    	
    	Portfolio portfolio = new Portfolio(name, community).save();


    	redirect("/community/"+ portfolio.community.name + "/edit/"+portfolio.name);
    }

}