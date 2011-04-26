package services;

import java.util.Date;
import java.util.List;

import models.Community;

public class CommunityServices {

	
	public static void updateCommunities(){
    	//Refresh all quotes and stats for this community
    	QuoteServices.refreshAllQuotes();
    	
    	
       	List<Community> communities = Community.find("order by id desc").fetch();
       	for (Community community : communities) {
			PortofolioStatServices.updateCommunityStat(community);
			community.lastUpdate = new Date();
			community.save();
		}
	}
}
