package models;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
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
	
    @OneToMany(cascade=CascadeType.ALL, mappedBy="community")
    public List<Comment> comments;
    
    public static Community findByName(String name){
    	return Community.find("select c from Community c where c.name = ?"  , name).first();
    }
    
    public String toString()  {
        return "Community(" + name + ")";
    }
}
