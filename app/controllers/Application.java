package controllers;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.yaml.snakeyaml.Yaml;


import models.Community;
import models.User;
import play.data.validation.Valid;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;
import controllers.Secure;
import controllers.Secure.Security;

@With(Secure.class)
public class Application extends BaseController {

    public static void index() {
        render();
    }
    
    public static void communitylist() {

	   	List communities = Community.find("order by id desc").fetch();
	    render(communities);
    }


    /**
     * Render the YAML of a community
     * 
     * @param communityName
     */
    @Check("admin")
    public static void yaml(String communityName) {
    	

           	Community community = Community.findByName(communityName);
            Yaml yaml = new Yaml();
            String output = yaml.dump(community);
            renderBinary(new ByteArrayInputStream(output.getBytes()));
    	
    }

    

    
    
}
