package models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Portfolio extends Model {

	
    @Required
    @OneToOne
	public CommunityUser communityUser;
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable 
    (
    	name="watchQuotes",    	
        joinColumns={ @JoinColumn(name="portofolioId") },
    	inverseJoinColumns={ @JoinColumn(name="quoteId") }

    )
	public List<Quote> watchQuotes;
	

    @OneToMany(cascade = CascadeType.ALL, mappedBy="portfolio")
    public List<PortfolioEntry> portfolioEntries;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy="portfolio")
    public List<PorfolioTransaction> transactions;
    
    @OneToOne(cascade = CascadeType.ALL, mappedBy="portfolio", fetch=FetchType.LAZY)
    public PortfolioStat stats;
    
    @Required
    public BigDecimal balance;
    
    public Date creationDate;
    
    public Portfolio(){
    	PortfolioStat stats = new PortfolioStat(this, 0, 0);
    	this.stats = stats;
    	
    	this.watchQuotes = new ArrayList<Quote>();
    	this.transactions = new ArrayList<PorfolioTransaction>();
    	this.portfolioEntries = new ArrayList<PortfolioEntry>();
    }
    
    public Portfolio (CommunityUser communityUser, BigDecimal startingBalance){
    	this();
    	this.communityUser = communityUser;
    	this.balance = startingBalance;
    	this.creationDate = new Date();



    }
    
    public String toString()  {
        return "Name(" + communityUser.user.userName + ")";
    }

	public static Portfolio findByCommunityAndUserName(String communityName,
			String alias) {

		return Portfolio.find("select p from Portfolio p where p.communityUser.user.userName= ? and p.communityUser.community.name=? ", alias,communityName).first();
	}
	
	/**
	 * 
	 * @param quoteToWatch
	 * @return true if the quote was added, false if symbol already exist in collection.
	 */
	public boolean addQuoteToWatch(Quote quoteToWatch) {
		
		for (Quote quote : watchQuotes) {
			if(quote.symbol.equals(quoteToWatch.symbol)){
				return false;
			}
		}
		
		watchQuotes.add(quoteToWatch);
		
		return true;
	}
	
	public void addTransaction(PorfolioTransaction transaction){

		
		transactions.add(transaction);
	}
	
	public PortfolioEntry getPortfolioEntryBySymbol(String quote){
		PortfolioEntry position = null;
		
		for(PortfolioEntry existingPositions : portfolioEntries) {
			if(existingPositions.communityQuote.quote.symbol.equals(quote)){
				position = existingPositions;
			}
			
		}
		return position;
	}
	
	public boolean addPortfolioEntry(PortfolioEntry position){

		for (PortfolioEntry existingPositions : portfolioEntries) {
			if(existingPositions.communityQuote.quote.symbol.equals(position.communityQuote.quote.symbol)){
				return false;
			}
		}
		
		portfolioEntries.add(position);
		return true;
	}

	public boolean removePortfolioEntry(Long id) {
		
		PortfolioEntry entryToDelete = null;

		int index  = 0;
		int indexToDelete = -1;
		for (PortfolioEntry existingPosition : portfolioEntries) {
			if(existingPosition.id.equals(id)){
				entryToDelete = existingPosition;
				indexToDelete = index;
			}
			index++;
		}
		if(entryToDelete != null){
			portfolioEntries.remove(indexToDelete);
			entryToDelete.delete();
			return true;
		}
		return false;


	}



}
