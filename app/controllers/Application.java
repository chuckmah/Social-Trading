package controllers;

import java.util.List;

import models.Community;
import play.mvc.Controller;

public class Application extends Controller {

    public static void index() {
       	List communities = Community.find("order by id desc").fetch();
        render(communities);
    }
    
}
