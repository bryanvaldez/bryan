package pe.gob.onpe.rae.controller.verificar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import pe.gob.onpe.rae.dao.AmbitoDAO;
import pe.gob.onpe.rae.dao.ExpedienteDAO;
import pe.gob.onpe.rae.dao.ExpedientePadronDAO;
import pe.gob.onpe.rae.dao.ParametroDAO;
import pe.gob.onpe.rae.model.Ambito;
import pe.gob.onpe.rae.model.Expediente;
import pe.gob.onpe.rae.model.ExpedientePadron;
import pe.gob.onpe.rae.model.Parametro;

/**
 *
 * @author Manuel Arrisueno <marrisueno at onpe.gob.pe>
 */
@Controller
@Transactional
@RequestMapping("/verificar/*")
public class VerificarController {        
    
    @Autowired
    AmbitoDAO ambitoDAO;
    
    @Autowired
    ExpedienteDAO expedienteDAO;
    
    @Autowired
    ExpedientePadronDAO expedientePadronDAO; 
    
    @Autowired
    ParametroDAO parametroDAO; 
    
    @RequestMapping("index")
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView view;
        view = new ModelAndView("verificar/index");        
        view.addObject("dptos", new Gson().toJson(ambitoDAO.listarDepartamento()));
        return view;
    }
        
//    @RequestMapping("verificar/{id}")
//    public String verificar(@PathVariable("id") Long id, HttpServletRequest request) {                        
//        return "verificar/verificar";
//    }
          
    @RequestMapping(value = "expediente/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String infoExpediente(HttpServletRequest request, @PathVariable("id") int id) {                
        
        Map<String, Object> map = new HashMap<String, Object>();
        GsonBuilder builder= new GsonBuilder();
        
        Expediente expediente = expedienteDAO.find(new Expediente(id));
        expediente.getAmbito().setExpedientes(null);
        expediente.getAmbito().setExpedientesPadron(null);
        expediente.getAmbito().setLocales(null);            
        expediente.setExpedientesImpresion(null);
        expediente.setExpedientesPadron(null);
            
        List<ExpedientePadron> exist = expedientePadronDAO.allPendientes(new Expediente(id));
        if (exist.isEmpty()) {
            map.put("result", HttpStatus.NO_CONTENT);
            map.put("expediente", "");
            map.put("electores", "");            
            return builder.create().toJson(map);
        }              
        
        List<ExpedientePadron> electores = expedientePadronDAO.allPendientes(new Expediente(id));        
        List<ExpedientePadron> lstElectores = new ArrayList();
        for (ExpedientePadron elector : electores) {            
            elector.getExpediente().setExpedientesImpresion(null);
            elector.getExpediente().setExpedientesPadron(null);                     
            
            if (elector.getAmbito().getTipoAmbito() == 2) {
                Ambito ambito = ambitoDAO.findById(elector.getAmbito().getId());
                elector.setAmbito(ambito);
            }
                        
            lstElectores.add(elector);
        }
                
        //List<Parametro> opciones = parametroDAO.findByType(2);
        List<Parametro> opciones = new ArrayList<Parametro>();
        Parametro p = new Parametro();
        p.setId(Long.parseLong("17"));
        p.setDescripcion("DNI No Corresponde al nombre = NC");
        p.setCodigo(6);
        p.setOrden(6);
        p.setFlag(2);
        p.setTipo(2);
        opciones.add(p);
        
        p = new Parametro();
        p.setId(Long.parseLong("18"));
        p.setDescripcion("No consignó DNI = ND");
        p.setCodigo(7);
        p.setOrden(7);
        p.setFlag(2);
        p.setTipo(2);
        opciones.add(p);
        
        p = new Parametro();
        p.setId(Long.parseLong("19"));
        p.setDescripcion("No consignó Firma / huella = NF");
        p.setCodigo(8);
        p.setOrden(8);
        p.setFlag(2);
        p.setTipo(2);
        opciones.add(p);
        
        map.put("result", HttpStatus.OK);
        map.put("expediente", expediente);
        map.put("electores", lstElectores);
        map.put("opciones", opciones);
                
        return builder.create().toJson(map);
    }
    
    @RequestMapping(value = "guardar/", method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public String save(@RequestBody String list) {                               
        Map<String, Object> map = new HashMap<String, Object>();
        GsonBuilder builder = new GsonBuilder();
        
        Gson g = new Gson();
        JsonParser jsonParser = new JsonParser();        
        try {
            JsonArray array = (JsonArray) jsonParser.parse(list);
            for (JsonElement electores : array) {                
                ExpedientePadron elector = g.fromJson(electores, ExpedientePadron.class);
                if (elector.getObservacion().contains("6") || elector.getObservacion().contains("7") || elector.getObservacion().contains("8")) {
                    elector.setEstado(3);
                }                
                elector.setFlag(0);
                expedientePadronDAO.updateDAO(elector);
            }            
            map.put("result", HttpStatus.OK);          
        } catch (Exception ex) {
            map.put("result", HttpStatus.NOT_FOUND);
        }
        
        return builder.create().toJson(map);
    }
    
}
