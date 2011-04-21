package models;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ManyToAny;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class PortfolioEntry extends Model {

	
	@ManyToOne(fetch=FetchType.LAZY)
	public Portfolio portfolio;
	
	@Required
	@ManyToOne
	public Quote quote;
    
    public int shareQty;

    public BigDecimal averagePrice;
    
    public PortfolioEntry (){
    	
    }
    
    public PortfolioEntry (Quote quote, Portfolio portfolio){
    	this.portfolio = portfolio;
    	this.quote = quote;
    }
    
    public static List<PortfolioEntry> findByQuote(Quote quote){
    	return Portfolio.find("select p from PortfolioEntry p where p.quote.id= ?", quote.id).fetch();

    }
    

}
