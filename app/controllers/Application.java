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

public class Application extends BaseController {


    
    public static void index() {

        render();
    }
    
    public static void communitylist() {
	   	List communities = Community.find("order by id desc").fetch();
	    render(communities);
    }
    
    
	public static void register() {
        flash.keep("url");
		render();
	}
	
    public static void saveUser(@Valid User user, String verifyPassword) {
  /*      validation.required(verifyPassword);
        validation.equals(verifyPassword, user.password).message("Your password doesn't match");
        if(validation.hasErrors()) {
            render("@register", user, verifyPassword);
        }
        user.create();
        session.put("user", user);
        try {
			Secure.redirectToOriginalURL();
		} catch (Throwable e) {

			e.printStackTrace();
		}*/
    }
    
    /**
     * Render the YAML of a community
     * 
     * @param communityName
     */
    public static void yaml(String communityName) {
       	Community community = Community.findByName(communityName);
        Yaml yaml = new Yaml();
        String output = yaml.dump(community);
        renderBinary(new ByteArrayInputStream(output.getBytes()));
    }
    
    
}
