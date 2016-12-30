package pe.gob.onpe.rae.dao.hibernate;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import pe.gob.onpe.rae.dao.ElectorDAO;
import pe.gob.onpe.rae.model.Elector;

/**
 * 
 * @author Bryan Valdez <bvaldez at onpe.gob.pe>
 */
@Repository
public class ElectorDAOH extends HibernateTemplate implements ElectorDAO{

    @Autowired    
    public ElectorDAOH(SessionFactory sessionFactory) {
        super (sessionFactory);
    }
    
    @Override
    public Elector find(Elector t) {
        Criteria criteria = this.getSession().createCriteria(Elector.class);
        criteria.add(Restrictions.eq("id", t.getId()));
        return (Elector) criteria.uniqueResult();
    }

    @Override
    public List<Elector> all() {
        Criteria criteria = this.getSession().createCriteria(Elector.class);
        return criteria.list();
    }

    @Override
    public void saveDAO(Elector t) {
        this.save(t);
    }

    @Override
    public void updateDAO(Elector t) {
        this.merge(t);
    }

    @Override
    public void deleteDAO(Elector t) {
        this.delete(t);
    }

    @Override
    public Elector fetchElectorbyDni(String numEle){
        Criteria criteria = this.getSession().createCriteria(Elector.class);
        criteria.add(Restrictions.eq("dni", numEle));
        return (Elector) criteria.uniqueResult();
    }
}
