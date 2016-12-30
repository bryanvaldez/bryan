package pe.gob.onpe.rae.controller.mantenimiento.local;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import pe.gob.onpe.rae.dao.AmbitoDAO;
import pe.gob.onpe.rae.dao.LocalDAO;
import pe.gob.onpe.rae.dao.UbigeoDAO;
import pe.gob.onpe.rae.model.Ambito;
import pe.gob.onpe.rae.model.Local;
import pe.gob.onpe.rae.model.UbigeoVw;
import pe.gob.onpe.rae.model.Usuario;

/**
 * 
 * @author Bryan Valdez <bvaldez at onpe.gob.pe>
 */
@Controller
@Transactional
@SessionAttributes("local")
@RequestMapping(value="/mantenimiento/local/*")
public class LocalController {

    @Autowired
    LocalDAO localDAO;
    
    @Autowired
    AmbitoDAO ambitoDAO;
    
    
    @RequestMapping("{id}")
    public String local(@PathVariable("id") Long id, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        session.setAttribute("ambito", id);         
        return "mantenimiento/local/index";
    }
    
    @RequestMapping("modal")
    public String create(Model model){
        model.addAttribute("local", new Local());        
        return "mantenimiento/local/form";
    }  
    
    @RequestMapping("modalChange")
    public String createChange(Model model){
        return "mantenimiento/local/formChange";
    }
    
    @RequestMapping(value="lista", method = RequestMethod.GET)
    public ResponseEntity<List<Local>> listarLocales(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        List<Local> locales = localDAO.findByAmbito(new Ambito(session.getAttribute("ambito")));        
        List<Local> lstLocales = new ArrayList();
        for (Local local : locales) {
            local.getAmbito().setExpedientes(null);
            local.getAmbito().setExpedientesPadron(null);
            local.getAmbito().setLocales(null);
            lstLocales.add(local);
        }
        if(locales.isEmpty()){
            return new ResponseEntity<List<Local>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Local>>(lstLocales, HttpStatus.OK);
    }  
    
    @RequestMapping(value="listaAmbitos", method = RequestMethod.GET)
    public ResponseEntity<List<Ambito>> listarAmbitos(HttpServletRequest request) {
        
        List<Ambito> ambitos = ambitoDAO.allAmbitoAnyPadre();
        List<Ambito> lstAmbitos = new ArrayList();
        for (Ambito ambito : ambitos) {
            ambito.setExpedientes(null);
            ambito.setExpedientesPadron(null);
            ambito.setLocales(null);
            lstAmbitos.add(ambito);
        }
        if(ambitos.isEmpty()){
            return new ResponseEntity<List<Ambito>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Ambito>>(lstAmbitos, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<Local> save(HttpServletRequest request, @RequestBody Local local) {
        HttpSession session = request.getSession(false);
        Ambito ambito = ambitoDAO.find(new Ambito(session.getAttribute("ambito")));        
        if(local.getId() == null){
            local.setAmbito(ambito);
            local.setDepartamento(ambito.getDepartamento());
            local.setProvincia(ambito.getProvincia());
            local.setDistrito(ambito.getDistrito());
            local.setUbigeo(ambito.getUbigeo());
            localDAO.saveDAO(local);
       }else{
           Local currentLocal = localDAO.find(local);
           if(currentLocal == null){
               return new ResponseEntity<Local>(HttpStatus.NOT_FOUND);
           }else{
               local.setAmbito(ambitoDAO.find(ambito));
               localDAO.updateDAO(local);
           }
       }

        return new ResponseEntity<Local>(HttpStatus.OK);
    }
    
    @RequestMapping(value="{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<Local> delete(@PathVariable("id") long id) {
        Local local = localDAO.find(new Local(id));
        if (local == null) {
            return new ResponseEntity<Local>(HttpStatus.NOT_FOUND);
        }
        localDAO.deleteDAO(local);
        return new ResponseEntity<Local>(HttpStatus.NO_CONTENT);

    }
    
    @RequestMapping("editar/{id}")
    public String editar(@PathVariable Long id, Model model){
        
        Local local = localDAO.find(new Local(id));
        model.addAttribute("usuario", local);
        return "mantenimiento/local/form";
    } 
    
    @RequestMapping(value = "search/{texto}", method = RequestMethod.GET)
    public ResponseEntity<List<Local>> searchLocal(@PathVariable String texto, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        List<Local> lstExpedientes = localDAO.allByAmbitoByText(new Ambito(session.getAttribute("ambito")), texto);                                
        return new ResponseEntity<List<Local>>(lstExpedientes, HttpStatus.OK);
    } 

    @RequestMapping(value = "searchLocales/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Local>> searchLocales(@PathVariable Long id) {        
        List<Local> locales = localDAO.allByLocal(id);
        List<Local> lstLocales = new ArrayList();
        for (Local local : locales) {
            local.getAmbito().setExpedientes(null);
            local.getAmbito().setExpedientesPadron(null);
            local.getAmbito().setLocales(null);
            lstLocales.add(local);
        }
        if (locales.isEmpty()) {
            return new ResponseEntity<List<Local>>(HttpStatus.NO_CONTENT);
        }                        
        return new ResponseEntity<List<Local>>(lstLocales, HttpStatus.OK);
    }   
    
//    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
//    @ResponseBody
//    public ResponseEntity<Ambito> change(HttpServletRequest request, @RequestBody int id) {
//                
//        if (id != 0) {
//            System.out.println(id);
//        }        
//
//        return new ResponseEntity<Ambito>(HttpStatus.OK);
//    }
    
    @RequestMapping(value = "changeAmbito", method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<Ambito> save(HttpServletRequest request, @RequestBody String json) {
                
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(json);
        Long idAmbito = jsonObject.get("id").getAsLong();
        Long idLocal = jsonObject.get("idLocal").getAsLong();
        
        Local local = new Local();
        local.setId(idLocal);
        local.setAmbito(new Ambito(idAmbito));
                
        localDAO.updateLocalById(local);
        
        return new ResponseEntity<Ambito>(HttpStatus.OK);
    }
    
}
