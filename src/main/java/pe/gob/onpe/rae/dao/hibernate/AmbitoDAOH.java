package pe.gob.onpe.rae.dao.hibernate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import pe.gob.onpe.rae.dao.AmbitoDAO;
import pe.gob.onpe.rae.model.Ambito;
import pe.gob.onpe.rae.model.AmbitoNew;

/**
 *
 * @author Bryan Valdez <bvaldez at onpe.gob.pe>
 */
@Repository
public class AmbitoDAOH extends HibernateTemplate implements AmbitoDAO {

    @Autowired
    public AmbitoDAOH(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Ambito find(Ambito t) {
        Criteria criteria = this.getSession().createCriteria(Ambito.class);
        criteria.add(Restrictions.eq("id", t.getId()));
        return (Ambito) criteria.uniqueResult();
    }

    @Override
    public List<Ambito> all() {
        Criteria criteria = this.getSession().createCriteria(Ambito.class);
        return criteria.list();
    }

    @Override
    public void saveDAO(Ambito t) {
        this.save(t);
    }

    @Override
    public void updateDAO(Ambito t) {
        this.merge(t);
    }

    @Override
    public void deleteDAO(Ambito t) {
        this.delete(t);
    }

    @Override
    public List<Ambito> fetchAmbitoByAmbPadre(Long ambPadre) {
        Criteria criteria = this.getSession().createCriteria(Ambito.class);
        criteria.add(Restrictions.eq("ambitoPadre", ambPadre));
        return (List<Ambito>) criteria.list();
    }

    @Override
    public List<Ambito> listarDepartamento() {
        String sql = "SELECT SUBSTR(C_UBIGEO,1,2) AS UBIGEO, C_DEPARTAMENTO AS DEPARTAMENTO"
                + " FROM TAB_AMBITO"
                + " GROUP BY SUBSTR(C_UBIGEO,1,2), C_DEPARTAMENTO ORDER BY 2";
        SQLQuery query = this.getSession().createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Ambito> list = query.list();
        return list;
    }
//    
//    @Override
//    public List<Ambito> getAmbitosHijos(Ambito t) {        
//        Criteria criteria = this.getSession().createCriteria(Ambito.class);
//        criteria.add(Restrictions.eq("ambitoPadre", t.getId()));
//        return criteria.list();        
//    }    

    @Override
    public List<Ambito> listarProvincia(Ambito ambito) {
        String sql = "SELECT  SUBSTR(C_UBIGEO,1,4) as UBIGEO, C_PROVINCIA AS PROVINCIA"
                + " FROM TAB_AMBITO WHERE SUBSTR(C_UBIGEO,1,2) = :ubigeo"
                + " GROUP BY SUBSTR(C_UBIGEO,1,4),C_PROVINCIA ORDER BY 2";
        SQLQuery query = this.getSession().createSQLQuery(sql);
        query.setParameter("ubigeo", ambito.getUbigeo());
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Ambito> list = query.list();
        return list;
    }

    @Override
    public List<Ambito> listarDistrito(Ambito ambito) {
        String sql = "SELECT C_UBIGEO AS UBIGEO, C_DISTRITO AS DISTRITO FROM TAB_AMBITO WHERE SUBSTR(C_UBIGEO,1,4) = :ubigeo"
                + " GROUP BY C_UBIGEO,C_DISTRITO ORDER BY 2";
        SQLQuery query = this.getSession().createSQLQuery(sql);
        query.setParameter("ubigeo", ambito.getUbigeo());
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Ambito> list = query.list();
        return list;
    }

    @Override
    public List<Ambito> listarCCPP(Ambito ambito) {
        String sql = "SELECT N_AMBITO_PK AS ID, C_NOMBRE_AMBITO AS CCPP FROM TAB_AMBITO WHERE N_AMBITO_PADRE IS NULL AND C_UBIGEO = :ubigeo";
        SQLQuery query = this.getSession().createSQLQuery(sql);
        query.setParameter("ubigeo", ambito.getUbigeo());
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Ambito> list = query.list();
        return list;
    }

    @Override
    public List<Ambito> buscarUbigeo(Ambito ambito) {
        String sql = "SELECT * FROM (SELECT N_AMBITO_PK AS ID, C_UBIGEO AS UBIGEO, C_DEPARTAMENTO||' '||C_PROVINCIA||' '||C_DISTRITO AS DESCRIPCION FROM TAB_AMBITO WHERE N_AMBITO_PADRE IS NULL) "
                + "WHERE DESCRIPCION LIKE :ambito";
        SQLQuery query = this.getSession().createSQLQuery(sql);
        query.setParameter("ambito", "%" + ambito.getDepartamento().toUpperCase() + "%");
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Ambito> list = query.list();
        return list;
    }

    @Override
    public List<Ambito> buscarCCPP(Ambito ambito) {
//        String sql = "SELECT * FROM TAB_AMBITO WHERE N_AMBITO_PADRE IS NULL AND C_NOMBRE_AMBITO LIKE :ambito";
//        SQLQuery query = this.getSession().createSQLQuery(sql);
//        query.setParameter("ambito", "%" + ambito.getNombreAmbito().toUpperCase() + "%");
//        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
//        List<Ambito> list = query.list();
//        return list;

        Criteria criteria = this.getSession().createCriteria(Ambito.class);
        criteria.add(Restrictions.like("nombreAmbito", ambito.getNombreAmbito(), MatchMode.ANYWHERE));
        return (List<Ambito>) criteria.list();
    }

    @Override
    public List<Ambito> buscarAmbitosRegElector(Long ambPadre) {
        String sql = "SELECT N_AMBITO_PK, C_NOMBRE_AMBITO  FROM TAB_AMBITO WHERE N_AMBITO_PK = :N_AMBITO_PK OR N_AMBITO_PADRE = :N_AMBITO_PADRE";
        SQLQuery query = this.getSession().createSQLQuery(sql);
        query.setParameter("N_AMBITO_PK", ambPadre);
        query.setParameter("N_AMBITO_PADRE", ambPadre);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Ambito> list = query.list();
        return list;
    }

    @Override
    public List<Ambito> allAmbitoByText(String text) {
        String sql = "SELECT N_AMBITO_PK AS ID, C_NOMBRE_AMBITO AS DESCRIPCION, C_DEPARTAMENTO AS DPTO, C_PROVINCIA AS PROV, C_DISTRITO AS DIST FROM TAB_AMBITO WHERE C_NOMBRE_AMBITO LIKE :text";
        SQLQuery query = this.getSession().createSQLQuery(sql);
        query.setParameter("text", "%" + text + "%");
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return (List<Ambito>) query.list();
    }

//    @Override
//    public List<Ambito> allAmbitoPrincipal() {
//        String sql = "SELECT * FROM TAB_AMBITO WHERE N_AMBITO_PADRE IS NULL";
//        SQLQuery query = this.getSession().createSQLQuery(sql);
//        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
//        List<Ambito> list = query.list();
//        return list;   
//    }
    @Override
    public Ambito findById(Long id) {
        String sql = "SELECT N_AMBITO_PK AS id, C_NOMBRE_AMBITO AS nombreAmbito, N_AMBITO_PADRE AS ambitoPadre FROM TAB_AMBITO WHERE N_AMBITO_PK = :N_AMBITO_PK";
        SQLQuery query = this.getSession().createSQLQuery(sql);
        query.setParameter("N_AMBITO_PK", id);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

        GsonBuilder builder = new GsonBuilder();
        String json = builder.create().toJson(query.uniqueResult());

        Gson g = new Gson();
        AmbitoNew temp = g.fromJson(json, AmbitoNew.class);

        Ambito ambito = new Ambito();
        ambito.setId(temp.getID());
        ambito.setNombreAmbito(temp.getNOMBREAMBITO());
        ambito.setAmbitoPadre(temp.getAMBITOPADRE());

        return ambito;
    }

    @Override
    public List<Ambito> allAmbitoAnyPadre() {
        Criteria criteria = this.getSession().createCriteria(Ambito.class);
        criteria.add(Restrictions.isNull("ambitoPadre"));
        return (List<Ambito>) criteria.list();
    }

    @Override
    public List<Ambito> buscarCCPPP(Ambito ambito) {
//        String sql = "SELECT * FROM TAB_AMBITO WHERE N_AMBITO_PADRE IS NULL AND C_NOMBRE_AMBITO LIKE :ambito";
//        SQLQuery query = this.getSession().createSQLQuery(sql);
//        query.setParameter("ambito", "%" + ambito.getNombreAmbito().toUpperCase() + "%");
//        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
//        List<Ambito> list = query.list();
//        return list;
        Criteria criteria = this.getSession().createCriteria(Ambito.class);
        criteria.add(Restrictions.isNull("ambitoPadre"));
        criteria.add(Restrictions.like("nombreAmbito", "%" + ambito.getNombreAmbito().toUpperCase() + "%"));
        return (List<Ambito>) criteria.list();

    }
    
    @Override
    public List<Ambito> buscarCCPPPP(Ambito ambito) {
        String sql = "SELECT * FROM TAB_AMBITO WHERE N_AMBITO_PADRE IS NULL AND C_NOMBRE_AMBITO LIKE :ambito";
        SQLQuery query = this.getSession().createSQLQuery(sql);
        query.setParameter("ambito", "%" + ambito.getNombreAmbito().toUpperCase() + "%");
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Ambito> list = query.list();
        return list;

    }    
}
