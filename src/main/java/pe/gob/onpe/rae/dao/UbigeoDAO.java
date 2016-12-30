/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.onpe.rae.dao;

import java.util.ArrayList;
import java.util.List;
import pe.gob.onpe.rae.helper.GenericDAO;
import pe.gob.onpe.rae.model.Ubigeo;
import pe.gob.onpe.rae.model.UbigeoVw;

/**
 *
 * @author AQuispec
 */
public interface UbigeoDAO extends GenericDAO<Ubigeo>{
    public List<Ubigeo> fetchUbigeoByUbiPadre(String ubiPadre);
    public List<Ubigeo> fetchDepartamentos();
    
    public ArrayList allDepartamento();
    public ArrayList getProvincia(String departamento);
    public ArrayList getDistrito(String provincia);
    UbigeoVw findByUbigeo(String ubigeo);
    public Ubigeo findByCodeUbigeo(String ubigeo);
}
