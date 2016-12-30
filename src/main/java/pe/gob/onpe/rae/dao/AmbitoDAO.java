/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.onpe.rae.dao;

import java.util.List;
import pe.gob.onpe.rae.helper.GenericDAO;
import pe.gob.onpe.rae.model.Ambito;

/**
 *
 * @author Bryan Valdez <bvaldez at onpe.gob.pe>
 */
public interface AmbitoDAO extends GenericDAO<Ambito> {

    public List<Ambito> fetchAmbitoByAmbPadre(Long ambPadre);

    public List<Ambito> listarDepartamento();
    
//    public List<Ambito> getAmbitosHijos(Ambito ambito);

    public List<Ambito> listarProvincia(Ambito ambito);

    public List<Ambito> listarDistrito(Ambito ambito);

    public List<Ambito> listarCCPP(Ambito ambito);
    
    public List<Ambito> buscarCCPPP(Ambito ambito);
    
    public List<Ambito> buscarCCPPPP(Ambito ambito);

    public List<Ambito> buscarUbigeo(Ambito ambito);

    public List<Ambito> buscarCCPP(Ambito ambito);
    
    public List<Ambito> buscarAmbitosRegElector(Long ambPadre);
    
    public List<Ambito> allAmbitoByText(String text);
        
//    public List<Ambito> allAmbitoPrincipal();
    
    public Ambito findById(Long id);
    
    public List<Ambito> allAmbitoAnyPadre();
}
