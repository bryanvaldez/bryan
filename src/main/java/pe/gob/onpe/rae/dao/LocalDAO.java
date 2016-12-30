/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.onpe.rae.dao;

import java.util.ArrayList;
import java.util.List;
import pe.gob.onpe.rae.helper.GenericDAO;
import pe.gob.onpe.rae.model.Ambito;
import pe.gob.onpe.rae.model.Local;

/**
 *
 * @author Bryan Valdez <bvaldez at onpe.gob.pe>
 */
public interface LocalDAO extends GenericDAO<Local>{
    public List<Local> listarDepartamento();
    public List<Local> listarProvincia(Local local);
    public List<Local> listarDistrito(Local local);
    public List<Local> listarLocal(Local local);
    public ArrayList allDepartamento();
    public ArrayList allProvincia();
    public ArrayList allDistrito();
    public List<Local> allLocal();
    public List<Local> buscarUbigeo(Local local);
    public List<Local> buscarLocal(Local local);
    public List<Local> findByAmbito(Ambito ambito);
    public List<Local> allByAmbitoByText(Ambito ambito, String text);
    public List<Local> allByLocal(Long id);
    public void updateLocalById(Local local);
}

