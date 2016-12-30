package pe.gob.onpe.rae.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import pe.gob.onpe.rae.dao.LocalDAO;
import pe.gob.onpe.rae.model.Ambito;
import pe.gob.onpe.rae.model.Local;

/**
 * 
 * @author Bryan Valdez <bvaldez at onpe.gob.pe>
 */
@Repository
public class LocalDAOH extends HibernateTemplate implements LocalDAO{   
    
    @Autowired    
    public LocalDAOH(SessionFactory sessionFactory) {
        super (sessionFactory);
    } 
    
    @Override
    public Local find(Local t) {
        Criteria criteria = this.getSession().createCriteria(Local.class);
        criteria.add(Restrictions.eq("id", t.getId()));
        return (Local) criteria.uniqueResult();
    }

    @Override
    public List<Local> all() {
        Criteria criteria = this.getSession().createCriteria(Local.class);
        return criteria.list(); 
    }

    @Override
    public void saveDAO(Local t) {
        this.save(t);
    }

    @Override
    public void updateDAO(Local t) {
        this.merge(t);
    }

    @Override
    public void deleteDAO(Local t) {
        this.delete(t);
    }

    
    @Override
    public List<Local> listarDepartamento() {        
        String sql = "SELECT SUBSTR(C_UBIGEO,1,2) AS UBIGEO, C_DEPARTAMENTO AS DEPARTAMENTO"
                + " FROM TAB_LOCAL"
                + " GROUP BY SUBSTR(C_UBIGEO,1,2), C_DEPARTAMENTO ORDER BY 2";
        SQLQuery query = this.getSession().createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Local> list = query.list();
        return list;
    }
    
    @Override
    public List<Local> listarProvincia(Local local) {
        String sql = "SELECT  SUBSTR(C_UBIGEO,1,4) as UBIGEO, C_PROVINCIA AS PROVINCIA"
                + " FROM TAB_LOCAL WHERE SUBSTR(C_UBIGEO,1,2) = :ubigeo"
                + " GROUP BY SUBSTR(C_UBIGEO,1,4),C_PROVINCIA ORDER BY 2";
        SQLQuery query = this.getSession().createSQLQuery(sql);
        query.setParameter("ubigeo", local.getUbigeo());
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Local> list = query.list();
        return list;       
    }
    
    @Override
    public List<Local> listarDistrito(Local local) {
        String sql = "SELECT C_UBIGEO AS UBIGEO, C_DISTRITO AS DISTRITO FROM TAB_LOCAL WHERE SUBSTR(C_UBIGEO,1,4) = :ubigeo"
                + " GROUP BY C_UBIGEO,C_DISTRITO ORDER BY 2";
        SQLQuery query = this.getSession().createSQLQuery(sql);
        query.setParameter("ubigeo", local.getUbigeo());
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Local> list = query.list();
        return list;                
    }
    
    @Override
    public List<Local> listarLocal(Local local) {
        String sql = "SELECT N_LOCAL_PK AS ID, C_NOMBRE_LOCAL AS LOCAL FROM TAB_LOCAL WHERE C_UBIGEO = :ubigeo";
        SQLQuery query = this.getSession().createSQLQuery(sql);
        query.setParameter("ubigeo", local.getUbigeo());
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Local> list = query.list();
        return list;                
    }
    
    @Override
    public ArrayList allDepartamento() {        
        String sql = "SELECT DISTINCT RPAD(SUBSTR(c_ubigeo,1,2),6,'0') AS codigo, c_departamento AS descripcion"
                + " FROM tab_local"
                + " ORDER BY 2";
        SQLQuery query = this.getSession().createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        ArrayList list = new ArrayList(query.list());
        return list;
    }
    @Override
    public ArrayList allProvincia() {        
        String sql = "SELECT DISTINCT RPAD(SUBSTR(c_ubigeo,1,4),6,'0') AS codigo, RPAD(SUBSTR(c_ubigeo,1,2),6,'0') AS padre, c_provincia AS descripcion"
                + " FROM tab_local"
                + " ORDER BY 2";
        SQLQuery query = this.getSession().createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        ArrayList list = new ArrayList(query.list());
        return list;
    }
    
    @Override
    public ArrayList allDistrito() {        
        String sql = "SELECT DISTINCT c_ubigeo AS codigo, RPAD(SUBSTR(c_ubigeo,1,4),6,'0') AS padre, c_distrito AS descripcion"
                + " FROM tab_local"
                + " ORDER BY 2";
        SQLQuery query = this.getSession().createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        ArrayList list = new ArrayList(query.list());
        return list;
    }
    @Override
    public List<Local> allLocal() {        
        String sql = "SELECT n_local_pk AS id, c_nombre_local AS nombre, c_ubigeo AS ubigeo"
                + " FROM tab_local";
        SQLQuery query = this.getSession().createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Local> list = new ArrayList(query.list());
        return list;
    }
    public List<Local> buscarUbigeo(Local local) { 
        Criteria criteria = this.getSession().createCriteria(Local.class);
        criteria.add(Restrictions.like("ubigeo", local.getUbigeo(), MatchMode.START));
        return (List<Local>) criteria.list();                
    }
    
    @Override
    public List<Local> buscarLocal(Local local) { 
        Criteria criteria = this.getSession().createCriteria(Local.class);
        criteria.add(Restrictions.like("nombre", local.getNombre(), MatchMode.ANYWHERE));
        return (List<Local>) criteria.list();                
    }

    @Override
    public List<Local> findByAmbito(Ambito ambito) {
        Criteria criteria = this.getSession().createCriteria(Local.class);
        criteria.add(Restrictions.eq("ambito", ambito));
        return (List<Local>)  criteria.list();
    }
    
    @Override
    public List<Local> allByAmbitoByText(Ambito ambito, String text) {
        String sql = "SELECT * FROM (SELECT L.N_LOCAL_PK AS ID, L.C_DEPARTAMENTO || ' ' || L.C_PROVINCIA || ' ' || L.C_DISTRITO || ' - ' || L.C_NOMBRE AS LOCAL FROM TAB_LOCAL L" 
                + " WHERE L.N_AMBITO = :ambito) WHERE LOCAL LIKE :text";                    
        SQLQuery query = this.getSession().createSQLQuery(sql);
        query.setParameter("ambito", ambito.getId());
        query.setParameter("text", "%" + text + "%");
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return (List<Local>)  query.list();
    } 
    
    @Override
    public List<Local> allByLocal(Long id) {        
        Criteria criteria = this.getSession().createCriteria(Local.class);
        criteria.add(Restrictions.eq("id", id));        
        return (List<Local>) criteria.list();                       
    }
    
    @Override
    public void updateLocalById(Local local) {
        String sql = "UPDATE TAB_LOCAL SET N_AMBITO = :ambito WHERE N_LOCAL_PK = :local";
        SQLQuery query = this.getSession().createSQLQuery(sql);
        query.setParameter("ambito", local.getAmbito().getId());
        query.setParameter("local", local.getId());
        query.executeUpdate();                                
    }
}

