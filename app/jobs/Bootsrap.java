package jobs;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.yaml.snakeyaml.Yaml;

import models.Community;
import play.Logger;
import play.Play;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;
import services.CommunityServices;
import services.PortofolioStatServices;
import services.QuoteServices;
import sun.util.logging.resources.logging;

@OnApplicationStart
public class Bootsrap extends Job {

	
    public void doJob() {
    	
        // Load test data if the database is empty
        if(Community.count() == 0) {
            String fileName = "data.yml";
            Fixtures.load(fileName);
 
            
        }
        
    	CommunityServices.updateCommunities();
    }
}
