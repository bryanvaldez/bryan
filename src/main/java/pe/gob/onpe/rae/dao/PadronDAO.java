/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.onpe.rae.dao;

import java.util.List;
import pe.gob.onpe.rae.helper.GenericDAO;
import pe.gob.onpe.rae.model.Padron;

/**
 *
 * @author AQuispec
 */
public interface PadronDAO extends GenericDAO<Padron>{
    public List<Padron> findByDNI(String numele);
    public List<Padron> findByPadron(Padron padron);
}
