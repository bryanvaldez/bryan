package pe.gob.onpe.rae.controller.security.auth;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import pe.gob.onpe.rae.constant.Parametros;
import pe.gob.onpe.rae.controller.main.MainController;

/**
 * Redirect user to change onetime password.
 */
public class ResetPasswordInterceptor extends HandlerInterceptorAdapter {
    public boolean preHandle(HttpServletRequest request,
			     HttpServletResponse response,
			     Object handler) throws ServletException {
        
	if (request.getSession().getAttribute("USUARIO_AUTENTICADO") != null) { // only intercept my controller, don't mess with other controllers.
	    if (request.getSession().getAttribute("CAMBIAR_PASSWORD") != null) {
		    //redirect(request, response, "/cambiar-clave");
	    }
	}
	return true;
    }

    private void redirect(HttpServletRequest request, 
			  HttpServletResponse response, 
			  String path) throws ServletException {
	    try {
		response.sendRedirect(request.getContextPath() + path);
	    }
	    catch (java.io.IOException e) {
		throw new ServletException(e);
	    }
    }

    private boolean isOnetimePassword() {
	return true;
    }
}