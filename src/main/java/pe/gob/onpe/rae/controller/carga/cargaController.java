package pe.gob.onpe.rae.controller.carga;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import pe.gob.onpe.excelvalidator.ValidationManager;
import pe.gob.onpe.excelvalidator.model.Upload;
import pe.gob.onpe.rae.constant.Parametros;
import pe.gob.onpe.rae.constant.RaeConstants;
import pe.gob.onpe.rae.dao.AmbitoDAO;
import pe.gob.onpe.rae.dao.ElectorDAO;
import pe.gob.onpe.rae.dao.ExpedienteDAO;
import pe.gob.onpe.rae.dao.ExpedientePadronDAO;
import pe.gob.onpe.rae.dao.LocalDAO;
import pe.gob.onpe.rae.dao.PadronDAO;
import pe.gob.onpe.rae.dao.UploadDAO;
import pe.gob.onpe.rae.model.Ambito;
import pe.gob.onpe.rae.dao.UbigeoDAO;
import pe.gob.onpe.rae.dao.UsuarioDAO;
import pe.gob.onpe.rae.helper.PrintUtil;
import pe.gob.onpe.rae.model.Elector;
import pe.gob.onpe.rae.model.Expediente;
import pe.gob.onpe.rae.model.ExpedientePadron;
import pe.gob.onpe.rae.model.Padron;
import pe.gob.onpe.rae.model.Usuario;
import pe.gob.onpe.rae.model.report.RElector;

/**
 *
 * @author Bryan Valdez <bvaldez at onpe.gob.pe>
 */
@Controller
@Transactional
@RequestMapping(value = "/carga/*")
public class cargaController {

    @Autowired
    ExpedienteDAO expedienteDAO;

    @Autowired
    ExpedientePadronDAO expedientePadronDAO;

    @Autowired
    ElectorDAO electorDAO;

    @Autowired
    UploadDAO uploadDAO;

    @Autowired
    AmbitoDAO ambitoDAO;

    @Autowired
    UbigeoDAO ubigeoDAO;

    @Autowired
    PadronDAO padronDAO;
    
    @Autowired
    UsuarioDAO usuarioDAO;    

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        return "carga/index";
    }

    // Ubigeos, Ambitos y Expedientes    
    @RequestMapping(value = "/listar-departamentos", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String listarDepartamentos() {

        JsonParser p = new JsonParser();
        JsonObject jResponse = new JsonObject();

        //jResponse.add("data", p.parse(new Gson().toJson(ambitoDAO.listarDepartamento())));
        return new Gson().toJson(ambitoDAO.listarDepartamento());
    }

    @RequestMapping(value = "/listar-provincias/{ubigeo}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String listarProvincias(@PathVariable("ubigeo") String ubigeo) {

        JsonParser p = new JsonParser();
        JsonObject jResponse = new JsonObject();

        Ambito a = new Ambito();
        a.setUbigeo(ubigeo);

        //jResponse.add("data", p.parse(new Gson().toJson(ambitoDAO.listarProvincia(a))));
        return new Gson().toJson(ambitoDAO.listarProvincia(a));
    }

    @RequestMapping(value = "/listar-distritos/{ubigeo}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String listarDistritos(@PathVariable("ubigeo") String ubigeo) {

        JsonParser p = new JsonParser();
        JsonObject jResponse = new JsonObject();

        Ambito a = new Ambito();
        a.setUbigeo(ubigeo);

        //jResponse.add("data", p.parse(new Gson().toJson(ambitoDAO.listarDistrito(a))));
        return new Gson().toJson(ambitoDAO.listarDistrito(a));
    }

    @RequestMapping(value = "/listar-ccpp/{ubigeo}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String listarCCPP(@PathVariable("ubigeo") String ubigeo) {

        JsonParser p = new JsonParser();
        JsonObject jResponse = new JsonObject();

        Ambito a = new Ambito();
        a.setUbigeo(ubigeo);

        //jResponse.add("data", p.parse(new Gson().toJson(ambitoDAO.listarCCPP(a))));
        return new Gson().toJson(ambitoDAO.listarCCPP(a));
    }

    @RequestMapping(value = "/listar-expedientes/{ambito}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String listarExpedientes(@PathVariable("ambito") Long ambito) {

        JsonParser p = new JsonParser();
        JsonObject jResponse = new JsonObject();

        Ambito a = new Ambito();
        a.setId(ambito);

        Expediente e = new Expediente();
        e.setAmbito(a);

        //jResponse.add("data", p.parse(new Gson().toJson(expedienteDAO.listarExpediente(e))));
        return new Gson().toJson(expedienteDAO.listarExpediente(e));
    }

    // Busqueda
    @RequestMapping(value = "/buscar-ubigeo/{ubigeo}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String buscarUbigeo(@PathVariable("ubigeo") String ubigeo) {

        Ambito a = new Ambito();
        a.setDepartamento(ubigeo);

        return new Gson().toJson(ambitoDAO.buscarUbigeo(a));
    }

    @RequestMapping(value = "/buscar-ambito/{nombre}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String buscarAmbito(@PathVariable("nombre") String nombre) {

        Ambito a = new Ambito();
        a.setNombreAmbito(nombre);

        return new Gson().toJson(ambitoDAO.buscarCCPPPP(a));
    }

    @RequestMapping(value = "/expediente-ambito/{ambito}", method = RequestMethod.GET)
    @ResponseBody
    public String getListas(HttpServletRequest request, @PathVariable("ambito") Long ambito) {

        Ambito a = new Ambito();
        a.setId(ambito);

        Map<String, String> jResponse = new HashMap();

        jResponse.put("data", new Gson().toJson(expedienteDAO.allByAmbito(a)));
        return new Gson().toJson(jResponse);
    }

    @RequestMapping(value = "/validar-formato", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String validate(HttpServletRequest request, @RequestParam("ambito") String ambito, @RequestParam("file") MultipartFile file) {

        JsonParser p = new JsonParser();
        JsonObject jResponse = new JsonObject();
        List<Upload> columns = uploadDAO.getColumns(1);

        JsonObject result = new JsonObject();

        boolean success = false;

        try {
            File convFile = new File(file.getOriginalFilename());
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();

            ValidationManager validationManager = new ValidationManager(columns, convFile);
            result = (JsonObject) p.parse(validationManager.validate());

            JsonArray message = (JsonArray) result.get("message");

            if (message.size() == 0) {
                HttpSession session = request.getSession(false);
                result.add("columns", p.parse(new Gson().toJson(columns)));
                session.setAttribute("FILE_CONTENT", new Gson().toJson(result));
                session.setAttribute("ambito", ambito); 
                success = true;
            }

        } catch (Exception ex) {
            success = false;
            jResponse.addProperty("message", "El archivo seleccionado no se puede validar.");
        }

        jResponse.add("data", result);
        jResponse.addProperty("success", success);

        return new Gson().toJson(jResponse);
    }

    @RequestMapping(value = "/observaciones/", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getObservaciones(HttpServletRequest request) {

        JsonObject jResponse = new JsonObject();
        JsonParser p = new JsonParser();

        HttpSession session = request.getSession(false);
        JsonObject datos = (JsonObject) p.parse((String) session.getAttribute("FILE_CONTENT"));

        boolean obs = false;
        boolean success = true;
        List<Integer> duplicates = new ArrayList<Integer>();

        JsonArray data = (JsonArray) datos.get("data");

        try {

            for (int i = 0; i < data.size(); i++) {
                JsonObject row = data.get(i).getAsJsonObject();
                JsonObject rowObs = new JsonObject();
                String rowObsS = "";

                // validar datos Duplicados
                if (duplicates.contains(i)) {
                    rowObs.addProperty("OBS4", "Datos duplicados");
                    rowObsS = addObservacion(rowObsS,4);
                    obs = true;
                } else {
                    for (int j = 0; j < data.size(); j++) {
                        JsonObject rowToSearch = data.get(j).getAsJsonObject();

                        if (j > i && row.get("C_DOCUMENTO_IDENTIDAD").getAsString().equals(rowToSearch.get("C_DOCUMENTO_IDENTIDAD").getAsString())) {
                            if (!duplicates.contains(j)) {
                                duplicates.add(j);
                            }
                        }
                    }
                }

                Padron padron = new Padron();
                padron.setNumEle(row.get("C_DOCUMENTO_IDENTIDAD").getAsString());
                padron = padronDAO.find(padron);

                // validar Padron Nacional Trimestral
                if (padron == null) {
                    rowObs.addProperty("OBS3", "No se encuentra en el Padron Trimestral");
                    rowObsS = addObservacion(rowObsS,3);
                } else {

                    // validar Nombres
                    String fullnameu = row.get("C_APELLIDO_PATERNO").getAsString() + row.get("C_APELLIDO_MATERNO").getAsString() + row.get("C_NOMBRE").getAsString();
                    String fullname = padron.getApePat() + padron.getApMat() + padron.getNombres();

                    if (!fullname.equalsIgnoreCase(fullnameu)) {
                        obs = true;
                        rowObs.addProperty("OBS1", "DNI no corresponde al nombre");
                        rowObsS = addObservacion(rowObsS,1);
                    }

                    // validar Padron Distrito - Ubigeo
                    if (!session.getAttribute("ambito").equals(row.get("C_UBIGEO_ELECTOR").getAsString())) {
                        obs = true;
                        rowObs.addProperty("OBS2", "No se encuentra en el Padron del Distrito");
                        rowObsS = addObservacion(rowObsS,2);
                    }

                    // validar DNI restringido
                    if (padron.getRestric() != null) {
                        obs = true;
                        rowObs.addProperty("OBS5", "DNI Restringido");
                        rowObsS = addObservacion(rowObsS,5);
                    }
                }

                // add observaciones
                JsonObject observaciones = new JsonObject();
                observaciones.add("SISTEMA", rowObs);
                row.add("C_OBSERVACION", observaciones);
                row.addProperty("observacion", rowObsS);

                // Definir estado
                // DNI registrado
                if (expedientePadronDAO.fetchListaPadronByDni(row.get("C_DOCUMENTO_IDENTIDAD").getAsString()) != null) {
                    //row.addProperty("N_ESTADO", 1); estado deshabilitado
                    row.addProperty("N_ESTADO", 3);
                }

                // Pendiente
                if (rowObs.has("OBS2") || rowObs.has("OBS3") || rowObs.has("OBS5")) {
                    row.addProperty("N_ESTADO", 2);
                }

                // Rechazado
                if (rowObs.has("OBS1") || rowObs.has("OBS4") || rowObs.has("OBS6") || rowObs.has("OBS7")) {
                    row.addProperty("N_ESTADO", 3);
                }

                //Eliminado - falta implementar
            }

        } catch (Exception ex) {
            success = false;
            jResponse.addProperty("message", "No se pudieron obtener las observaciones.");
        }

        jResponse.addProperty("success", success);
        jResponse.add("data", data);

        return new Gson().toJson(jResponse);
    }

    @RequestMapping(value = "/guardar", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String guardar(HttpServletRequest request,HttpServletResponse response, @RequestParam("padron") String padron, @RequestParam("expediente") Long expediente, @RequestParam("ambito") Long ambito) {

        JsonObject jResponse = new JsonObject();
        JsonParser jp = new JsonParser();
        Gson gson = new Gson();

        boolean success = true;

        try {
            JsonArray padronUpload = (JsonArray) jp.parse(padron);
            jResponse.addProperty("data", new Gson().toJson(padronUpload));            

            Expediente e = new Expediente();
            e.setId(expediente);

            Ambito a = new Ambito();
            a.setId(ambito);
            a = ambitoDAO.find(a);

            for (int i = 0; i < padronUpload.size(); i++) {
                JsonObject item = padronUpload.get(i).getAsJsonObject();

                JsonObject obs = item.get("C_OBSERVACION").getAsJsonObject();

                ExpedientePadron ep = new ExpedientePadron();
                ep.setDni(item.get("C_DOCUMENTO_IDENTIDAD").getAsString());
                ep.setApellidoPaterno(new String(item.get("C_APELLIDO_PATERNO").getAsString().getBytes("ISO-8859-1"), "UTF-8"));
                ep.setApellidoMaterno(new String(item.get("C_APELLIDO_MATERNO").getAsString().getBytes("ISO-8859-1"), "UTF-8"));
                ep.setNombre(new String(item.get("C_NOMBRE").getAsString().getBytes("ISO-8859-1"), "UTF-8"));
                //ep.setObservacion(gson.toJson(obs));
                ep.setObservacion(item.get("observacion").getAsString());
                
                HttpSession session = request.getSession(false);
                Usuario user = (Usuario)session.getAttribute("USUARIO_AUTENTICADO");
                Usuario currentUser = usuarioDAO.find(user);    
                if(currentUser.getPerfil().getId()== Parametros.PERFIL_ONPE){
                    ep.setFlag(1);  
                }else{
                    ep.setFlag(0); 
                }
                if(item.get("C_UBIGEO_ELECTOR").getAsString() != a.getUbigeo()){
                    ep.setIndicador(1);
                }else{
                    ep.setIndicador(0);
                }
                ep.setEstado(item.get("N_ESTADO").getAsInt());
                ep.setOrdenRegistro(item.get("N_ORDEN_REGISTRO").getAsInt());
                ep.setUbigeoElector(item.get("C_UBIGEO_ELECTOR").getAsString());
                ep.setUbigeoLista(a.getUbigeo());

                ep.setExpediente(e);
                ep.setAmbito(a);                               

                expedientePadronDAO.saveDAO(ep);

                // nuevo elector ESTADO = PENDIENTE Y OBS3            
                if (ep.getEstado() == 2 && obs.get("SISTEMA").getAsJsonObject().has("OBS3")) {
                    Elector elector = new Elector();
                    elector.setDni(ep.getDni());
                    elector.setApellidoPaterno(ep.getApellidoPaterno());
                    elector.setApellidoMaterno(ep.getApellidoMaterno());
                    elector.setNombre(ep.getNombre());
                    elector.setUbigeo(ep.getUbigeoElector());
                    //elector.setExpediente(e);
                    electorDAO.saveDAO(elector);
                }

                // create new Ambito
                Ambito an = new Ambito();
                an.setId(item.get("N_AMBITO").getAsLong());
                an = ambitoDAO.find(an);

                if(an!=null){
                    if(!an.getNombreAmbito().equals(item.get("C_NOMBRE_AMBITO").getAsString())){
                        Ambito newambito = new Ambito();
                        newambito.setId(item.get("N_AMBITO").getAsLong());
                        newambito.setNombreAmbito(item.get("C_NOMBRE_AMBITO").getAsString());
                        ambitoDAO.saveDAO(newambito);
                    }
                }                             
            }
            

        } catch (Exception ex) {
            success = false;
            jResponse.addProperty("message", "No se guardo los registros cargados.");
        }
        jResponse.addProperty("success", success);
        
        
        return gson.toJson(jResponse);
    }
    
    private String addObservacion(String obs, int code) {
        if (obs.equals("")) {
            obs = obs + code;
        } else {
            obs = obs + "," + code;
        }
        return obs;
    }
//    CAMBIOS
    @RequestMapping(value = "/observaciones/", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getObservaciones2(HttpServletRequest request, @RequestParam("padron") String padron) {
        
        JsonObject jResponse = new JsonObject();
        JsonParser jp = new JsonParser();
        HttpSession session = request.getSession(false);
        String rowObsS = "";
        
        JsonObject row = new JsonObject();
        JsonObject rowObs = new JsonObject();
        row.addProperty("C_DOCUMENTO_IDENTIDAD", padron);

        Padron padron1 = new Padron();
        padron1.setNumEle(row.get("C_DOCUMENTO_IDENTIDAD").getAsString());
        padron1 = padronDAO.find(padron1);
        
        row.addProperty("C_APELLIDO_PATERNO", padron1.getApePat()); 
        row.addProperty("C_APELLIDO_MATERNO", padron1.getApMat());
        row.addProperty("C_NOMBRE", padron1.getNombres());
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        row.addProperty("D_FECHA_REGISTRO", df.format(date));
        row.addProperty("C_UBIGEO_ELECTOR", padron1.getUbigeo());
        
       
        if (!session.getAttribute("ambito").equals(row.get("C_UBIGEO_ELECTOR").getAsString())) {
            rowObs.addProperty("OBS2", "No se encuentra en el Padron del Distrito");
            rowObsS = addObservacion(rowObsS,2);
        }
        if (padron1.getRestric() != null) {
            rowObs.addProperty("OBS5", "DNI Restringido");
            rowObsS = addObservacion(rowObsS,5);
        }
        
        JsonObject observaciones = new JsonObject();
        observaciones.add("SISTEMA", rowObs);
        row.add("C_OBSERVACION", observaciones);
        row.addProperty("observacion", rowObsS);
        
        if (expedientePadronDAO.fetchListaPadronByDni(row.get("C_DOCUMENTO_IDENTIDAD").getAsString()) != null) {
            //row.addProperty("N_ESTADO", 1); estado deshabilitado
            row.addProperty("N_ESTADO", 3);
        }
        if (rowObs.has("OBS2") || rowObs.has("OBS3") || rowObs.has("OBS5")) {
            row.addProperty("N_ESTADO", 2);
        }
        if (rowObs.has("OBS1") || rowObs.has("OBS4") || rowObs.has("OBS6") || rowObs.has("OBS7")) {
            row.addProperty("N_ESTADO", 3);
        }        
        
        jResponse.addProperty("success", true);
        jResponse.add("data", row);

        return new Gson().toJson(jResponse);
    }
    
    
    @RequestMapping(value = "/documento", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void reImprimir(HttpServletRequest request,HttpServletResponse response, @RequestParam("padron") String padron, @RequestParam("expediente") Long expediente, @RequestParam("ambito") Long ambito) {

        JsonObject jResponse = new JsonObject();
        JsonParser jp = new JsonParser();
        Gson gson = new Gson();

        boolean success = true;

        try {
            JsonArray padronUpload = (JsonArray) jp.parse(padron);      

            Expediente e = new Expediente();
            e.setId(expediente);

            Ambito a = new Ambito();
            a.setId(ambito);
            a = ambitoDAO.find(a);
            
            List<RElector> lstElector = new ArrayList<RElector>();
            for (int i = 0; i < padronUpload.size(); i++) {
                JsonObject item = padronUpload.get(i).getAsJsonObject();
                RElector elec = new RElector();
                elec.setOrdenRegistro(item.get("N_ORDEN_REGISTRO").getAsInt());
                elec.setDocumentoIdentidad(item.get("N_ORDEN_REGISTRO").getAsString());
                elec.setApellidoPaterno(new String(item.get("C_APELLIDO_PATERNO").getAsString().getBytes("ISO-8859-1"), "UTF-8"));
                elec.setApellidoMaterno(new String(item.get("C_APELLIDO_MATERNO").getAsString().getBytes("ISO-8859-1"), "UTF-8"));
                elec.setNombre(new String(item.get("C_NOMBRE").getAsString().getBytes("ISO-8859-1"), "UTF-8"));
                //elec.setDistritoElector((ubigeoDAO.findByCodeUbigeo(lstElectores.get(i).getUbigeoElector())).getDescripcion());
                lstElector.add(elec);
            }            
            
            ServletContext sc = request.getSession().getServletContext();
            InputStream inStream = sc.getResourceAsStream("/WEB-INF/jasper/ficha_verificacion.jasper");
            HashMap<String, Object> parameters = new HashMap();
            parameters.put("TITLE_REPORT", RaeConstants.N_TITLE_REPORT_OBS);
            parameters.put("CCPP",a.getNombreAmbito());
            parameters.put("DISTRITO",a.getDistrito());
            parameters.put("EXPEDIENTE",e.getExpediente());
            parameters.put("YEAR",e.getFechaExpediente());
            byte[] bytes = PrintUtil.generarPdf(parameters, lstElector, inStream);

            response.setContentLength(bytes.length);
            ServletOutputStream outStream = response.getOutputStream();
            outStream.write(bytes, 0, bytes.length);
            outStream.flush();
            outStream.close();             
            
       } catch (Exception ex) {
            
           Logger.getLogger(cargaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

}
