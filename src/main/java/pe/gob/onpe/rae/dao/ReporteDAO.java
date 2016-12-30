/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.onpe.rae.dao;

import java.util.List;
import pe.gob.onpe.rae.helper.GenericDAO;
import pe.gob.onpe.rae.model.Reporte;
import pe.gob.onpe.rae.model.json.Filtro;

/**
 *
 * @author Manuel Arrisueno <marrisueno at onpe.gob.pe>
 */
public interface ReporteDAO extends GenericDAO<Reporte>{
    public List execute(String sql, Filtro filter);
    public List exportarExpediente(int id);
}
