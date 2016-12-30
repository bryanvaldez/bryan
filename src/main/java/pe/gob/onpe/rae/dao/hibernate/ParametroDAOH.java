package pe.gob.onpe.rae.dao.hibernate;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import pe.gob.onpe.rae.dao.ParametroDAO;
import pe.gob.onpe.rae.helper.TypesUtil;
import pe.gob.onpe.rae.model.Parametro;
import pe.gob.onpe.rae.model.Usuario;

/**
 * 
 * @author Bryan Valdez <bvaldez at onpe.gob.pe>
 */
@Repository
public class ParametroDAOH extends HibernateTemplate implements ParametroDAO{

    @Autowired    
    public ParametroDAOH(SessionFactory sessionFactory) {
        super (sessionFactory);
    }      
    
    @Override
    public Parametro find(Parametro t) {
        Criteria criteria = this.getSession().createCriteria(Parametro.class);
        criteria.add(Restrictions.eq("id", t.getId()));
        return (Parametro) criteria.uniqueResult();        
    }

    @Override
    public List<Parametro> all() {
        Criteria criteria = this.getSession().createCriteria(Parametro.class);
        return criteria.list(); 
    }

    @Override
    public void saveDAO(Parametro t) {
        this.save(t);
    }

    @Override
    public void updateDAO(Parametro t) {
        this.merge(t);
    }

    @Override
    public void deleteDAO(Parametro t) {
        this.delete(t);
    }

    @Override
    public Parametro findByProfile(String parametro) {
        Criteria criteria = this.getSession().createCriteria(Parametro.class);
        criteria.add(Restrictions.eq("valor", parametro));
        return (Parametro) criteria.uniqueResult();
    }
    
    @Override
    public List<Parametro> findByType(int tipo) {
        Criteria criteria = this.getSession().createCriteria(Parametro.class);
        criteria.add(Restrictions.eq("tipo", tipo));
        return (List<Parametro>) criteria.list();
    }    
    
    @Override
    public Parametro getParametro(int id) {
        Criteria criteria = this.getSession().createCriteria(Parametro.class);
        criteria.add(Restrictions.eq("id", TypesUtil.getDefaultLong(id)));
        return (Parametro) criteria.uniqueResult();
    }
}
