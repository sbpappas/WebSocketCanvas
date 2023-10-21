package controllers

import javax.inject._
import play.api.mvc._
import play.api.i18n._
import java.lang.ProcessBuilder.Redirect

@Singleton
class WebController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def draw = Action { implicit request =>
    Ok(views.html.draw())
  }


}