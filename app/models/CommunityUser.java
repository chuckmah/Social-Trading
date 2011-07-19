package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class CommunityUser extends Model{

    @Required
    @ManyToOne(fetch=FetchType.LAZY)
    public Community community;
	

    @OneToOne(cascade = CascadeType.ALL, mappedBy="communityUser")
    public Portfolio portfolio; 
    
    @Required 
    @ManyToOne
    public User user;
	
}
