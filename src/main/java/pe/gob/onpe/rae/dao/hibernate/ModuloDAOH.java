package pe.gob.onpe.rae.dao.hibernate;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import pe.gob.onpe.rae.dao.ModuloDAO;
import pe.gob.onpe.rae.model.Modulo;

/**
 * 
 * @author Bryan Valdez <bvaldez at onpe.gob.pe>
 */
@Repository
public class ModuloDAOH extends HibernateTemplate implements ModuloDAO{
    
    @Autowired    
    public ModuloDAOH(SessionFactory sessionFactory) {
        super (sessionFactory);
    }    

    @Override
    public Modulo find(Modulo t) {
        Criteria criteria = this.getSession().createCriteria(Modulo.class);
        criteria.add(Restrictions.eq("id", t.getId()));
        return (Modulo) criteria.uniqueResult();             
    }

    @Override
    public List<Modulo> all() {
        Criteria criteria = this.getSession().createCriteria(Modulo.class);
        return criteria.list();         
    }

    @Override
    public void saveDAO(Modulo t) {
        this.save(t);
    }

    @Override
    public void updateDAO(Modulo t) {
        this.merge(t);
    }

    @Override
    public void deleteDAO(Modulo t) {
        this.delete(t);
    }

}
