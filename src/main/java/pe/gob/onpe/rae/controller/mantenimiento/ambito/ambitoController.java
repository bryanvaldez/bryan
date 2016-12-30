package pe.gob.onpe.rae.controller.mantenimiento.ambito;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
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
import pe.gob.onpe.rae.dao.AmbitoDAO;
import pe.gob.onpe.rae.dao.ExpedienteDAO;
import pe.gob.onpe.rae.dao.ExpedientePadronDAO;
import pe.gob.onpe.rae.dao.UbigeoDAO;
import pe.gob.onpe.rae.model.Ambito;
import pe.gob.onpe.rae.model.Expediente;
import pe.gob.onpe.rae.model.ExpedientePadron;
import pe.gob.onpe.rae.model.UbigeoVw;

/**
 * 
 * @author Bryan Valdez <bvaldez at onpe.gob.pe>
 */
@Controller
@Transactional
@SessionAttributes("ambito")
@RequestMapping(value ="/mantenimiento/ambito/*")
public class ambitoController {
    
    @Autowired
    AmbitoDAO ambitoDAO;
    
    @Autowired
    ExpedienteDAO listaDAO;
    
    @Autowired
    UbigeoDAO ubigeoDAO;    
    
    @Autowired
    ExpedienteDAO expedienteDAO;
    
    @Autowired
    ExpedientePadronDAO expedientePadronDAO;
    
    @Autowired
    private Properties configProperties;
    
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Ambito>> listarAmbitos() {
        List<Ambito> ambitos = ambitoDAO.all();
        List<Ambito> lstAmbito = new ArrayList();
        
        for (Ambito ambito : ambitos) {
            ambito.setExpedientes(null);
            ambito.setLocales(null);
            ambito.setExpedientesPadron(null);
            lstAmbito.add(ambito);
        }
        
        if(ambitos.isEmpty()){
            return new ResponseEntity<List<Ambito>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Ambito>>(lstAmbito, HttpStatus.OK);
    }    
    
//    @RequestMapping(value="filters", method = RequestMethod.GET)
//    @ResponseBody
//    public String getFilters() {        
//        Map<String, String> jResponse = new HashMap();
//        Map<String, String> data = new HashMap();
//        
//        data.put("departamentos",new Gson().toJson(ubigeoDAO.allDepartamento()));
//        data.put("provincias", new Gson().toJson(ubigeoDAO.allProvincia()));
//        data.put("distritos", new Gson().toJson(ubigeoDAO.allDistrito()));
//        
//        jResponse.put("data", new Gson().toJson(data));
//        return new Gson().toJson(jResponse);
//    }    
    
    @RequestMapping(value="lista", method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody    
    public ResponseEntity<List<Ambito>> saveAmbitos(@RequestBody String json){
              
        List<Ambito> lstambitos = new ArrayList<Ambito>();
        
        Gson g = new Gson();
        JsonParser jsonParser = new JsonParser();
        JsonArray array = (JsonArray) jsonParser.parse(json);
        for (JsonElement abs : array) {
            Ambito temp = g.fromJson(abs, Ambito.class);
            lstambitos.add(temp);
        }                
        
        UbigeoVw ubigeo = new UbigeoVw();
        Ambito ambitoPadre = new Ambito();
        for (Ambito ambito : lstambitos) {
            if(ambito.getId() == null){                
                if(ambito.getTipoAmbito() == 1){
                    ubigeo = ubigeoDAO.findByUbigeo(ambito.getUbigeo());
                    ambito.setDepartamento(ubigeo.getDepartamento());
                    ambito.setProvincia(ubigeo.getProvincia());
                    ambito.setDistrito(ubigeo.getDistrito());
                    ambitoDAO.saveDAO(ambito);
                    ambitoPadre = ambito;
                }else{
                    ambito.setUbigeo(ambitoPadre.getUbigeo());
                    ambito.setDepartamento(ubigeo.getDepartamento());
                    ambito.setProvincia(ubigeo.getProvincia());
                    ambito.setDistrito(ubigeo.getDistrito());
                    ambito.setAmbitoPadre(ambitoPadre.getId());
                    ambitoDAO.saveDAO(ambito);                    
                }
            }else{
//                Ambito currentAmbito = ambitoDAO.find(ambito);
//                if(currentAmbito == null){
//                    return new ResponseEntity<List<Ambito>>(HttpStatus.NOT_FOUND);
//                } else {
//                    ubigeo = ubigeoDAO.findByUbigeo(currentAmbito.getUbigeo());
//                    currentAmbito.setDepartamento(ubigeo.getDepartamento());
//                    currentAmbito.setProvincia(ubigeo.getProvincia());
//                    currentAmbito.setDistrito(ubigeo.getDistrito());                    
//                    ambitoPadre = currentAmbito;
//                }            
//                ambitoDAO.updateDAO(currentAmbito);
                if (ambito.getTipoAmbito() == 1) {
                    ubigeo = ubigeoDAO.findByUbigeo(ambito.getUbigeo());
                    ambito.setDepartamento(ubigeo.getDepartamento());
                    ambito.setProvincia(ubigeo.getProvincia());
                    ambito.setDistrito(ubigeo.getDistrito());                    
                    ambitoPadre = ambito;    
                }                
                ambitoDAO.updateDAO(ambito);
            }
        }
        return new ResponseEntity<List<Ambito>>(HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<Ambito> saveAmbito(@RequestBody Ambito ambito){
        UbigeoVw ubigeo = ubigeoDAO.findByUbigeo(ambito.getUbigeo());        
        if(ambito.getId() == null){
            ambito.setDepartamento(ubigeo.getDepartamento());
            ambito.setProvincia(ubigeo.getProvincia());
            ambito.setDistrito(ubigeo.getDistrito()); 
            ambitoDAO.saveDAO(ambito);                        
        }else{
            ambito.setDepartamento(ubigeo.getDepartamento());
            ambito.setProvincia(ubigeo.getProvincia());
            ambito.setDistrito(ubigeo.getDistrito());
            ambitoDAO.updateDAO(ambito);
        }
        return new ResponseEntity<Ambito>(HttpStatus.OK);
    }    
        
    
    @RequestMapping(value="{id}", method = RequestMethod.GET)
    @ResponseBody
    public String delete(@PathVariable("id") long id){
        Map<String, Object> map = new HashMap<String, Object>();        
        Ambito ambito = new Ambito();
        ambito.setId(id);
        Ambito currentambito = ambitoDAO.find(ambito);                        
        if(currentambito == null){
            map.put("result", HttpStatus.NOT_FOUND);
            map.put("message", "Ocurrio un error.");
        } else {
            if( currentambito.getLocales().isEmpty() && currentambito.getExpedientes().isEmpty() ){
                List<Ambito> lstHijos = ambitoDAO.fetchAmbitoByAmbPadre(currentambito.getId());
                if (lstHijos.isEmpty()) {
                    ambitoDAO.deleteDAO(currentambito);
                    map.put("result", HttpStatus.OK);
                    map.put("message", "El ambito se ha eliminado con exito.");
                } else {
                    map.put("result", HttpStatus.NO_CONTENT);
                    map.put("message", "El ambito tiene ambito(s) adjunto(s).");
                }              
            }else{
                map.put("result", HttpStatus.NO_CONTENT);
                map.put("message", "El ambito tiene Locales de Votacion y/o Expedientes asociados.");
            }
        }               
        
        GsonBuilder builder= new GsonBuilder();
        return builder.create().toJson(map);
    } 
    
    @RequestMapping(value="deleteAnexo/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String deleteAnexo(@PathVariable("id") long id){
        Map<String, Object> map = new HashMap<String, Object>();                        
        
        Expediente exp = new Expediente();
        exp.setAmbito(new Ambito(id));        
        List<ExpedientePadron> exist = expedientePadronDAO.getExpedientesByAmbitoAdjunto(exp);
        if (exist.isEmpty()) {
            Ambito ambito = new Ambito();
            ambito.setId(id);
            Ambito currentambito = ambitoDAO.find(ambito);
            ambitoDAO.deleteDAO(currentambito);
            map.put("result", HttpStatus.OK);
            map.put("message", "El ambito adjunto se ha eliminado con exito.");            
        } else {
            map.put("result", HttpStatus.NO_CONTENT);
            map.put("message", "No se pudo eliminar. El ambito adjunto tiene electores registrados.");
        }                   
        
        GsonBuilder builder= new GsonBuilder();
        return builder.create().toJson(map);
    }
    
    @RequestMapping(value="departamentos", method = RequestMethod.GET)
    @ResponseBody
    public String getDepartamentos() {        
        Map<String, String> jResponse = new HashMap();
        Map<String, String> data = new HashMap();       
        data.put("departamentos",new Gson().toJson(ubigeoDAO.allDepartamento()));                     
//        data.put("ambitoPrincipal", new Gson().toJson(ambitoDAO.allAmbitoPrincipal()));
        jResponse.put("data", new Gson().toJson(data));
        return new Gson().toJson(jResponse);
    }

    @RequestMapping(value="provincias/{dep}", method = RequestMethod.GET)
    @ResponseBody
    public String getProvincias(@PathVariable("dep") String dep) {        
        Map<String, String> jResponse = new HashMap();
        Map<String, String> data = new HashMap();        
        data.put("provincias", new Gson().toJson(ubigeoDAO.getProvincia(dep)));        
        jResponse.put("data", new Gson().toJson(data));
        return new Gson().toJson(jResponse);
    } 
    
    @RequestMapping(value="distritos/{prov}", method = RequestMethod.GET)
    @ResponseBody
    public String getDistritos(@PathVariable("prov") String prov) {        
        Map<String, String> jResponse = new HashMap();
        Map<String, String> data = new HashMap();        
        data.put("distritos", new Gson().toJson(ubigeoDAO.getDistrito(prov)));       
        jResponse.put("data", new Gson().toJson(data));
        return new Gson().toJson(jResponse);

    }            
    
    @RequestMapping(value = "/exportar/{id}", method = RequestMethod.GET)
    public void exportar(HttpServletRequest request, @PathVariable("id") Long id, HttpServletResponse response) {
        try {     
            Ambito ambito = ambitoDAO.find(new Ambito(id));
            
            String url = configProperties.getProperty("BASE_URL") + "/resources/FORMATON2.xls";            
            InputStream inStream = new URL(url).openStream();
            
            HSSFWorkbook workbook = new HSSFWorkbook(inStream);  
            HSSFSheet sheet = workbook.getSheetAt(0);
            
            HSSFRow row = sheet.getRow(1);              
            HSSFCell cell = row.getCell(6);
            cell.setCellValue(ambito.getNombreAmbito());
            
            row = sheet.getRow(4);
            cell = row.getCell(1);
            cell.setCellValue(ambito.getDepartamento());
            cell = row.getCell(4);
            cell.setCellValue(ambito.getProvincia());
            cell = row.getCell(7);
            cell.setCellValue(ambito.getDistrito());
            
            row = sheet.getRow(5);
            cell = row.getCell(3);
            cell.setCellValue(ambito.getNombreAmbito());
            
            row = sheet.getRow(6);
            cell = row.getCell(1);
            String nombreCategoria;
            switch (ambito.getCategoria()) {
                case 1:  nombreCategoria = "Pueblo";
                         break;
                case 2:  nombreCategoria = "Caserio";
                         break;
                case 3:  nombreCategoria = "Anexo";
                         break;
                case 4:  nombreCategoria = "Comunidad Indigena";
                         break;
                case 5:  nombreCategoria = "Comunidad Campesina";
                         break;
                case 6:  nombreCategoria = "Unidad Agropecuaria";
                         break;
                case 7:  nombreCategoria = "Cooperativa Agraria";
                         break;
                case 94:  nombreCategoria = "Otros";
                         break;                
                default: nombreCategoria = "CCPP Principal";
                         break;
            }
            cell.setCellValue(nombreCategoria);
            
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = (JsonObject) jsonParser.parse(ambito.getInformacion());
            row = sheet.getRow(10);
            cell = row.getCell(2); 
            String nombre = jsonObject.get("nombres").toString() + " " + jsonObject.get("apellidoPaterno").toString() + " " + jsonObject.get("apellidoMaterno").toString();
            cell.setCellValue(nombre.replace("\"", ""));
            
            row = sheet.getRow(11);
            cell = row.getCell(1); 
            String cargo = jsonObject.get("cargo").toString().replace("\"", "");
            if (cargo.equals("1")) {
                cell.setCellValue("Alcalde");
            } else {
                cell.setCellValue("Representante");
            }   
            
            row = sheet.getRow(11);
            cell = row.getCell(4); 
            cell.setCellValue(jsonObject.get("direccion").toString().replace("\"", "")); 
            
            row = sheet.getRow(12);
            cell = row.getCell(1); 
            cell.setCellValue(jsonObject.get("telefono").toString().replace("\"", "")); 
            
            row = sheet.getRow(12);
            cell = row.getCell(5); 
            cell.setCellValue(jsonObject.get("email").toString().replace("\"", "")); 
            
            
            row = sheet.getRow(15);
            cell = row.getCell(1); 
            String nombre_ = jsonObject.get("nombres_").toString() + " " + jsonObject.get("apellidoPaterno_").toString() + " " + jsonObject.get("apellidoMaterno_").toString();
            cell.setCellValue(nombre_.replace("\"", ""));
            
            row = sheet.getRow(16);
            cell = row.getCell(1); 
            String cargo_ = jsonObject.get("cargo_").toString().replace("\"", "");
            if (cargo_.equals("1")) {
                cell.setCellValue("Alcalde");
            } else {
                cell.setCellValue("Representante");
            }
            
            row = sheet.getRow(17);
            cell = row.getCell(1); 
            cell.setCellValue(jsonObject.get("direccion_").toString().replace("\"", "")); 
            
            row = sheet.getRow(18);
            cell = row.getCell(1); 
            cell.setCellValue(jsonObject.get("telefono_").toString().replace("\"", "")); 
            
            row = sheet.getRow(19);
            cell = row.getCell(1); 
            cell.setCellValue(jsonObject.get("email_").toString().replace("\"", "")); 
                        
            response.setContentType("application/vnd.ms-excel");

            String attachment = String.format("attachment; filename=%s.xls", "FORMATO N2");
            response.setHeader("Content-Disposition", attachment);
            workbook.write(response.getOutputStream());
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
               
    }
    
//    @RequestMapping(value="anexo/{id}", method = RequestMethod.GET)
//    public ResponseEntity<List<Ambito>> AmbitosHijos(@PathVariable("id") Long id){        
//        Ambito ambito = new Ambito();
//        ambito.setId(id);
//        Ambito currentAmbito = ambitoDAO.find(ambito);
//        List<Ambito> lstAmbito = new ArrayList();
//        
//        for (Ambito hijo : currentAmbito.getAmbitoHijo()) {
//            hijo.setExpedientes(null);
//            hijo.setLocales(null);
//            hijo.setAmbitoHijo(null);
//            hijo.setExpedientesPadron(null);
//            hijo.setAmbitoPadre(null);
//            lstAmbito.add(hijo);
//        }        
//
////                
////        if(ambitos.isEmpty()){
////            return new ResponseEntity<List<Ambito>>(HttpStatus.NO_CONTENT);
////        }
//        return new ResponseEntity<List<Ambito>>(lstAmbito, HttpStatus.OK);
//    } 
//           
      
        
}
