package models;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.data.validation.Required;
import play.db.jpa.Model;

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
    
    
    @OneToMany(cascade=CascadeType.ALL, mappedBy="user")
	public List<CommunityUser> communityUsers;
    
    public User(String email, String password, String fullname, String userName) {
        this.email = email;
        this.password = password;
        this.name = fullname;
        this.userName = userName;
        this.isAdmin = false;
    }
    

    
    public String toString() {
        return email;
    }
    
    
    public static User connect(String email, String password) {
        return find("byEmailAndPassword", email, password).first();
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
