package models;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.data.validation.Required;
import play.db.jpa.Model;


@Entity
public class PorfolioTransaction extends Model {
	
	@ManyToOne
	public Portfolio portfolio;
	
	@Required
	@ManyToOne
	public Quote quote;
	
	@Required
	public BigDecimal amount;
	
	@Required
	public Date executionDate;
	
	@Required
	public String type;
	
	public PorfolioTransaction(){
		
	}
	
	public PorfolioTransaction(Portfolio portfolio, Quote quote, BigDecimal amount , Date executionDate, String type){
		this.portfolio = portfolio;
		this.quote = quote;
		this.amount = amount;
		this.executionDate = executionDate;
		this.type = type;
	}
	
}
