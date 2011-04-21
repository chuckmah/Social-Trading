package controllers;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.yaml.snakeyaml.Yaml;


import models.Community;
import play.mvc.Controller;

public class Application extends Controller {

    public static void index() {
       	List communities = Community.find("order by id desc").fetch();
        render(communities);
    }
    
    public static void yaml(String communityName) {
       	Community community = Community.findByName(communityName);
        Yaml yaml = new Yaml();

        String output = yaml.dump(community);

        renderBinary(new ByteArrayInputStream(output.getBytes()));
    }
    
    
}
