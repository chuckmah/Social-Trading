package models;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.UniqueConstraint;

import play.data.validation.Required;
import play.db.jpa.Model;


@Entity
public class Quote extends Model {


	@Required
	public String symbol;
	
	public String companyName;
	
	public BigDecimal marketPrice;
	
    @OneToOne(cascade = CascadeType.ALL, mappedBy="quote", fetch=FetchType.LAZY)
    public QuoteStat stats;
	
	public Quote(){
	}
	
	public Quote(String symbol){
		this.symbol = symbol;

	}
	
    public String toString()  {
        return "Symbol(" + symbol + ")";
    }
	
	public static Quote findBySymbol(String symbol) {
		return Portfolio.find("select q from Quote q where q.symbol= ?", symbol).first();
	}
	

	

}
