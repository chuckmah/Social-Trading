package models;

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
	
    @OneToMany(cascade=CascadeType.ALL, mappedBy="community")
	public List<Portfolio> portfolios;
	
    
    public static Community findByName(String name){
    	return Community.find("select c from Community c where c.name = ?"  , name).first();
    }
    
    public String toString()  {
        return "Name(" + name + ")";
    }
}
