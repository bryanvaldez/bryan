package pe.gob.onpe.rae.controller.mantenimiento;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pe.gob.onpe.rae.dao.ExpedienteDAO;
import pe.gob.onpe.rae.model.Expediente;

/**
 * 
 * @author Bryan Valdez <bvaldez at onpe.gob.pe>
 */
@Controller
@RequestMapping(value = "/mantenimiento/*")
public class mantenimientoController {        

    @Autowired
    ExpedienteDAO expedienteDAO;
    
    @RequestMapping("index")
    public String ambitoIndex(){                
        return "mantenimiento/ambito/index";
    }
    @RequestMapping("popup")
    public String crear(){        
        return "mantenimiento/ambito/ambitoForm";
    }
    @RequestMapping("popupEdit")
    public String editar(){        
        return "mantenimiento/ambito/ambitoView";
    }
    @RequestMapping("popupAuth")
    public String editarAuth(){        
        return "mantenimiento/ambito/autoridadForm";
    }
    
    @RequestMapping(value="updateExpediente", method = RequestMethod.GET)
    @ResponseBody
    public HttpStatus updateExpediente() {               
        expedienteDAO.updateExpediente();
        return HttpStatus.OK;
    }
}
