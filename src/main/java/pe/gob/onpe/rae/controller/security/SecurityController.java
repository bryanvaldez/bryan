package pe.gob.onpe.rae.controller.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 
 * @author Bryan Valdez <bvaldez at onpe.gob.pe>
 */
@Controller
@RequestMapping(value = "/seguridad/*")
public class SecurityController {
    

    @RequestMapping("usuarios")
    public String index(){
        return "security/usuario/index";
    }
    
    @RequestMapping("usuario/editar")
    public String editar(){        
        return "security/usuario/usuarioForm";
    }    
             
    
}
