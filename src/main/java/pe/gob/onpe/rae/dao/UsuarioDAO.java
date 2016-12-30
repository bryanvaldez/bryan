/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.onpe.rae.dao;

import pe.gob.onpe.rae.helper.GenericDAO;
import pe.gob.onpe.rae.model.Usuario;

/**
 *
 * @author Bryan Valdez <bvaldez at onpe.gob.pe>
 */
public interface UsuarioDAO extends GenericDAO<Usuario>{
    Usuario findByLogin(String Usuario);
    Usuario findById(Long id);
}
