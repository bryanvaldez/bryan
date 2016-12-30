package pe.gob.onpe.rae.controller.security.auth;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import pe.gob.onpe.rae.constant.Parametros;
import pe.gob.onpe.rae.dao.UsuarioDAO;
import pe.gob.onpe.rae.model.Usuario;

/**
 * 
 * @author Bryan Valdez <bvaldez at onpe.gob.pe>
 */
@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{

    @Autowired
    UsuarioDAO usuarioDAO;
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response, Authentication auth) throws IOException, ServletException{
        
        Usuario usuario = usuarioDAO.findByLogin(auth.getName());
   
        request.getSession().setAttribute("USUARIO_AUTENTICADO", usuario);
        
        if (usuario.getEstado() == Parametros.ESTADO_USUARIO_PENDIENTE) {
            this.setDefaultTargetUrl("/cambiar-clave");
            //request.getSession().setAttribute("CAMBIAR_PASSWORD", Parametros.ESTADO_USUARIO_INACTIVO);
        } else {
            this.setDefaultTargetUrl("/main");
        }
        
        super.onAuthenticationSuccess(request, response, auth);
    }
}
