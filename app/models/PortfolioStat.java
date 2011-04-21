package models;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class PortfolioStat extends Model {

	@OneToOne
	public Portfolio portfolio;
	
	
	public BigDecimal marketValue;
	
	public String marketValueChange;
	
	public BigDecimal PreviousDayMarketValue;
	
	public String previousDayMarketValueChange;
	
	public BigDecimal PreviousWeekMarketValue;
	
	public String previousWeekMarketValueChange;
	
	public Integer entriesTotal;
	
	public Integer transactionTotal;
}
