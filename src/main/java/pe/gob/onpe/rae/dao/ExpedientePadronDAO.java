/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.onpe.rae.dao;

import java.util.List;
import pe.gob.onpe.rae.helper.GenericDAO;
import pe.gob.onpe.rae.model.Expediente;
import pe.gob.onpe.rae.model.ExpedienteImpresion;
import pe.gob.onpe.rae.model.ExpedientePadron;

/**
 *
 * @author Manuel Arrisueno <marrisueno at onpe.gob.pe>
 */
public interface ExpedientePadronDAO extends GenericDAO<ExpedientePadron> {

    public List<ExpedientePadron> all(Expediente expediente);

    public List<ExpedientePadron> listarElectores(ExpedientePadron listaPadron, int iOrdenMayorQue);

    public ExpedientePadron fetchListaPadronByDni(String numele);

    public List<ExpedientePadron> exportarExpediente(int id);
    
    public Integer getCountExportarExpediente(int id);

    public Integer fetchCountExpedPadron(int expediente, int indicador);

    public List<ExpedientePadron> fetchByExpediente(int codExpediente);
    
    public List<ExpedientePadron> allPendientes(Expediente id);

    public List<ExpedientePadron> fetchByRangoInicialInd(Expediente expediente, int indicador, int desde);
    
    public List<ExpedientePadron> getByCompaginado(ExpedienteImpresion expImpresion);
    
    public List<ExpedientePadron> getByExpedienteIndicador(Expediente expediente, int indicador);
    
    public List<ExpedientePadron> getElectores(Expediente expediente, String observacion);

    public List<ExpedientePadron> getExpedientesByAmbitoAdjunto(Expediente expediente);
    
    public int getCountByExpedienteAndEstado(Expediente expediente,int estado);
    
    public int getCountByExpediente(Expediente expediente);
}
