/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.onpe.rae.dao;

import java.util.List;
import pe.gob.onpe.rae.helper.GenericDAO;
import pe.gob.onpe.rae.model.ExpedienteImpresion;

/**
 *
 * @author Bryan Valdez <bvaldez at onpe.gob.pe>
 */
public interface ExpedienteImpresionDAO extends GenericDAO<ExpedienteImpresion> {

    public Integer getCountPaginas(int codExpediente, int indicador);

    public Integer getRegistroFinal(int codExpediente, int indicador, int pagina);

    public List<ExpedienteImpresion> getCompaginados(int codExpediente, int indicador);
}
