package pe.gob.onpe.rae.controller.mantenimiento.expediente;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import pe.gob.onpe.rae.dao.AmbitoDAO;
import pe.gob.onpe.rae.dao.ExpedienteDAO;
import pe.gob.onpe.rae.dao.ExpedientePadronDAO;
import pe.gob.onpe.rae.dao.UbigeoDAO;
import pe.gob.onpe.rae.model.Ambito;
import pe.gob.onpe.rae.model.Expediente;
import pe.gob.onpe.rae.model.ExpedientePadron;
import pe.gob.onpe.rae.model.json.JAmbito;
import pe.gob.onpe.rae.model.json.JCategoria;
import pe.gob.onpe.rae.model.json.JExpediente;

/**
 *
 * @author Manuel Arrisueno <marrisueno at onpe.gob.pe>
 */
@Controller
@Transactional
@SessionAttributes("expediente")
@RequestMapping(value = "/mantenimiento/expediente/*")
public class ExpedienteController {

    @Autowired
    ExpedienteDAO expedienteDAO;

    @Autowired
    ExpedientePadronDAO expedientePadronDAO;

    @Autowired
    UbigeoDAO ubigeoDAO;

    @Autowired
    AmbitoDAO ambitoDAO;

    @RequestMapping("{id}")
    public String expediente(@PathVariable("id") Long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.setAttribute("ambito", id);
        return "mantenimiento/expediente/index";
    }

    @RequestMapping("modal")
    public String create(Model model) {
        model.addAttribute("expediente", new Expediente());
        return "mantenimiento/expediente/form";
    }

    @RequestMapping(value = "lista", method = RequestMethod.GET)
    public ResponseEntity<List<Expediente>> listarExpediente(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        List<Expediente> expedientes = expedienteDAO.allByAmbito(new Ambito(session.getAttribute("ambito")));
        List<Expediente> lstExpedientes = new ArrayList();
        for (Expediente expediente : expedientes) {
            expediente.getAmbito().setExpedientes(null);
            expediente.getAmbito().setExpedientesPadron(null);
            expediente.getAmbito().setLocales(null);
            //expediente.setElectores(null);
            expediente.setExpedientesImpresion(null);
            expediente.setExpedientesPadron(null);
            lstExpedientes.add(expediente);
        }
        if (expedientes.isEmpty()) {
            return new ResponseEntity<List<Expediente>>(HttpStatus.NO_CONTENT);
        }                        
        return new ResponseEntity<List<Expediente>>(lstExpedientes, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<Expediente> save(HttpServletRequest request, @RequestBody Expediente expediente) {
        HttpSession session = request.getSession(false);
        Ambito ambito = ambitoDAO.find(new Ambito(session.getAttribute("ambito")));
        if (expediente.getId() == null) {
            expediente.setAmbito(ambito);
            expedienteDAO.saveDAO(expediente);
        } else {
            Expediente currentExpediente = expedienteDAO.find(expediente);
            if (currentExpediente == null) {
                return new ResponseEntity<Expediente>(HttpStatus.NOT_FOUND);
            } else {
                expediente.setAmbito(ambitoDAO.find(ambito));
                expedienteDAO.updateDAO(expediente);
            }
        }

        return new ResponseEntity<Expediente>(HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    @ResponseBody
    public HttpStatus delete(@PathVariable("id") long id) {        
        Expediente expediente = expedienteDAO.find(new Expediente(id));
        List<ExpedientePadron> lstExpedientePadron = expedientePadronDAO.all(expediente);
        if (lstExpedientePadron.isEmpty()) {            
            if (expediente == null) {
                return HttpStatus.NOT_FOUND;
            }
            expedienteDAO.deleteDAO(expediente);
            return HttpStatus.OK;
        } else {
            return HttpStatus.NO_CONTENT;
        }                
    }

    @RequestMapping("editar/{id}")
    public String editar(@PathVariable Long id, Model model) {

        Expediente expediente = expedienteDAO.find(new Expediente(id));
        model.addAttribute("usuario", expediente);
        return "mantenimiento/expediente/form";
    }
    
    @RequestMapping(value = "selected", method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public int selected(HttpServletRequest request, @RequestBody Long id) {        
        Expediente expediente = expedienteDAO.find(new Expediente(id));
        expediente.setEstado(1);
        expedienteDAO.updateDAO(expediente);
             
        List<Expediente> expedientes = expedienteDAO.allByEstado(1);                        
        return expedientes.size();
    }
    
    @RequestMapping(value = "unselected", method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public int unselected(HttpServletRequest request, @RequestBody Long id) {        
        Expediente expediente = expedienteDAO.find(new Expediente(id));        
        expediente.setEstado(0);
        expedienteDAO.updateDAO(expediente);
                
        List<Expediente> expedientes = expedienteDAO.allByEstado(1);                        
        return expedientes.size();
    }    
    
    @RequestMapping(value = "listaSelected", method = RequestMethod.GET)
    public ResponseEntity<List<Expediente>> listarExpedienteSelected(HttpServletRequest request) {        
        List<Expediente> expedientes = expedienteDAO.allByEstado(1);
        List<Expediente> lstExpedientes = new ArrayList();
        for (Expediente expediente : expedientes) {
            expediente.getAmbito().setExpedientes(null);
            expediente.getAmbito().setExpedientesPadron(null);
            expediente.getAmbito().setLocales(null);
            //expediente.setElectores(null);
            expediente.setExpedientesImpresion(null);
            expediente.setExpedientesPadron(null);
            lstExpedientes.add(expediente);
        }
        if (expedientes.isEmpty()) {
            return new ResponseEntity<List<Expediente>>(HttpStatus.NO_CONTENT);
        }                        
        return new ResponseEntity<List<Expediente>>(lstExpedientes, HttpStatus.OK);
    }
        
    @RequestMapping(value = "exportar/{expedientes}", method = RequestMethod.GET)
    public void exportar(HttpServletRequest request, @PathVariable("expedientes") String expedientes, HttpServletResponse response) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            Map<Ambito, Integer> ambitos = new HashMap<Ambito, Integer>();
            Gson gson = new Gson();

            List<JExpediente> lstExpedientes = new ArrayList<JExpediente>();
            List<JAmbito> lstAmbitos = new ArrayList<JAmbito>();                               

            String[] exps = expedientes.split(",");
            for (int i = 0; i < exps.length; i++) {
                Expediente e = new Expediente();
                e.setId(Long.parseLong(exps[i]));
                e = expedienteDAO.find(e);

                Ambito a = new Ambito();
                a.setId(e.getAmbito().getId());
                a = ambitoDAO.find(a);
                a.setExpedientes(null);
                List<Expediente> lstCurrentExp = new ArrayList<Expediente>();
                Expediente currentExp = new Expediente();
                currentExp = expedienteDAO.find(new Expediente(Long.parseLong(exps[i])));
                lstCurrentExp.add(currentExp);
                a.setExpedientes(lstCurrentExp);

                JExpediente je = new JExpediente();
                je.setCodigoExpediente(e.getId());
                je.setNombreExpediente(e.getExpediente());
                je.setUbigeo(a.getUbigeo());
                je.setDepartamento(a.getDepartamento());
                je.setProvincia(a.getProvincia());
                je.setDistrito(a.getDistrito());
                je.setCodigoAmbito(e.getAmbito().getId());
                je.setNombreAmbito(e.getAmbito().getNombreAmbito());                

                lstExpedientes.add(je);

                if (!ambitos.containsKey(a)) {
                    ambitos.put(a, 1);
                }
            }

            for (Ambito ap : ambitos.keySet()) {
                JAmbito ja = new JAmbito();
                ja.setCodigoAmbito(ap.getId());
                ja.setNombreAmbito(ap.getNombreAmbito());
                ja.setTipoAmbito(ap.getTipoAmbito());
                ja.setCategoria(ap.getCategoria());
                ja.setUbigeo(ap.getUbigeo());
                ja.setDepartamento(ap.getDepartamento());
                ja.setProvincia(ap.getProvincia());
                ja.setDistrito(ap.getDistrito());
                ja.setCodigoAmbitoPadre(ap.getAmbitoPadre() == null ? 0 : ap.getAmbitoPadre());
                for (Expediente exp : ap.getExpedientes()) {
                    ja.setCodigoExpediente(exp.getId());
                }                
                lstAmbitos.add(ja);

                List<Ambito> tempHijos = ambitoDAO.fetchAmbitoByAmbPadre(ap.getId());
                for (Ambito temp : tempHijos) {
                    JAmbito jah = new JAmbito();
                    jah.setCodigoAmbito(temp.getId());
                    jah.setNombreAmbito(temp.getNombreAmbito());
                    jah.setTipoAmbito(temp.getTipoAmbito());
                    jah.setCategoria(temp.getCategoria());
                    jah.setUbigeo(temp.getUbigeo());
                    jah.setDepartamento(temp.getDepartamento());
                    jah.setProvincia(temp.getProvincia());
                    jah.setDistrito(temp.getDistrito());
                    jah.setCodigoAmbitoPadre(temp.getAmbitoPadre());
                    jah.setCodigoExpediente(ja.getCodigoExpediente());
                    lstAmbitos.add(jah);
                }
            }

            List<JCategoria> lstCategoria = new ArrayList<JCategoria>();
            JCategoria jc = new JCategoria(0, "CC.PP. Principal");
            lstCategoria.add(jc);
            jc = new JCategoria(1, "PUEBLO");
            lstCategoria.add(jc);
            jc = new JCategoria(2, "CASERÍO");
            lstCategoria.add(jc);
            jc = new JCategoria(3, "ANEXO");
            lstCategoria.add(jc);
            jc = new JCategoria(4, "COMUNIDAD INDÍGENA");
            lstCategoria.add(jc);
            jc = new JCategoria(5, "COMUNIDAD CAMPESINA");
            lstCategoria.add(jc);
            jc = new JCategoria(6, "UNIDAD AGROPECUARIA");
            lstCategoria.add(jc);
            jc = new JCategoria(7, "COOPERATIVA AGRAGRIA");
            lstCategoria.add(jc);
            jc = new JCategoria(94, "OTROS");
            lstCategoria.add(jc);

            map.put("expedientes", lstExpedientes);
            map.put("ambitos", lstAmbitos);
            map.put("categorias", lstCategoria);

            response.setContentType("text/x-json;charset=UTF-8");
            String attachment = String.format("attachment; filename=%s.json", "EXPEDIENTES");
            response.setHeader("Content-Disposition", attachment);

            String result = gson.toJson(map);
            response.getWriter().write(result);

        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
        
        //Retorna los valores a cero
        String[] ex = expedientes.split(",");
        for (int i = 0; i < ex.length; i++) {
            Expediente e = new Expediente();
            e.setId(Long.parseLong(ex[i]));
            e = expedienteDAO.find(e);            
            e.setEstado(0);
            
            expedienteDAO.updateDAO(e);
        }
    }
    
    @RequestMapping(value = "search/{texto}", method = RequestMethod.GET)
    public ResponseEntity<List<Expediente>> searchExpediente(@PathVariable String texto, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        List<Expediente> lstExpedientes = expedienteDAO.allByAmbitoByText(new Ambito(session.getAttribute("ambito")), texto);                                
        return new ResponseEntity<List<Expediente>>(lstExpedientes, HttpStatus.OK);
    }    
    
    @RequestMapping(value = "existExpediente/{expediente}", method = RequestMethod.GET)
    public ResponseEntity<Integer> existExpediente(@PathVariable String expediente){
        int result = expedienteDAO.countExpedienteByNombre(expediente);
        return new ResponseEntity<Integer>(result, HttpStatus.OK);
    }
    
    @RequestMapping(value = "searchLista/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Expediente>> listarExpediente(@PathVariable Long id) {        
        List<Expediente> expedientes = expedienteDAO.allByExpediente(id);
        List<Expediente> lstExpedientes = new ArrayList();
        for (Expediente expediente : expedientes) {
            expediente.getAmbito().setExpedientes(null);
            expediente.getAmbito().setExpedientesPadron(null);
            expediente.getAmbito().setLocales(null);
            //expediente.setElectores(null);
            expediente.setExpedientesImpresion(null);
            expediente.setExpedientesPadron(null);
            lstExpedientes.add(expediente);
        }
        if (expedientes.isEmpty()) {
            return new ResponseEntity<List<Expediente>>(HttpStatus.NO_CONTENT);
        }                        
        return new ResponseEntity<List<Expediente>>(lstExpedientes, HttpStatus.OK);
    }
        
//    @RequestMapping(value = "save/", method = RequestMethod.POST, headers = "Accept=application/json")
//    @ResponseBody
//    public String save(@RequestBody Expediente expediente) {
//        Map<String, Object> map = new HashMap<String, Object>();
//        try {
//            if (expediente.getId() == null) {
//                expedienteDAO.saveDAO(expediente);
//            } else {
//                expedienteDAO.updateDAO(expediente);
//            }
//            map.put("result", HttpStatus.OK);            
//        } catch (Exception ex) {            
//            map.put("result", HttpStatus.NOT_FOUND);
//        }
//        
//        Ambito ambito = new Ambito();
//        ambito.setId(expediente.getAmbito().getId());
//        map.put("expedientes", expedienteDAO.allByAmbito(ambito));
//        GsonBuilder builder= new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
//        return builder.create().toJson(map);
//    }                

}
