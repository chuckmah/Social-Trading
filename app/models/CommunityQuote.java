package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Query;

import play.data.validation.Required;
import play.db.jpa.Model;


@Entity
public class CommunityQuote  extends Model {

	@OneToOne
	@Required
	public Quote quote;

    @ManyToOne(fetch=FetchType.LAZY)
    @Required
    public Community community;
	
    @OneToMany(cascade=CascadeType.ALL, mappedBy="communityQuote")
    public List<PortfolioEntry> portfolioEntries;
	
    @OneToMany(cascade=CascadeType.ALL, mappedBy="communityQuote")
    public List<PorfolioTransaction> portfolioTransactions;

	public CommunityQuote(Quote quote, Community community) {
		this.quote = quote;
		this.community = community;
	}

	public static CommunityQuote findBySymbolAndCommunityId(String symbol, long communityId) {
	
		return CommunityQuote.find("select c from CommunityQuote c where c.quote.symbol = ? and c.community.id=?", symbol, communityId).first();
	}
    

/*	public static int findNumberOfoccurences(Quote quote){
		EntityManager em = em();
		
		Query query=em.createQuery("SELECT COUNT(p.id) FROM Porfolio p, Quote q WHERE p.");
		
		return 0;
		
	}
	*/
}
