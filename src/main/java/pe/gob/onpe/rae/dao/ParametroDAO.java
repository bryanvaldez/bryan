/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.onpe.rae.dao;

import java.util.List;
import pe.gob.onpe.rae.helper.GenericDAO;
import pe.gob.onpe.rae.model.Parametro;

/**
 *
 * @author Bryan Valdez <bvaldez at onpe.gob.pe>
 */
public interface ParametroDAO extends GenericDAO<Parametro>{
    Parametro findByProfile(String Parametro);
    public List<Parametro> findByType(int tipo);
    public Parametro getParametro(int id);
}
