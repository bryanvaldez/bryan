package pe.gob.onpe.rae.controller.buscar;


import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pe.gob.onpe.rae.dao.PadronDAO;
import pe.gob.onpe.rae.model.Padron;

/**
 *
 * @author Manuel Arrisueno <marrisueno at onpe.gob.pe>
 */
@Controller
@Transactional
@RequestMapping(value = "/buscarElector/*")
public class BuscarController {

    
    @Autowired
    PadronDAO padronDAO;
    

    @RequestMapping("modal")
    public String create() {        
        return "buscar/form";
    }
    
    @RequestMapping("modal2")
    public String create2() {        
        return "carga/form";
    }    

    @RequestMapping(value = "buscar", method = RequestMethod.POST, headers = "Accept=application/json")    
    @ResponseBody
    public ResponseEntity<List<Padron>> buscar(HttpServletRequest request, @RequestBody String p) {                
        
        Gson g = new Gson();
        Padron padron = g.fromJson(p, Padron.class);
        
        List<Padron> lstPadron = new ArrayList<Padron>();
        if (!padron.getNumEle().equals("")) {
            lstPadron = padronDAO.findByDNI(padron.getNumEle());
        } else {
            lstPadron = padronDAO.findByPadron(padron);                       
        }
        return new ResponseEntity<List<Padron>>(lstPadron, HttpStatus.OK);
    }
    
}
