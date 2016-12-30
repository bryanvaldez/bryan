/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.onpe.rae.controller.registro;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
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
import org.springframework.web.servlet.ModelAndView;
import pe.gob.onpe.rae.constant.Parametros;
import pe.gob.onpe.rae.constant.RaeConstants;
import pe.gob.onpe.rae.controller.BaseController;
import pe.gob.onpe.rae.dao.AmbitoDAO;
import pe.gob.onpe.rae.dao.ElectorDAO;
import pe.gob.onpe.rae.dao.ExpedienteDAO;
import pe.gob.onpe.rae.dao.ExpedienteImpresionDAO;
import pe.gob.onpe.rae.dao.ExpedientePadronDAO;
import pe.gob.onpe.rae.dao.PadronDAO;
import pe.gob.onpe.rae.dao.ParametroDAO;
import pe.gob.onpe.rae.dao.ReporteDAO;
import pe.gob.onpe.rae.dao.UbigeoDAO;
import pe.gob.onpe.rae.helper.PrintUtil;
import pe.gob.onpe.rae.model.Ambito;
import pe.gob.onpe.rae.model.Elector;
import pe.gob.onpe.rae.model.Expediente;
import pe.gob.onpe.rae.model.ExpedienteImpresion;
import pe.gob.onpe.rae.model.ExpedientePadron;
import pe.gob.onpe.rae.model.Padron;
import pe.gob.onpe.rae.model.Parametro;
import pe.gob.onpe.rae.model.Ubigeo;
import pe.gob.onpe.rae.model.Usuario;
import pe.gob.onpe.rae.model.report.RElector;
import pe.gob.onpe.reportgenerator.iface.IExcelGenerator;
import pe.gob.onpe.reportgenerator.iface.IFactory;
import pe.gob.onpe.reportgenerator.iface.impl.FactoryService;
import pe.gob.onpe.reportgenerator.model.Report;

/**
 *
 * @author AQuispec
 */
@Controller
@Transactional
@RequestMapping("/registro/*")
public class registroController extends BaseController {

    @Autowired
    PadronDAO padronDAO;

    @Autowired
    ElectorDAO electorDAO;

    @Autowired
    ExpedientePadronDAO expedientePadronDAO;

    @Autowired
    UbigeoDAO ubigeoDAO;

    @Autowired
    AmbitoDAO ambitoDAO;

    @Autowired
    ExpedienteDAO expedienteDAO;

    @Autowired
    ExpedienteImpresionDAO expedienteImpresionDAO;

    @Autowired
    ParametroDAO parametroDAO;

    @Autowired
    ReporteDAO reporteDAO;

    @RequestMapping(value = "fetchPadron/{numele}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ExpedientePadron> fetchPadronByDni(HttpServletRequest request, @PathVariable("numele") String numele) {

        Padron padron = new Padron();
        Expediente lista = new Expediente();
        Elector elector = new Elector();
        ExpedientePadron expPadron = null;
        Ambito ambito = new Ambito();
        padron.setNumEle(numele);
        padron = padronDAO.find(padron);

        if (padron == null) {
            elector = electorDAO.fetchElectorbyDni(numele);
            if (elector != null) {
                expPadron.setDni(numele);
                expPadron.setApellidoPaterno(elector.getApellidoPaterno());
                expPadron.setApellidoMaterno(elector.getApellidoMaterno());
                expPadron.setNombre(elector.getNombre());
                expPadron.setUbigeoElector(elector.getUbigeo());
                //ubigeolista
                //ordenregistro
                //indicador
                //estado
                //observacion
                lista.setId(Long.valueOf(0));
                expPadron.setExpediente(lista);
                //ambito
            }
        } else {

            expPadron = expedientePadronDAO.fetchListaPadronByDni(numele);
            if (expPadron != null) {
                lista.setId(expPadron.getExpediente().getId());
                expPadron.setExpediente(lista);
                ambito.setId(expPadron.getAmbito().getId());
                expPadron.setAmbito(ambito);
            } else {
                expPadron = new ExpedientePadron();
                expPadron.setDni(numele);
                expPadron.setApellidoPaterno(padron.getApePat());
                expPadron.setApellidoMaterno(padron.getApMat());
                expPadron.setNombre(padron.getNombres());
                expPadron.setUbigeoElector(padron.getUbigeo());

                lista.setId(Long.valueOf(0));
                expPadron.setExpediente(lista);
                ambito.setId(Long.valueOf(0));
                expPadron.setAmbito(ambito);
            }

        }

        if (expPadron == null) {
            return new ResponseEntity<ExpedientePadron>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<ExpedientePadron>(expPadron, HttpStatus.OK);

    }

    @RequestMapping(value = "/registro/fetchElector/{numele}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Elector> fetchElectorByDni(HttpServletRequest request, @PathVariable("numele") String numele) {

        List<String> result = new ArrayList<String>();

        Elector elector = electorDAO.fetchElectorbyDni(numele);
        if (elector == null) {
            return new ResponseEntity<Elector>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Elector>(elector, HttpStatus.OK);
    }

//    @RequestMapping(value = "/registro/fetchListaPadron/{numele}", method = RequestMethod.GET)
//    @ResponseBody
//    public ExpedientePadron fetchListaPadronByDni(HttpServletRequest request, @PathVariable("numele") String numele) {
//        ExpedientePadron listaPadron = new ExpedientePadron();
//
//        listaPadron.setDni(numele);
//        listaPadron = expedientePadronDAO.find(listaPadron);
//        return listaPadron;
//    }
    @RequestMapping(value = "fetchUbigeo/{ubiPadre}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Ubigeo>> fetchUbigeoByUbiPadre(HttpServletRequest request, @PathVariable("ubiPadre") String ubiPadre) {
        List<Ubigeo> lstUbigeo;
        lstUbigeo = ubigeoDAO.fetchUbigeoByUbiPadre(ubiPadre);
        return new ResponseEntity<List<Ubigeo>>(lstUbigeo, HttpStatus.OK);
    }

    @RequestMapping(value = "fetchAmbitosReg/{ambitoPadre}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Ambito>> fetchAmbitosReg(HttpServletRequest request, @PathVariable("ambitoPadre") Long ambitoPadre) {
        List<Ambito> lstAmbito;
        lstAmbito = ambitoDAO.buscarAmbitosRegElector(ambitoPadre);
        return new ResponseEntity<List<Ambito>>(lstAmbito, HttpStatus.OK);
    }

    @RequestMapping("/registro/registrar")
    public ModelAndView selectList(HttpServletRequest request) {
        ModelAndView view;
        view = new ModelAndView("registro/index");
        view.addObject("lstDptos", new Gson().toJson(ubigeoDAO.fetchDepartamentos()));
        view.addObject("dptos", new Gson().toJson(ambitoDAO.listarDepartamento()));
        return view;
    }

//    @RequestMapping(value = "/listarProvincias/{ubigeo}", method = RequestMethod.GET)
//    @ResponseBody
//    public List<Ambito> listarProvincias(HttpServletRequest request, @PathVariable("ubigeo") String ubigeo) {
//        List<Ambito> provincias;
//        Ambito ambito = new Ambito();
//        ambito.setUbigeo(ubigeo);
//        provincias = ambitoDAO.listarProvincia(ambito);
//        return provincias;
//    }               
    @RequestMapping(value = "listarProvincias/{ubigeo}", method = RequestMethod.GET)
    public ResponseEntity<List<Ambito>> listarProvincias(@PathVariable String ubigeo) {
        Ambito ambito = new Ambito();
        ambito.setUbigeo(ubigeo);
        List<Ambito> provincias = ambitoDAO.listarProvincia(ambito);
        return new ResponseEntity<List<Ambito>>(provincias, HttpStatus.OK);
    }

    @RequestMapping(value = "listarDistritos/{ubigeo}", method = RequestMethod.GET)
    public ResponseEntity<List<Ambito>> listarDistritos(@PathVariable String ubigeo) {
        Ambito ambito = new Ambito();
        ambito.setUbigeo(ubigeo);
        List<Ambito> distritos = ambitoDAO.listarDistrito(ambito);
        return new ResponseEntity<List<Ambito>>(distritos, HttpStatus.OK);
    }

    @RequestMapping(value = "listarCCPP/{ubigeo}", method = RequestMethod.GET)
    public ResponseEntity<List<Ambito>> listarCCPP(@PathVariable String ubigeo) {
        Ambito ambito = new Ambito();
        ambito.setUbigeo(ubigeo);
        List<Ambito> ambitos = ambitoDAO.listarCCPP(ambito);
        return new ResponseEntity<List<Ambito>>(ambitos, HttpStatus.OK);
    }

    @RequestMapping(value = "listarExpedientes/{ccpp}", method = RequestMethod.GET)
    public ResponseEntity<List<Expediente>> listarExpedientes(@PathVariable Long ccpp) {
        Ambito ambito = new Ambito();
        ambito.setId(ccpp);
        Expediente expediente = new Expediente();
        expediente.setAmbito(ambito);
        List<Expediente> listas = expedienteDAO.listarExpediente(expediente);
        return new ResponseEntity<List<Expediente>>(listas, HttpStatus.OK);
    }

    @RequestMapping(value = "buscarUbigeo/{ubigeo}", method = RequestMethod.GET)
    public ResponseEntity<List<Ambito>> buscarUbigeo(@PathVariable String ubigeo) {
        Ambito ambito = new Ambito();
        ambito.setDepartamento(ubigeo);
        List<Ambito> ambitos = ambitoDAO.buscarUbigeo(ambito);
        return new ResponseEntity<List<Ambito>>(ambitos, HttpStatus.OK);
    }

    @RequestMapping(value = "buscarCCPP/{nombre}", method = RequestMethod.GET)
    public ResponseEntity<List<Ambito>> buscarLocal(@PathVariable String nombre) {
        Ambito ambito = new Ambito();
        ambito.setNombreAmbito(nombre);
        List<Ambito> lstAmbitos = new ArrayList<Ambito>();
        List<Ambito> ambitos = ambitoDAO.buscarCCPPP(ambito);
        for (Ambito a : ambitos) {
            a.setExpedientes(null);
            a.setExpedientesPadron(null);
            a.setLocales(null);
            lstAmbitos.add(a);
        }
        return new ResponseEntity<List<Ambito>>(lstAmbitos, HttpStatus.OK);

    }

    @RequestMapping(value = "eliminarElector/", method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<ExpedientePadron> eliminar(@RequestBody ExpedientePadron elector) {
        try {
            if (elector.getId() != null) {
                expedientePadronDAO.updateDAO(elector);
                return new ResponseEntity<ExpedientePadron>(HttpStatus.OK);
            } else {
                return new ResponseEntity<ExpedientePadron>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception ex) {
            return new ResponseEntity<ExpedientePadron>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "fetchExpediente/{id}", method = RequestMethod.GET)
    public ResponseEntity<Expediente> fetchExpediente(@PathVariable Long id) {
        Expediente expediente = new Expediente();
        expediente.setId(id);
        expediente = expedienteDAO.find(expediente);
        expediente.setAmbito(expediente.getAmbito());

        expediente.getAmbito().setExpedientes(null);
        expediente.getAmbito().setExpedientesPadron(null);
        expediente.getAmbito().setLocales(null);
        expediente.setExpedientesImpresion(null);
        expediente.setExpedientesPadron(null);

        return new ResponseEntity<Expediente>(expediente, HttpStatus.OK);
    }

    @RequestMapping(value = "listarElectoresPrevia/", method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<List<ExpedientePadron>> listarElectoresPrevia(@RequestBody ExpedientePadron expedientePadron) {
        try {
            if (expedientePadron.getExpediente().getId() != null) {

                int ultPagina = expedienteImpresionDAO.getCountPaginas(expedientePadron.getExpediente().getId().intValue(), expedientePadron.getIndicador());
                int ultimoReg = 0;
                if (ultPagina > 0) {
                    ultimoReg = expedienteImpresionDAO.getRegistroFinal(expedientePadron.getExpediente().getId().intValue(), expedientePadron.getIndicador(), ultPagina);
                }
                List<ExpedientePadron> electores = expedientePadronDAO.listarElectores(expedientePadron, ultimoReg);
                return new ResponseEntity<List<ExpedientePadron>>(electores, HttpStatus.OK);
            } else {
                return new ResponseEntity<List<ExpedientePadron>>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception ex) {
            return new ResponseEntity<List<ExpedientePadron>>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/exportarExpediente/{expediente}", method = RequestMethod.GET)
    public void exportarExpediente(HttpServletRequest request, @PathVariable("expediente") int expediente, HttpServletResponse response) {
        try {
            Report report = new Report();
            report.setCode("RPT01");
            report.setLabel("REGISTRO DE ELECTORES");
            report.setDescription("LISTA DE ELECTORES REGISTRADOS");

            //List<ExpedientePadron> electores = expedientePadronDAO.exportarExpediente(expediente);
            List electores = reporteDAO.exportarExpediente(expediente);
            String gson = new Gson().toJson(electores);

            IFactory factory = new FactoryService();
            IExcelGenerator excelGenerator = factory.createExcelGenerator(report, gson, null, true, null);
            HSSFWorkbook workbook = excelGenerator.createFromJson();

            response.setContentType("application/vnd.ms-excel");

            String attachment = String.format("attachment; filename=%s.xls", "REGISTRO DE ELECTORES");
            response.setHeader("Content-Disposition", attachment);
            workbook.write(response.getOutputStream());
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

    @RequestMapping(value = "/countExpedientePadron/{expediente}", method = RequestMethod.GET)
    public ResponseEntity<Integer> countExpedientePadron(@PathVariable int expediente) {
        int result = expedientePadronDAO.getCountExportarExpediente(expediente);
        return new ResponseEntity<Integer>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "countVPDiferenteUbigeo/{codExpediente}", method = RequestMethod.GET)
    public ResponseEntity<Integer> countVPDiferenteUbigeo(@PathVariable int codExpediente) {
        int ultPagina = expedienteImpresionDAO.getCountPaginas(codExpediente, Parametros.N_INDICADOR_DIFERENTE_UBIGEO);
        int result = 0;
        if (ultPagina > 0) {
            result = expedienteImpresionDAO.getRegistroFinal(codExpediente, Parametros.N_INDICADOR_DIFERENTE_UBIGEO, ultPagina);
        }
        return new ResponseEntity<Integer>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "getContadores/{codExpediente}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Integer>> getContadores(@PathVariable int codExpediente) {
        List<Integer> lstContadores = new ArrayList<Integer>();
        int ultPagina;
        int ultimoReg;

        int iTotalDifUbigeo = expedientePadronDAO.fetchCountExpedPadron(codExpediente, Parametros.N_INDICADOR_DIFERENTE_UBIGEO);
        int iTotalMismoUbigeo = expedientePadronDAO.fetchCountExpedPadron(codExpediente, Parametros.N_INDICADOR_MISMO_UBIGEO);

        ultPagina = expedienteImpresionDAO.getCountPaginas(codExpediente, Parametros.N_INDICADOR_DIFERENTE_UBIGEO);
        ultimoReg = 0;
        if (ultPagina > 0) {
            ultimoReg = expedienteImpresionDAO.getRegistroFinal(codExpediente, Parametros.N_INDICADOR_DIFERENTE_UBIGEO, ultPagina);
        }
        int iTotalVPDifUbigeo = iTotalDifUbigeo - ultimoReg;

        ultPagina = expedienteImpresionDAO.getCountPaginas(codExpediente, Parametros.N_INDICADOR_MISMO_UBIGEO);
        ultimoReg = 0;
        if (ultPagina > 0) {
            ultimoReg = expedienteImpresionDAO.getRegistroFinal(codExpediente, Parametros.N_INDICADOR_MISMO_UBIGEO, ultPagina);
        }
        int iTotalVPMismoUbigeo = iTotalMismoUbigeo - ultimoReg;
        lstContadores.add(iTotalMismoUbigeo);
        lstContadores.add(iTotalVPMismoUbigeo);
        lstContadores.add(iTotalDifUbigeo);
        lstContadores.add(iTotalVPDifUbigeo);

        return new ResponseEntity<List<Integer>>(lstContadores, HttpStatus.OK);
    }

    @RequestMapping(value = "fetchExpPadByCodExpediente/{codExpediente}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<ExpedientePadron>> fetchExpPadByCodExp(@PathVariable int codExpediente) {
        List<ExpedientePadron> result = new ArrayList<ExpedientePadron>();
        result = expedientePadronDAO.fetchByExpediente(codExpediente);
        return new ResponseEntity<List<ExpedientePadron>>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "registrarElector/", method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<ExpedientePadron> registrarElector(HttpServletRequest request, @RequestBody ExpedientePadron elector) {
        try {

            HttpSession session = request.getSession(false);
            Usuario usu = (Usuario) session.getAttribute("USUARIO_AUTENTICADO");
            if (usu != null) {
                ExpedientePadron expPadron = null;
                Padron padron = new Padron();
                Elector nEle = null;
                padron.setNumEle(elector.getDni());
                padron = padronDAO.find(padron);

                if (padron == null) {
                    elector.setObservacion(addObservacion(elector.getObservacion(), Parametros.CODE_PARAM_NO_PADRON_NAC));
                    nEle = electorDAO.fetchElectorbyDni(elector.getDni());
                    if (nEle == null) {
                        nEle = new Elector();
                        nEle.setDni(elector.getDni());
                        nEle.setApellidoPaterno(elector.getApellidoPaterno());
                        nEle.setApellidoMaterno(elector.getApellidoMaterno());
                        nEle.setNombre(elector.getNombre());
                        nEle.setUbigeo(elector.getUbigeoElector());
                        electorDAO.saveDAO(nEle);
                    }
                }

                expPadron = expedientePadronDAO.fetchListaPadronByDni(elector.getDni());
                if (expPadron != null) {
                    expPadron.setEstado(Parametros.ESTADO_ELECTOR_REG_OTRA_LISTA);
                    expedientePadronDAO.updateDAO(expPadron);
                }

                if (padron != null) {
                    if (padron.getRestric() != null) {
                        elector.setObservacion(addObservacion(elector.getObservacion(), Parametros.CODE_PARAM_DNI_RESTRINGIDO));
                    }
                }

                elector.setEstado(Parametros.ESTADO_ELECTOR_ACTIVO);
                if (!elector.getUbigeoElector().equals(elector.getUbigeoLista())) {
                    elector.setEstado(Parametros.ESTADO_ELECTOR_PENDIENTE);
                    elector.setObservacion(addObservacion(elector.getObservacion(), Parametros.CODE_PARAM_NO_PADRON_DIST));
                    elector.setOrdenRegistro(expedientePadronDAO.fetchCountExpedPadron((elector.getExpediente().getId()).intValue(), 1) + 1);
                    elector.setIndicador(Parametros.N_INDICADOR_DIFERENTE_UBIGEO);
                } else {
                    elector.setOrdenRegistro(expedientePadronDAO.fetchCountExpedPadron((elector.getExpediente().getId()).intValue(), 0) + 1);
                    elector.setIndicador(Parametros.N_INDICADOR_MISMO_UBIGEO);
                }
                if (usu.getPerfil().getId() == Parametros.PERFIL_AMBITO) {
                    elector.setFlag(Parametros.FLAG_ELECTOR_PENDIENTE);
                } else {
                    elector.setFlag(Parametros.FLAG_ELECTOR_VALIDO);
                }
                expedientePadronDAO.saveDAO(elector);
                return new ResponseEntity<ExpedientePadron>(HttpStatus.OK);
            } else {
                return new ResponseEntity<ExpedientePadron>(HttpStatus.NO_CONTENT);
            }

        } catch (Exception ex) {
            return new ResponseEntity<ExpedientePadron>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "updateElector/", method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<ExpedientePadron> update(HttpServletRequest request, @RequestBody ExpedientePadron elector) {
        try {
            HttpSession session = request.getSession(false);
            Usuario usu = (Usuario) session.getAttribute("USUARIO_AUTENTICADO");

            if (usu != null) {

                if (elector.getId() != null) {

                    if (usu.getPerfil().getId() == Parametros.PERFIL_AMBITO) {
                        elector.setFlag(Parametros.FLAG_ELECTOR_PENDIENTE);
                    } else {
                        elector.setFlag(Parametros.FLAG_ELECTOR_VALIDO);
                    }

                    expedientePadronDAO.updateDAO(elector);
                    return new ResponseEntity<ExpedientePadron>(HttpStatus.OK);
                } else {
                    return new ResponseEntity<ExpedientePadron>(HttpStatus.NO_CONTENT);
                }
            } else {
                return new ResponseEntity<ExpedientePadron>(HttpStatus.NO_CONTENT);
            }

        } catch (Exception ex) {
            return new ResponseEntity<ExpedientePadron>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "printDifUbigeo/{codExpediente}", method = RequestMethod.GET)
    public void getPrintDifUbigeo(HttpServletRequest request, @PathVariable("codExpediente") int codExpediente, HttpServletResponse response) throws IOException {

        try {
            ServletContext sc = request.getSession().getServletContext();
            InputStream inStream = sc.getResourceAsStream("/WEB-INF/jasper/ListaImpresion_no_pertenecientes_15.jasper");
            byte[] bytes = getByteVP(codExpediente, 1, 0, inStream);
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            ServletOutputStream outStream = response.getOutputStream();
            outStream.write(bytes, 0, bytes.length);
            outStream.flush();
            outStream.close();

        } catch (Exception ex) {
            Logger.getLogger(registroController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @RequestMapping(value = "printMismoUbigeo/{codExpediente}", method = RequestMethod.GET)
    public void getPrintMismoUbigeo(HttpServletRequest request, @PathVariable("codExpediente") int codExpediente, HttpServletResponse response) throws IOException {

        try {
            ServletContext sc = request.getSession().getServletContext();
            InputStream inStream = sc.getResourceAsStream("/WEB-INF/jasper/ListaImpresion_pertenecientes_15.jasper");

            byte[] bytes = getByteVP(codExpediente, 0, 0, inStream);
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            ServletOutputStream outStream = response.getOutputStream();
            outStream.write(bytes, 0, bytes.length);
            outStream.flush();
            outStream.close();
        } catch (Exception ex) {
            Logger.getLogger(registroController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @RequestMapping(value = "printDifUbigeo2/{codExpediente}", method = RequestMethod.GET)
    public void getPrintDifUbigeo2(HttpServletRequest request, @PathVariable("codExpediente") int codExpediente, HttpServletResponse response) throws IOException {

        try {
            ServletContext sc = request.getSession().getServletContext();
            InputStream inStream = sc.getResourceAsStream("/WEB-INF/jasper/ListaImpresion_ubigeo.jasper");

            byte[] bytes = getByteVP(codExpediente, 1, 1, inStream);
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            ServletOutputStream outStream = response.getOutputStream();
            outStream.write(bytes, 0, bytes.length);
            outStream.flush();
            outStream.close();
        } catch (Exception ex) {
            Logger.getLogger(registroController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @RequestMapping(value = "getCompaginadosMUbigeo/{codExpediente}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<ExpedienteImpresion>> getCompaginadosMUbigeo(@PathVariable int codExpediente) {
        List<ExpedienteImpresion> result = new ArrayList<ExpedienteImpresion>();
        result = expedienteImpresionDAO.getCompaginados(codExpediente, Parametros.N_INDICADOR_MISMO_UBIGEO);
        return new ResponseEntity<List<ExpedienteImpresion>>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "getCompaginadosDUbigeo/{codExpediente}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<ExpedienteImpresion>> getCompaginadosDUbigeo(@PathVariable int codExpediente) {
        List<ExpedienteImpresion> result = new ArrayList<ExpedienteImpresion>();
        result = expedienteImpresionDAO.getCompaginados(codExpediente, Parametros.N_INDICADOR_DIFERENTE_UBIGEO);
        return new ResponseEntity<List<ExpedienteImpresion>>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "getElectoresByCompaginado/{codCompaginado}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<ExpedientePadron>> getElectoresByCompaginado(@PathVariable int codCompaginado) {
        ExpedienteImpresion expImpresion = new ExpedienteImpresion(codCompaginado);
        expImpresion = expedienteImpresionDAO.find(expImpresion);

        List<ExpedientePadron> result = new ArrayList<ExpedientePadron>();
        result = expedientePadronDAO.getByCompaginado(expImpresion);
        for (int i = 0; i < result.size(); i++) {
            result.get(i).setExpediente(new Expediente(result.get(i).getExpediente().getId()));
            result.get(i).setAmbito(new Ambito(result.get(i).getAmbito().getId()));
        }
        return new ResponseEntity<List<ExpedientePadron>>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "getElectoresTodos/{codExpediente}/{indicador}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<ExpedientePadron>> getElectoresTodos(@PathVariable int codExpediente, @PathVariable int indicador) {
        Expediente expediente = new Expediente(codExpediente);

        List<ExpedientePadron> result = new ArrayList<ExpedientePadron>();
        result = expedientePadronDAO.getByExpedienteIndicador(expediente, indicador);
        for (ExpedientePadron ePadron : result) {
            ePadron.setExpediente(new Expediente(ePadron.getExpediente().getId()));
            ePadron.setAmbito(new Ambito(ePadron.getAmbito().getId()));
        }
        return new ResponseEntity<List<ExpedientePadron>>(result, HttpStatus.OK);

    }

    @RequestMapping(value = "getElectoresByRango/{codExpediente}/{indicador}/{rangoInicial}/{rangoFinal}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<ExpedientePadron>> getElectoreByRango(@PathVariable int codExpediente, @PathVariable int indicador, @PathVariable int rangoInicial, @PathVariable int rangoFinal) {
        Expediente expediente = new Expediente(codExpediente);
        ExpedienteImpresion expImp = new ExpedienteImpresion(0);
        expImp.setExpediente(expediente);
        expImp.setIndicador(indicador);
        expImp.setRegistroInicial(rangoInicial);
        expImp.setResgitroFinal(rangoFinal);

        List<ExpedientePadron> result = new ArrayList<ExpedientePadron>();
        result = expedientePadronDAO.getByCompaginado(expImp);
        for (int i = 0; i < result.size(); i++) {
            result.get(i).setExpediente(new Expediente(result.get(i).getExpediente().getId()));
            result.get(i).setAmbito(new Ambito(result.get(i).getAmbito().getId()));
        }
        return new ResponseEntity<List<ExpedientePadron>>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "generateFVDoc/{codExpediente}", method = RequestMethod.GET)
    public void generateFVDoc(HttpServletRequest request, @PathVariable("codExpediente") int codExpediente, HttpServletResponse response) {
        try {
            ServletContext sc = request.getSession().getServletContext();

            Expediente expediente = new Expediente(codExpediente);
            expediente = expedienteDAO.find(expediente);

            Ambito amb = new Ambito(expediente.getAmbito().getId());
            amb = ambitoDAO.find(amb);

            int totalElectoresRemitidos = expedientePadronDAO.getCountByExpediente(expediente);
            int totalElectoresIncorporados = expedientePadronDAO.getCountByExpedienteAndEstado(expediente, Parametros.ESTADO_ELECTOR_ACTIVO);

            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = (JsonObject) jsonParser.parse(amb.getInformacion());
            String nombre = jsonObject.get("nombres").toString() + " " + jsonObject.get("apellidoPaterno").toString() + " " + jsonObject.get("apellidoMaterno").toString();

            InputStream is = registroController.class.getResourceAsStream("/ejemplo.docx");
            XWPFDocument document = new XWPFDocument(is);

            XWPFHeaderFooterPolicy policy = document.getHeaderFooterPolicy();
            if (policy == null) {
                CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
                policy = new XWPFHeaderFooterPolicy(document, sectPr);
            }

            if (policy.getDefaultHeader() == null && policy.getFirstPageHeader() == null
                    && policy.getDefaultFooter() == null) {
                XWPFFooter footerD = policy.getFooter(1);// createFooter(policy.DEFAULT);
                XWPFRun run = footerD.getParagraphs().get(0).createRun();
                run.setText("usuario");
                XWPFParagraph paragraph = footerD.createParagraph();
                paragraph.setAlignment(ParagraphAlignment.DISTRIBUTE);
                run = paragraph.createRun();
                run.setFontFamily("Arial");
                run.setFontSize(8);
                run.setText("Jr.Washington N° 1894, Cercado de Lima. Central Telefonica: 417-0630 www.onpe.gob.pe informes@onpe.gob.pe");

            }

            XWPFParagraph paragraph = document.createParagraph();

            XWPFRun run = paragraph.createRun();
            run.setFontSize(11);
            run.setFontFamily("Arial");
            run.setText("Lima,");
            run.addBreak();

            paragraph = document.createParagraph();
            run = paragraph.createRun();
            run.setFontSize(11);
            run.setFontFamily("Arial");
            run.setBold(true);
            run.setText("OFICIO N°       -2016-GPP/ONPE");
            run.setUnderline(UnderlinePatterns.SINGLE);
            run.addBreak();

            paragraph = document.createParagraph();
            run = paragraph.createRun();
            run.setFontSize(11);
            run.setFontFamily("Arial");
            run.setText("Señor");

            XWPFRun run1 = paragraph.createRun();
            run1.setFontSize(11);
            run1.setFontFamily("Arial");
            run1.setText(nombre.replace("\"", ""));
            run1.setBold(true);
            run1.addBreak();

            XWPFRun run2 = paragraph.createRun();
            run2.setFontSize(11);
            run2.setFontFamily("Arial");
            run2.setText(jsonObject.get("cargo").toString().replace("\"", ""));
            run2.addBreak();
            run2.setText("Centro Poblado " + amb.getNombreAmbito());
            run2.addBreak();
            run2.setText("Av. 28 de Julio S/N Centro Cívico Huacrachuco - Municipalidad Provincial de " + amb.getProvincia());
            run2.addBreak();
            run2.setText(amb.getDepartamento() + " - " + amb.getProvincia() + " - " + amb.getDistrito());
            run2.addBreak();

            run2 = paragraph.createRun();
            run2.setFontSize(11);
            run2.setFontFamily("Arial");
            run2.setUnderline(UnderlinePatterns.WORDS);
            run2.setText("Presente");

            run2 = paragraph.createRun();
            run2.setFontSize(11);
            run2.setFontFamily("Arial");
            run2.setText(".-");

            paragraph = document.createParagraph();
            run.addBreak();
            run = paragraph.createRun();
            run.setFontSize(11);
            run.setFontFamily("Arial");
            run.addBreak();
            run.setText("Asunto");
            run.addTab();
            run.addTab();
            run.setText(": SOLICITUD DE CREACIÓN DE MESA DE SUFRAGIO.");
            run.addBreak();

            paragraph = document.createParagraph();
            run = paragraph.createRun();
            run.setFontSize(11);
            run.setFontFamily("Arial");
            run.setText("Referencia");
            run.addTab();
            run.setText(": OFICIO N° 087-2016/M-CP.CHOCOBAMBA (16AGO2016) - Exp. " + expediente.getExpediente());
            run.addBreak();

            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.THAI_DISTRIBUTE);
            run = paragraph.createRun();
            run.setFontSize(11);
            run.setFontFamily("Arial");
            run.setText("Me dirijo a usted con relación al documento de la referencia con la finalidad de hacer de su "
                    + "conocimiento que se ha cumplido con todos los requisitos que dan inicio al trámite de "
                    + "instalación de mesas de sufragio en el Centro Poblado " + amb.getNombreAmbito() + ", distrito " + amb.getDistrito() + ", "
                    + "provincia " + amb.getProvincia() + ", departamento " + amb.getDepartamento() + ".");
            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.THAI_DISTRIBUTE);
            run = paragraph.createRun();
            run.setFontSize(11);
            run.setFontFamily("Arial");
            run.addBreak();
            run.setText("Al respecto, el mencionado expediente contiene un listado de electores que solicitan ser "
                    + "parte de la mesa de sufragio de la localidad " + amb.getNombreAmbito() + ", el cual, luego de la validación "
                    + "realizada, se informa que podrán ser incorporados " + totalElectoresIncorporados + " electores del total de " + totalElectoresRemitidos + " registros "
                    + "de electores remitidos. Se adjunta un cuadro resumen con las observaciones mencionadas.");
            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.THAI_DISTRIBUTE);
            run = paragraph.createRun();
            run.setFontSize(11);
            run.setFontFamily("Arial");
            run.addBreak();
            run.setText("Asimismo, se programará un viaje para la verificación de rutas, tiempos y servicios de la "
                    + "localidad, la cual se coordinará previamente con las autoridades del centro poblado a fin de "
                    + "programarla adecuadamente; luego de lo cual se emitirá un informe de respuesta al "
                    + "resultado de la solicitud, que de ser positivo, conllevaría a la instalación de mesas de sufragio "
                    + "en el centro poblado en mención, con miras a las ");
            run = paragraph.createRun();
            run.setFontSize(11);
            run.setFontFamily("Arial");
            run.setBold(true);
            run.setText("Elecciones Regionales y Municipales de 2018.");
            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.THAI_DISTRIBUTE);
            run = paragraph.createRun();
            run.setFontSize(11);
            run.setFontFamily("Arial");
            run.addBreak();
            run.setText("Finalmente, de requerir mayor información, agradeceremos se comunique con nosotros al "
                    + "telefono 417-0630 anexo 8484 o al 8481.");
            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.THAI_DISTRIBUTE);
            run = paragraph.createRun();
            run.setFontSize(11);
            run.setFontFamily("Arial");
            run.addBreak();
            run.setText("Sin otro particular.");

            paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.THAI_DISTRIBUTE);
            run = paragraph.createRun();
            run.setFontSize(11);
            run.setFontFamily("Arial");
            run.addBreak();
            run.addBreak();
            run.setText("Atentamente,");
            response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            document.write(response.getOutputStream());
        } catch (Exception ex) {
            Logger.getLogger(registroController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @RequestMapping(value = "generarFichaVerificacion/{codExpediente}", method = RequestMethod.GET)
    public void generarFichaVerificacion(HttpServletRequest request, @PathVariable("codExpediente") int codExpediente, HttpServletResponse response) {
        try {

            ServletContext sc = request.getSession().getServletContext();
            InputStream inStream = sc.getResourceAsStream("/WEB-INF/jasper/ficha_verificacion.jasper");

            Expediente expediente = new Expediente(codExpediente);
            expediente = expedienteDAO.find(expediente);

            Ambito amb = new Ambito(expediente.getAmbito().getId());
            amb = ambitoDAO.find(amb);

            List<RElector> lstRElector = new ArrayList<RElector>();

            Parametros.N_PARAM_TAB_EXP_PADRON_OBSERVACION_2 = getNParametro(Parametros.N_PARAM_TAB_EXP_PADRON_OBSERVACION_2, Parametros.N_PARAM_PK_TAB_EXP_PADRON_OBSERVACION_2);
            lstRElector = getElectoresByObservacion(expediente, Parametros.N_PARAM_TAB_EXP_PADRON_OBSERVACION_2, lstRElector);                  

            Parametros.N_PARAM_TAB_EXP_PADRON_OBSERVACION_3 = getNParametro(Parametros.N_PARAM_TAB_EXP_PADRON_OBSERVACION_3, Parametros.N_PARAM_PK_TAB_EXP_PADRON_OBSERVACION_3);
            lstRElector = getElectoresByObservacion(expediente, Parametros.N_PARAM_TAB_EXP_PADRON_OBSERVACION_3, lstRElector);           

            Parametros.N_PARAM_TAB_EXP_PADRON_OBSERVACION_5 = getNParametro(Parametros.N_PARAM_TAB_EXP_PADRON_OBSERVACION_5, Parametros.N_PARAM_PK_TAB_EXP_PADRON_OBSERVACION_5);
            lstRElector = getElectoresByObservacion(expediente, Parametros.N_PARAM_TAB_EXP_PADRON_OBSERVACION_5, lstRElector);           

            HashMap<String, Object> parameters = new HashMap();
            parameters.put("TITLE_REPORT", RaeConstants.N_TITLE_REPORT_OBS);
            parameters.put("CCPP", amb.getNombreAmbito());
            parameters.put("DISTRITO", amb.getDistrito());
            parameters.put("EXPEDIENTE", expediente.getExpediente());
            parameters.put("YEAR", expediente.getFechaExpediente().toString().substring(0, 4));
            byte[] bytes = PrintUtil.generarPdf(parameters, lstRElector, inStream);

            response.setContentLength(bytes.length);
            ServletOutputStream outStream = response.getOutputStream();
            outStream.write(bytes, 0, bytes.length);
            outStream.flush();
            outStream.close();
        } catch (Exception ex) {
            Logger.getLogger(registroController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private List<RElector> getElectoresByObservacion(Expediente expediente, Parametro paramObservacion, List<RElector> lstRElector) {
        List<ExpedientePadron> lstElectores = expedientePadronDAO.getElectores(expediente, String.valueOf(paramObservacion.getCodigo()));
        for (int i = 0; i < lstElectores.size(); i++) {
            RElector elec = new RElector();
            elec.setOrdenRegistro(i + 1);
            elec.setObservacion(paramObservacion.getTitulo());
            elec.setApellidoPaterno(lstElectores.get(i).getApellidoPaterno());
            elec.setApellidoMaterno(lstElectores.get(i).getApellidoMaterno());
            elec.setNombre(lstElectores.get(i).getNombre());
            elec.setDocumentoIdentidad(lstElectores.get(i).getDni());
            elec.setSubsanarObservacion(paramObservacion.getObservacion());
            lstRElector.add(elec);
        }
        return lstRElector;
    }

    @RequestMapping(value = "reImprimir/{codExpediente}/{indicador}/{pageUbigeo}/{page}/{rInicio}/{rFinal}", method = RequestMethod.GET)
    public void reImprimir(HttpServletRequest request, @PathVariable("codExpediente") int codExpediente, @PathVariable("indicador") int indicador, @PathVariable("pageUbigeo") int pageUbigeo, @PathVariable("page") int page, @PathVariable("rInicio") int rInicio, @PathVariable("rFinal") int rFinal, HttpServletResponse response) throws IOException {

        try {

            ServletContext sc = request.getSession().getServletContext();
            InputStream inStream = null;

            if (pageUbigeo == Parametros.N_INDICADOR_DIFERENTE_UBIGEO) {
                inStream = sc.getResourceAsStream("/WEB-INF/jasper/ListaImpresion_ubigeo.jasper");
            } else {
                if (indicador == Parametros.N_INDICADOR_DIFERENTE_UBIGEO) {
                    inStream = sc.getResourceAsStream("/WEB-INF/jasper/ListaImpresion_no_pertenecientes_15.jasper");
                } else {
                    inStream = sc.getResourceAsStream("/WEB-INF/jasper/ListaImpresion_pertenecientes_15.jasper");
                }
            }

            Expediente expediente = new Expediente(codExpediente);
            expediente = expedienteDAO.find(expediente);

            ExpedienteImpresion expImp = null;
            if (page > 0) {
                expImp = new ExpedienteImpresion(page);
                expImp = expedienteImpresionDAO.find(expImp);
                page = expImp.getPagina() - 1;
            }

            expImp = new ExpedienteImpresion(0);
            expImp.setExpediente(expediente);
            expImp.setIndicador(indicador);
            expImp.setRegistroInicial(rInicio);
            expImp.setResgitroFinal(rFinal);

            List<ExpedientePadron> lstElectores = new ArrayList<ExpedientePadron>();
            lstElectores = expedientePadronDAO.getByCompaginado(expImp);

            HashMap<String, Object> parameters = new HashMap();
            parameters.put("ubigeo", expediente.getAmbito().getDepartamento() + " / " + expediente.getAmbito().getProvincia() + " / " + expediente.getAmbito().getDistrito());
            parameters.put("lista", expediente.getAmbito().getUbigeo() + ": " + expediente.getExpediente());
            parameters.put("local", RaeConstants.SUBTITTLE_REPORT + expediente.getAmbito().getNombreAmbito());
            parameters.put("titulo", RaeConstants.N_TITLE_REPORT);
            parameters.put("pagina", page);

            List<RElector> lstElector = new ArrayList<RElector>();
            for (int i = 0; i < lstElectores.size(); i++) {
                RElector elec = new RElector();
                elec.setOrdenRegistro(lstElectores.get(i).getOrdenRegistro());
                elec.setDocumentoIdentidad(lstElectores.get(i).getDni());
                elec.setApellidoPaterno(lstElectores.get(i).getApellidoPaterno());
                elec.setApellidoMaterno(lstElectores.get(i).getApellidoMaterno());
                elec.setNombre(lstElectores.get(i).getNombre());
                if (indicador == Parametros.N_INDICADOR_DIFERENTE_UBIGEO) {
                    elec.setDistritoElector((ubigeoDAO.findByCodeUbigeo(lstElectores.get(i).getUbigeoElector())).getDescripcion());
                }

                lstElector.add(elec);
            }
            byte[] bytes = PrintUtil.generarPdf(parameters, lstElector, inStream);
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            //response.setHeader("Content-Disposition", "inline; filename=\\asghd.pdf");
            ServletOutputStream outStream = response.getOutputStream();
            outStream.write(bytes, 0, bytes.length);
            outStream.flush();
            outStream.close();

        } catch (Exception ex) {
            Logger.getLogger(registroController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private byte[] getByteVP(int codExpediente, int indicador, int pageUbigeo, InputStream inStream) throws Exception {
        Expediente exped = new Expediente(codExpediente);
        exped = expedienteDAO.find(exped);

        int pageReport = 0;
        int ultPagina;
        int ultimoReg = 0;
        ultPagina = expedienteImpresionDAO.getCountPaginas(codExpediente, indicador);
        pageReport = ultPagina;
        ultimoReg = 0;

        if (ultPagina > 0) {
            ultimoReg = expedienteImpresionDAO.getRegistroFinal(codExpediente, indicador, ultPagina);
        }
        List<ExpedientePadron> lstElectores = (List<ExpedientePadron>) expedientePadronDAO.fetchByRangoInicialInd(exped, indicador, ultimoReg);

        List<RElector> lstElector = new ArrayList<RElector>();

        for (int i = 0; i < lstElectores.size(); i++) {
            RElector elec = new RElector();
            elec.setOrdenRegistro(lstElectores.get(i).getOrdenRegistro());
            elec.setDocumentoIdentidad(lstElectores.get(i).getDni());
            elec.setApellidoPaterno(lstElectores.get(i).getApellidoPaterno());
            elec.setApellidoMaterno(lstElectores.get(i).getApellidoMaterno());
            elec.setNombre(lstElectores.get(i).getNombre());
            if (indicador == Parametros.N_INDICADOR_DIFERENTE_UBIGEO && pageUbigeo == 1) {
                elec.setDistritoElector((ubigeoDAO.findByCodeUbigeo(lstElectores.get(i).getUbigeoElector())).getDescripcion());
            }

            lstElector.add(elec);
        }

        int countFaltantes = lstElectores.size();
        while (countFaltantes > 0) {
            int inicio = ultimoReg + 1;
            boolean reg = false;
            if (countFaltantes - 15 > 0) {
                ultPagina++;

                ExpedienteImpresion impresion = new ExpedienteImpresion();
                impresion.setRegistroInicial(inicio);
                impresion.setResgitroFinal(ultimoReg + 15);
                impresion.setIndicador(indicador);
                impresion.setPagina(ultPagina);
                impresion.setExpediente(exped);
                if (indicador == 1 && pageUbigeo == 1) {
                    expedienteImpresionDAO.saveDAO(impresion);
                }
                ultimoReg = +15;
                countFaltantes = countFaltantes - 15;
            } else {
                ultPagina++;

                ExpedienteImpresion impresion = new ExpedienteImpresion();
                impresion.setRegistroInicial(inicio);
                impresion.setResgitroFinal(ultimoReg + countFaltantes);
                impresion.setIndicador(indicador);
                impresion.setPagina(ultPagina);
                impresion.setExpediente(exped);
                if (indicador == 1 && pageUbigeo == 1) {
                    expedienteImpresionDAO.saveDAO(impresion);
                }
                countFaltantes = 0;
            }
        }

        HashMap<String, Object> parameters = new HashMap();
        parameters.put("ubigeo", exped.getAmbito().getDepartamento() + " / " + exped.getAmbito().getProvincia() + " / " + exped.getAmbito().getDistrito());
        parameters.put("lista", exped.getAmbito().getUbigeo() + ": " + exped.getExpediente());
        parameters.put("local", RaeConstants.SUBTITTLE_REPORT + exped.getAmbito().getNombreAmbito());
        parameters.put("titulo", RaeConstants.N_TITLE_REPORT);
        parameters.put("pagina", pageReport);
        //parameters.put("PATH_LOGO", pathLogo);      //parameters.put("urlLogo", BASE_URL_EMAIL_IMG + LOGO_ONPE_REPORTS);

        byte[] bytes = PrintUtil.generarPdf(parameters, lstElector, inStream);
        return bytes;
    }

    @RequestMapping("vistaPrevia")
    public String vistaPrevia() {
        return "registro/vistaPrevia";
    }

    private String addObservacion(String obs, int code) {
        if (obs.equals("")) {
            obs = obs + code;
        } else {
            obs = obs + "," + code;
        }
        return obs;
    }

    @RequestMapping("nuevoElector")
    public String modalElector() {
        return "registro/registroForm";
    }
}
