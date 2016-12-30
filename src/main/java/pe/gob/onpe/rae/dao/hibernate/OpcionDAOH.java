package pe.gob.onpe.rae.dao.hibernate;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import pe.gob.onpe.rae.dao.OpcionDAO;
import pe.gob.onpe.rae.model.Opcion;

/**
 * 
 * @author Bryan Valdez <bvaldez at onpe.gob.pe>
 */
public class OpcionDAOH extends HibernateTemplate implements OpcionDAO{

    @Autowired    
    public OpcionDAOH(SessionFactory sessionFactory) {
        super (sessionFactory);
    }       
    
    @Override
    public Opcion find(Opcion t) {
        Criteria criteria = this.getSession().createCriteria(Opcion.class);
        criteria.add(Restrictions.eq("id", t.getId()));
        return (Opcion) criteria.uniqueResult();
    }

    @Override
    public List<Opcion> all() {
        Criteria criteria = this.getSession().createCriteria(Opcion.class);
        return criteria.list(); 
    }

    @Override
    public void saveDAO(Opcion t) {
        this.save(t);
    }

    @Override
    public void updateDAO(Opcion t) {
        this.merge(t);
    }

    @Override
    public void deleteDAO(Opcion t) {
        this.delete(t);
    }

}
