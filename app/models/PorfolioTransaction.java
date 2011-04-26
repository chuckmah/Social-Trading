package models;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import play.data.validation.Required;
import play.db.jpa.Model;


@Entity
public class PorfolioTransaction extends Model {
	
	@ManyToOne
	public Portfolio portfolio;
	
	@Required
	@ManyToOne
	public CommunityQuote communityQuote;
	

	
	@Required
	public int qty;
	
	@Required
	public BigDecimal unitPrice;
	
	@Required
	public Date executionDate;
	
	@Required
	public String type;
	
	public PorfolioTransaction(){
		
	}
	
	
	public PorfolioTransaction(Portfolio portfolio, CommunityQuote communityQuote ,BigDecimal unitPrice,int qty,  Date executionDate, String type){
		this.portfolio = portfolio;
		this.communityQuote = communityQuote;
		this.qty = qty;
		this.unitPrice = unitPrice;
		this.executionDate = executionDate;
		this.type = type;
	}
	
	public BigDecimal getAmount(){
		return unitPrice.multiply(new BigDecimal(qty));
	}
	
}
