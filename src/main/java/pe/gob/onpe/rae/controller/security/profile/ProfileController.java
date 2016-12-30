package pe.gob.onpe.rae.controller.security.profile;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pe.gob.onpe.rae.dao.PerfilDAO;
import pe.gob.onpe.rae.model.Perfil;
import pe.gob.onpe.rae.model.Usuario;

/**
 * 
 * @author Bryan Valdez <bvaldez at onpe.gob.pe>
 */
@Controller
@RequestMapping("seguridad/perfil/")
public class ProfileController {
    @Autowired
    PerfilDAO perfilDAO;
    
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Perfil>> listAllProfile() {
        List<Perfil> profiles = perfilDAO.all();
        List<Perfil> lstProfiles = new ArrayList();
        for (Perfil profile : profiles) {
            profile.setUsuarios(null);
            profile.setOpciones(null);
            lstProfiles.add(profile);
        }
        
        if(profiles.isEmpty()){
            return new ResponseEntity<List<Perfil>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Perfil>>(lstProfiles, HttpStatus.OK);
    }    
}
