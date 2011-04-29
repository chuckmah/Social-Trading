package controllers.admin;

import play.mvc.With;
import models.User;
import controllers.CRUD;
import controllers.Secure;
import controllers.Check;

@CRUD.For(User.class)
@With(Secure.class)
@Check("admin")
public class Users extends CRUD{

}
