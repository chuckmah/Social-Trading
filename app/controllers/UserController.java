package controllers;

import models.User;
import play.data.validation.Valid;
import play.mvc.With;

@With(Secure.class)
public class UserController extends BaseController {

	
	public static void index() {
		render();
	}
	
	

}
