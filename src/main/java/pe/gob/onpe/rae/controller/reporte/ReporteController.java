package pe.gob.onpe.rae.controller.reporte;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import pe.gob.onpe.rae.dao.AmbitoDAO;
import pe.gob.onpe.rae.dao.ReporteDAO;
import pe.gob.onpe.rae.dao.UbigeoDAO;
import pe.gob.onpe.rae.model.Ambito;
import pe.gob.onpe.rae.model.Reporte;
import pe.gob.onpe.rae.model.json.Filtro;
import pe.gob.onpe.rae.model.json.JElector;
import pe.gob.onpe.reportgenerator.iface.IExcelGenerator;
import pe.gob.onpe.reportgenerator.iface.IFactory;
import pe.gob.onpe.reportgenerator.iface.impl.FactoryService;
import pe.gob.onpe.reportgenerator.model.Report;

/**
 *
 * @author Manuel Arrisueno <marrisueno at onpe.gob.pe>
 */
@Controller
@Transactional
@RequestMapping("/reporte/*")
public class ReporteController {
    
    @Autowired
    ReporteDAO reporteDAO;
    
    @Autowired
    AmbitoDAO ambitoDAO;
    
    @Autowired
    UbigeoDAO ubigeoDAO;
    
//    @RequestMapping("index")
//    public String index(){
//        return "reporte/index";
//    }
    
    @RequestMapping("index")
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView view;
        view = new ModelAndView("reporte/index");        
        //view.addObject("dptos", new Gson().toJson(ambitoDAO.listarDepartamento()));
        view.addObject("lstDptos", new Gson().toJson(ubigeoDAO.fetchDepartamentos()));
        return view;
    }
    
    @RequestMapping(value = "modal/{filtro}")
    public String create(@PathVariable("filtro") String filtro) {        
        //return "reporte/form";
        return "reporte/" + filtro;
    }
    
    @RequestMapping(value = "searchCCPP/{texto}", method = RequestMethod.GET)
    public ResponseEntity<List<Ambito>> searchCCPP(@PathVariable String texto, HttpServletRequest request) {        
        List<Ambito> lstAmbito = ambitoDAO.allAmbitoByText(texto.toUpperCase());                                
        return new ResponseEntity<List<Ambito>>(lstAmbito, HttpStatus.OK);
    }
         
    @RequestMapping(value = "listar", method = RequestMethod.GET)
    public ResponseEntity<List<Reporte>> listarReportes(HttpServletRequest request) {
        
        List<Reporte> lstReportes = reporteDAO.all();        
        if (lstReportes.isEmpty()) {
            return new ResponseEntity<List<Reporte>>(HttpStatus.NO_CONTENT);
        }                        
        return new ResponseEntity<List<Reporte>>(lstReportes, HttpStatus.OK);
    }
    
    @RequestMapping(value = "exportar/{filtro}", method = RequestMethod.GET)
    public void exportar(HttpServletRequest request, @PathVariable("filtro") String filtro, HttpServletResponse response) {        
        try {            
            Gson g = new Gson();
            Filtro filter = g.fromJson(filtro, Filtro.class);
            
            JsonParser jsonParser= new JsonParser();
            JsonObject jsonObject= (JsonObject)jsonParser.parse(filtro);
            
            Report report = new Report();                        
            Reporte reporte = reporteDAO.find(new Reporte(filter.getIdReporte()));                                                
            
            report.setId(reporte.getId());
            report.setCode(reporte.getCodigo());
            report.setLabel(reporte.getEtiqueta());
            report.setDescription(reporte.getDescripcion());
            report.setQuery(reporte.getQuery());
            report.setFileName(reporte.getNombreArchivo());
            report.setEstado(reporte.getEstado());
            
            List result = reporteDAO.execute(report.getQuery(), filter);
//            List<JElector> lstElectores = new ArrayList<JElector>();
//            for (Object r : result) {
//                JElector jEle = new JElector();
//                jEle.setDepartamento(((Map<String, String>) r).get("DEPARTAMENTO"));
//                jEle.setProvincia(((Map<String, String>) r).get("PROVINCIA"));
//                jEle.setDistrito(((Map<String, String>) r).get("DISTRITO"));
//                jEle.setCcpp_principal(((Map<String, String>) r).get("CCPP_PRINCIPAL"));
//                jEle.setAdjunto(((Map<String, String>) r).get("ADJUNTO"));
//                jEle.setTotal(((Map<String, BigDecimal>) r).get("TOTAL").intValueExact());
//                jEle.setHabilitados(((Map<String, BigDecimal>) r).get("HABILITADOS").intValueExact());
//                jEle.setPendientes((((Map<String, BigDecimal>) r).get("PENDIENTES")).intValueExact());
//                jEle.setRechazados((((Map<String, BigDecimal>) r).get("RECHAZADOS")).intValueExact());
//                lstElectores.add(jEle);
//            }
            
            Gson gsonCreate = new GsonBuilder().serializeNulls().create();
            String gson = gsonCreate.toJson(result);
            IFactory factory = new FactoryService();
            IExcelGenerator excelGenerator = factory.createExcelGenerator(report, gson, null, true, null);
            HSSFWorkbook workbook = excelGenerator.createFromJson();

            response.setContentType("application/vnd.ms-excel");
            String fileName = report.getCode() + "_" + report.getFileName();
            fileName = fileName.replace(" ", "_");
            String attachment = String.format("attachment; filename=%s.xls", fileName);
            response.setHeader("Content-Disposition", attachment);
            workbook.write(response.getOutputStream());

        } catch (IOException ex) {
            System.out.println("ERROR");
        }               
    }
        
}

