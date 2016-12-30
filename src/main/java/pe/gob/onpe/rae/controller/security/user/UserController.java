package pe.gob.onpe.rae.controller.security.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import pe.gob.onpe.rae.constant.Parametros;
import pe.gob.onpe.rae.dao.PadronDAO;
import pe.gob.onpe.rae.dao.PerfilDAO;
import pe.gob.onpe.rae.dao.UsuarioDAO;
import pe.gob.onpe.rae.model.Padron;
import pe.gob.onpe.rae.model.Perfil;
import pe.gob.onpe.rae.model.Usuario;

/**
 * 
 * @author Bryan Valdez <bvaldez at onpe.gob.pe>
 */
@Controller
@Transactional
@SessionAttributes("usuario")
@RequestMapping(value ="/seguridad/user/*")
public class UserController {
    
    @Autowired
    UsuarioDAO usuarioDAO;
    
    @Autowired
    PerfilDAO perfilDAO;

    @Autowired
    PadronDAO padronDAO;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Usuario>> listAllUsers() {
        List<Usuario> users = usuarioDAO.all();
        List<Usuario> lstUsers = new ArrayList();
        
        for (Usuario user : users) {
            Perfil perfil = new Perfil();
            perfil.setId(user.getPerfil().getId());
            perfil.setNombre(user.getPerfil().getNombre());
            perfil.setEstado(user.getPerfil().getEstado());
            user.setPerfil(perfil);
            lstUsers.add(user);
        }
        
        if(users.isEmpty()){
            return new ResponseEntity<List<Usuario>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Usuario>>(lstUsers, HttpStatus.OK);
    }
 
    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> save(@RequestBody Usuario user) {
        Map<String, Object> message = new HashMap<String, Object>();
        if(user.getId() == null){
            if(usuarioDAO.findByLogin(user.getUsuario()) != null){
                message.put("success", false);
                message.put("message", "Usuario ya existe.");
                return new ResponseEntity<Map<String, Object>>(message,HttpStatus.OK);
            }            
            user.setClave(user.getUsuario());
            user.setEstado(Parametros.ESTADO_USUARIO_PENDIENTE);
            usuarioDAO.saveDAO(user);
        } else {
            Usuario currentUser = usuarioDAO.findById(user.getId());
            if (currentUser==null) {
                message.put("success", false);
                message.put("message", "Usuario no existe.");
                return new ResponseEntity<Map<String, Object>>(message,HttpStatus.OK);
            }
            if(user.getPerfil().getId() == Parametros.PERFIL_ADMIN || user.getPerfil().getId() == Parametros.PERFIL_ONPE){
                user.setFechaInicial(null);
                user.setFechaFinal(null);
                user.setUbigeo(null);
            }
            if(user.getEstado() == Parametros.ESTADO_USUARIO_PENDIENTE){
                user.setClave(user.getUsuario());
            }
            user.setPerfil(perfilDAO.find(user.getPerfil()));
            usuarioDAO.updateDAO(user);
        }
        message.put("success", true);
        message.put("message", "Usuario creado.");        
        return new ResponseEntity<Map<String, Object>>(message,HttpStatus.OK);
    }    
      
    @RequestMapping(value= "{dni}", method = RequestMethod.GET)
    public ResponseEntity<Usuario> getDni(@PathVariable String dni){
        
        Padron padron = new Padron();
        padron.setNumEle(dni);
        Padron currentUser = padronDAO.find(padron);
        
        if(currentUser == null){            
            return new ResponseEntity<Usuario>(HttpStatus.NO_CONTENT);
        }
        
        Usuario user = new Usuario();
        user.setUsuario(currentUser.getNumEle());
        user.setNombre(currentUser.getNombres());
        user.setApellidoPaterno(currentUser.getApePat());
        user.setApellidoMaterno(currentUser.getApMat());                
        
        return new ResponseEntity<Usuario>(user,HttpStatus.OK);
    }   
           
}
