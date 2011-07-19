package models;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.apache.commons.lang.StringUtils;

import com.google.gson.JsonObject;

import play.Logger;
import play.data.validation.Required;
import play.db.jpa.Model;
import play.mvc.Scope.Session;

@Entity
public class User extends Model {

	
    @Required
    public String email;
    
    @Required
    public String password;
    
    @Required
    public String name;
    
    @Required
    public String userName;
    
    public boolean isAdmin;
    
    public boolean fbAuthentication;
    
    @OneToMany(cascade=CascadeType.ALL, mappedBy="user")
	public List<CommunityUser> communityUsers;
    
    public User(String email, String password, String fullname, String userName) {
        this.email = email;
        this.password = password;
        this.name = fullname;
        this.userName = userName;
        this.isAdmin = false;
        this.fbAuthentication = false;
    }
    
    public User(String email, String fullname, String userName) {
        this.email = email;
        this.name = fullname;
        this.userName = userName;
        this.isAdmin = false;
        this.fbAuthentication = true;
    }

    
    public String toString() {
        return email;
    }
    
    
    public static User connect(String email, String password) {
        return find("byEmailAndPassword", email, password).first();
    }

    public static void facebookOAuthCallback(JsonObject data){
    		String email = data.get("email").getAsString();
    		
    		User user = null;
    		if(!StringUtils.isEmpty(email)){
    			user = find("byEmail", email).first();
    			if(user == null){
    					//get the username if not present use the first_name for the username
    	    		 	String userName = data.get("username").getAsString();
    	    		 	if(StringUtils.isEmpty(userName)){
    	    		 		userName  = data.get("first_name").getAsString();
    	    		 	}
    		    	    String name = data.get("name").getAsString();
    		    	    
    		    	    user = new User(email,name,userName);
    		            user.create();
    			}
    		}
    		
            // Mark user as connected
    		Session.current().put("fbuseremail", user.email);

    		
    }

	public boolean isMember(String communityName) {
		
		if(isAdmin){
			return true;
		}
		
		for (CommunityUser communityUser : communityUsers) {
			if(communityUser.community.name.equals(communityName)){
				return true;
			}
		}
		
		return false;
	}
    
}
