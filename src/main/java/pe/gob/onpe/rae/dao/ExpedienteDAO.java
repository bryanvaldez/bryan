/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.onpe.rae.dao;

import java.util.List;
import pe.gob.onpe.rae.helper.GenericDAO;
import pe.gob.onpe.rae.model.Expediente;
import pe.gob.onpe.rae.model.Ambito;

/**
 *
 * @author Bryan Valdez <bvaldez at onpe.gob.pe>
 */
public interface ExpedienteDAO extends GenericDAO<Expediente>{
    public List<Expediente> all(Long id);    
    public List<Expediente> allByAmbito(Ambito ambito);
    public List<Expediente> allByAmbitoByText(Ambito ambito, String text);
    public List<Expediente> allByExpediente(Long id);
    public List<Expediente> allByEstado(int estado);
    public List<Expediente> listarExpediente(Expediente lista);
    public void updateExpediente();
    public Integer countExpedienteByNombre(String expediente);
}
