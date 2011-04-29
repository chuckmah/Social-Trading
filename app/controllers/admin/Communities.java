package controllers.admin;

import controllers.CRUD;
import controllers.CRUD.For;
import controllers.Check;
import controllers.Secure;
import models.Community;

import play.mvc.*;
import play.*;

@CRUD.For(Community.class)
@With(Secure.class)
@Check("admin")
public class Communities extends CRUD {

}
