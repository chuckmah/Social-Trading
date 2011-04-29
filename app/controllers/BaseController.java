package controllers;

import models.User;
import play.mvc.Before;
import play.mvc.Controller;

public class BaseController extends Controller {

    @Before
    static void setConnectedUser() {
        if(Security.isConnected()) {
            User user = User.find("byEmail", Security.connected()).first();
            renderArgs.put("user", user);
         
        }
    }
	
}
