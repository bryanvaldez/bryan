package pe.gob.onpe.rae.controller;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import pe.gob.onpe.rae.dao.ParametroDAO;
import pe.gob.onpe.rae.helper.TypesUtil;
import pe.gob.onpe.rae.model.Parametro;

/**
 * 
 * @author Bryan Valdez <bvaldez at onpe.gob.pe>
 */
public class BaseController {
    
    @Autowired
    private ParametroDAO parametroDAO;
    
    @Autowired
    private MessageSource messageSource;
    
    Logger log = Logger.getLogger(this.getClass().getName());
    
    protected String getMessageInternal(String code) {
        Locale locale = new Locale("es");
        return messageSource.getMessage(code, null, locale);
    }
    
    protected Object getAttributeSession(HttpServletRequest request, String attribute){
        HttpSession session = request.getSession(false);
        if (session != null) {
            return session.getAttribute(attribute);
        } else {
            return null;
        }    
    }
    protected Parametro getNParametro(Parametro param,int n_parametro_pk){
        if (param == null) {            
            param = parametroDAO.getParametro(n_parametro_pk);
        }
        return param;
    }
    
}
