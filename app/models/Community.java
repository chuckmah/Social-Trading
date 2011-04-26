package models;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.UniqueConstraint;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Community extends Model{

	@Required
	public String name;
	
	@Required
	public BigDecimal startingBalance;
	
    @OneToMany(cascade=CascadeType.ALL, mappedBy="community")
	public List<Portfolio> portfolios;
	
    public Date lastUpdate;
    
//    @OneToMany(cascade=CascadeType.ALL, mappedBy="community")
//    public List<Comment> comments;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy="community", fetch=FetchType.LAZY)
    public  List<CommunityQuote> communityQuotes;
    
    public static Community findByName(String name){
    	return Community.find("select c from Community c where c.name = ?"  , name).first();
    }
    
    public String toString()  {
        return "Community(" + name + ")";
    }
}
