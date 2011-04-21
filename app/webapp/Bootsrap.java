package webapp;

import java.io.InputStream;

import org.yaml.snakeyaml.Yaml;

import models.Community;
import play.Logger;
import play.Play;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;
import sun.util.logging.resources.logging;

@OnApplicationStart
public class Bootsrap extends Job {

	
    public void doJob() {
    	
        // Load test data if the database is empty
        if(Community.count() == 0) {
            String fileName = "data.yml";
            Fixtures.load(fileName);
       /*     InputStream is = Play.classloader.getResourceAsStream(fileName);
            if (is == null) {
                throw new RuntimeException("Cannot load fixture " + fileName + ", the file was not found");
            }
            Yaml yaml = new Yaml();

            
            for (Object data : yaml.loadAll(is)) {

                	Logger.info("importing yaml file community name = " + data);
                	Community community = (Community) data;
                	community.save();
                

            }*/
            
        }
    }
}
