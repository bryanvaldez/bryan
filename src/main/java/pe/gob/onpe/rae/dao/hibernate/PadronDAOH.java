/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.onpe.rae.dao.hibernate;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import pe.gob.onpe.rae.dao.PadronDAO;
import pe.gob.onpe.rae.model.Padron;

/**
 *
 * @author AQuispec
 */
@Repository
public class PadronDAOH extends HibernateTemplate implements PadronDAO {

    @Autowired
    public PadronDAOH(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Padron find(Padron t) {
        Criteria criteria = this.getSession().createCriteria(Padron.class);
        criteria.add(Restrictions.eq("numele", t.getNumEle()));
        return (Padron) criteria.uniqueResult();
    }

    @Override
    public List<Padron> all() {
        Criteria criteria = this.getSession().createCriteria(Padron.class);
        return criteria.list();
    }

    @Override
    public void saveDAO(Padron t) {
        this.save(t);
    }

    @Override
    public void updateDAO(Padron t) {
        this.merge(t);
    }

    @Override
    public void deleteDAO(Padron t) {
        this.delete(t);
    }
    
    @Override
    public List<Padron> findByDNI(String numele) {
        Criteria criteria = this.getSession().createCriteria(Padron.class);
        criteria.add(Restrictions.eq("numele", numele));
        return criteria.list();  
    }
    
    @Override
    public List<Padron> findByPadron(Padron padron) {
        Criteria criteria = this.getSession().createCriteria(Padron.class);
        if (padron.getApePat() != null) {
            criteria.add(Restrictions.like("apePat", padron.getApePat(), MatchMode.END));
        }
        if (padron.getApMat() != null) {
            criteria.add(Restrictions.like("apMat", padron.getApMat(), MatchMode.END));
        }            
        if (padron.getNombres() != null) {
            criteria.add(Restrictions.like("nombres", padron.getNombres(), MatchMode.END));
        }                 
        criteria.addOrder(Order.asc("apePat"));
        criteria.addOrder(Order.asc("apMat"));
        criteria.addOrder(Order.asc("nombres"));        
        criteria.setMaxResults(25);        
        return criteria.list();          
    }
}
