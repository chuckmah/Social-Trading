package controllers.admin;


import play.mvc.With;
import models.Portfolio;
import controllers.CRUD;
import controllers.Check;
import controllers.Secure;

@CRUD.For(Portfolio.class)
@With(Secure.class)
@Check("admin")
public class Portfolios extends CRUD {

}
