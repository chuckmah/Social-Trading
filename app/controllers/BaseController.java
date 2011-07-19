package controllers;

import org.apache.commons.lang.StringUtils;

import models.Community;
import models.User;
import play.Logger;
import play.mvc.Before;
import play.mvc.Controller;

public class BaseController extends Controller {

    @Before()
    static void setConnectedUser() {
        if(Security.isConnected()) {
            User user = User.find("byEmail", Security.connected()).first();
            if(user == null){
            	//huh? a user is connected but not found log out immediately      
            	
            	
            	try {
					Secure.logout();
				} catch (Throwable e) {
					e.printStackTrace();
				}
            }
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
