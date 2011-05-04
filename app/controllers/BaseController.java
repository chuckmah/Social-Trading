package controllers;

import org.apache.commons.lang.StringUtils;

import models.Community;
import models.User;
import play.mvc.Before;
import play.mvc.Controller;

public class BaseController extends Controller {

    @Before()
    static void setConnectedUser() {
        if(Security.isConnected()) {
            User user = User.find("byEmail", Security.connected()).first();
            renderArgs.put("user", user);
        }
    }

    static void checkmembership(String communityName) {
 	   
	    User user =  (User) renderArgs.get("user");
	    
	    if(!user.isMember(communityName)){
	    	Community community = null ;
	    	if (!StringUtils.isEmpty(communityName)){
	    		community =	Community.findByName(communityName);
	    	}
	    	
	    renderTemplate("CommunityController/notMember.html",community);
	  
	    }
	    
    }
}
