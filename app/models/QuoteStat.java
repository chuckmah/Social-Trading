package models;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.OneToOne;
import javax.persistence.Query;

import play.db.jpa.Model;


@Entity
public class QuoteStat  extends Model {

	@OneToOne
	public Quote quote;
	
	public int totalOccurence;
	
/*	public static int findNumberOfoccurences(Quote quote){
		EntityManager em = em();
		
		Query query=em.createQuery("SELECT COUNT(p.id) FROM Porfolio p, Quote q WHERE p.");
		
		return 0;
		
	}
	*/
}
