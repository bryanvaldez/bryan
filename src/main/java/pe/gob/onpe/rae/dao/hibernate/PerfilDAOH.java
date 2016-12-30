/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.onpe.rae.dao.hibernate;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import pe.gob.onpe.rae.dao.PerfilDAO;
import pe.gob.onpe.rae.model.Perfil;

/**
 *
 * @author Bryan Valdez <bvaldez at onpe.gob.pe>
 */
@Repository
public class PerfilDAOH extends HibernateTemplate implements PerfilDAO{
    
    @Autowired    
    public PerfilDAOH(SessionFactory sessionFactory) {
        super (sessionFactory);
    }   
    
    @Override
    public Perfil find(Perfil t) {
        Criteria criteria = this.getSession().createCriteria(Perfil.class);
        criteria.add(Restrictions.eq("id", t.getId()));
        return (Perfil) criteria.uniqueResult(); 
    }

    @Override
    public List<Perfil> all() {
        Criteria criteria = this.getSession().createCriteria(Perfil.class);
        return criteria.list(); 
    }

    @Override
    public void saveDAO(Perfil t) {
        this.save(t);
    }

    @Override
    public void updateDAO(Perfil t) {
        this.merge(t);
    }

    @Override
    public void deleteDAO(Perfil t) {
        this.delete(t);
    }
    
}
