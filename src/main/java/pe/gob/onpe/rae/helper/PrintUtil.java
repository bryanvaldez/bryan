/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.onpe.rae.helper;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;




/**
 *
 * @author AQuispec
 */
public class PrintUtil {
    
    
    
    public static byte[] generarPdf(HashMap<String, Object> parameters, Object data, InputStream inStream) throws Exception {
        return JasperRunManager.runReportToPdf(inStream, parameters, new JRBeanCollectionDataSource((List) data));
    }
}
