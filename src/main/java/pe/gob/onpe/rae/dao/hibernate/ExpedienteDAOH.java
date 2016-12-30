package pe.gob.onpe.rae.dao.hibernate;

import java.math.BigDecimal;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import pe.gob.onpe.rae.dao.ExpedienteDAO;
import pe.gob.onpe.rae.model.Ambito;
import pe.gob.onpe.rae.model.Expediente;
import pe.gob.onpe.rae.model.Local;

/**
 * 
 * @author Bryan Valdez <bvaldez at onpe.gob.pe>
 */
@Repository
public class ExpedienteDAOH extends HibernateTemplate implements ExpedienteDAO{

    @Autowired    
    public ExpedienteDAOH(SessionFactory sessionFactory) {
        super (sessionFactory);
    }
    
    @Override
    public Expediente find(Expediente t) {
        Criteria criteria = this.getSession().createCriteria(Expediente.class);
        criteria.add(Restrictions.eq("id", t.getId()));
        return (Expediente) criteria.uniqueResult();
    }

    @Override
    public List<Expediente> all() {
        Criteria criteria = this.getSession().createCriteria(Expediente.class);
        return criteria.list();  
    }        

    @Override
    public void saveDAO(Expediente t) {
        this.save(t);
    }

    @Override
    public void updateDAO(Expediente t) {
        this.merge(t);
    }

    @Override
    public void deleteDAO(Expediente t) {
        this.delete(t);
    }
    
    @Override
    public List<Expediente> all(Long id) {
        Criteria criteria = this.getSession().createCriteria(Expediente.class);
        criteria.add(Restrictions.eq("id", id));
        return criteria.list();  
    }

    @Override
    public List<Expediente> allByAmbito(Ambito ambito) {        
        Criteria criteria = this.getSession().createCriteria(Expediente.class);
        criteria.add(Restrictions.eq("ambito", ambito));        
        return (List<Expediente>) criteria.list();                       
    }          
    
    @Override
    public List<Expediente> allByAmbitoByText(Ambito ambito, String text) {
        String sql = "SELECT * FROM (SELECT E.N_EXPEDIENTE_PK AS ID, A.C_NOMBRE_AMBITO || ' - ' || E.C_EXPEDIENTE AS EXPEDIENTE FROM TAB_AMBITO A INNER JOIN TAB_EXPEDIENTE E ON E.N_AMBITO = A.N_AMBITO_PK AND A.N_AMBITO_PADRE IS NULL" 
                + " WHERE A.N_AMBITO_PK = :ambito) WHERE EXPEDIENTE LIKE :text";
        SQLQuery query = this.getSession().createSQLQuery(sql);
        query.setParameter("ambito", ambito.getId());
        query.setParameter("text", "%" + text + "%");
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return (List<Expediente>)  query.list();
    } 
    
    @Override
    public List<Expediente> allByExpediente(Long id) {        
        Criteria criteria = this.getSession().createCriteria(Expediente.class);
        criteria.add(Restrictions.eq("id", id));        
        return (List<Expediente>) criteria.list();                       
    }
    
    @Override
    public List<Expediente> allByEstado(int estado) {
        Criteria criteria = this.getSession().createCriteria(Expediente.class);
        criteria.add(Restrictions.eq("estado", estado));        
        return (List<Expediente>)  criteria.list();                       
    }
    
    @Override
    public List<Expediente> listarExpediente(Expediente lista) {
        String sql = "SELECT N_EXPEDIENTE_PK AS ID, C_EXPEDIENTE AS EXPEDIENTE FROM TAB_EXPEDIENTE WHERE N_AMBITO = :ambito ORDER BY EXPEDIENTE";
        SQLQuery query = this.getSession().createSQLQuery(sql);
        query.setParameter("ambito", lista.getAmbito().getId());
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Expediente> list = query.list();
        return list;                
    }                       
    
    @Override
    public void updateExpediente() {
        String sql = "UPDATE TAB_EXPEDIENTE SET N_ESTADO = 0";
        SQLQuery query = this.getSession().createSQLQuery(sql);
        query.executeUpdate();
    }
    
    @Override
    public Integer countExpedienteByNombre(String expediente){
        String sql = "select count(n_expediente_pk) from tab_expediente where c_expediente like :expediente";
        SQLQuery query = this.getSession().createSQLQuery(sql);
        query.setParameter("expediente",expediente + "%");
        return  (Integer) ((BigDecimal) query.uniqueResult()).intValueExact();
    }
}