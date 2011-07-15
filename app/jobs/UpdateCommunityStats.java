package jobs;


import java.util.Date;
import java.util.List;

import models.Community;
import play.jobs.Every;
import play.jobs.Job;
import services.CommunityServices;
import services.PortofolioStatServices;
import services.QuoteServices;

@Every("5min")
public class UpdateCommunityStats extends Job{

	public void doJob() {

	    	CommunityServices.updateCommunities();

	}
}
