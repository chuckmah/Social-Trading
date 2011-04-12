package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Portfolio extends Model {

    @Required
	public String name;
    
    @ManyToOne
    public Community community;
    
    @OneToMany(cascade=CascadeType.ALL, mappedBy="portfolio")
	public List<PortfolioEntry> potfoliosEntries;
	
    public Portfolio (String name, Community community){
    	this.name = name;
    	this.community = community;
    }
    
    public String toString()  {
        return "Name(" + name + ")";
    }

	public static Portfolio findByCommunityAndName(String communityName,
			String name) {

		return Portfolio.find("select p from Portfolio p where p.name= ? and p.community.name=? ", name,communityName).first();
	}
}
