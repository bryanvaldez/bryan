/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.onpe.rae.dao;

import java.util.List;
import pe.gob.onpe.rae.model.Upload;


public interface UploadDAO {
    public List<pe.gob.onpe.excelvalidator.model.Upload> getColumns(int uploadType);
}