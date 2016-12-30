/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.onpe.rae.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import pe.gob.onpe.rae.dao.UbigeoDAO;
import pe.gob.onpe.rae.model.Ubigeo;
import pe.gob.onpe.rae.model.UbigeoVw;

/**
 *
 * @author AQuispec
 */
@Repository
public class UbigeoDAOH extends HibernateTemplate implements UbigeoDAO{

    @Autowired    
    public UbigeoDAOH(SessionFactory sessionFactory) {
        super (sessionFactory);
    }   
    
    @Override
    public Ubigeo find(Ubigeo t) {
        Criteria criteria = this.getSession().createCriteria(Ubigeo.class);
        criteria.add(Restrictions.eq("ubigeo", t.getUbigeo()));
        return (Ubigeo) criteria.uniqueResult();
    }

    @Override
    public List<Ubigeo> all() {
        Criteria criteria = this.getSession().createCriteria(Ubigeo.class);
        return criteria.list();  
    }

    @Override
    public void saveDAO(Ubigeo t) {
        this.save(t);
    }

    @Override
    public void updateDAO(Ubigeo t) {
        this.merge(t);
    }

    @Override
    public void deleteDAO(Ubigeo t) {
        this.delete(t);
    }
    
    @Override
    public List<Ubigeo> fetchUbigeoByUbiPadre(String ubiPadre){
        Criteria criteria = this.getSession().createCriteria(Ubigeo.class);
        criteria.add(Restrictions.eq("ubiPadre", ubiPadre));
        criteria.addOrder(Order.asc("descripcion"));
        return  criteria.list();
    }
    
    @Override
    public List<Ubigeo> fetchDepartamentos() {
        String sql = "SELECT * FROM UBIGEO WHERE UBIPADRE = '000000' AND UBIGEO != '000000' AND UBIGEO NOT LIKE '9%' ORDER BY DESCRIPCION";
        SQLQuery query = this.getSession().createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Ubigeo> lstUbigeo = query.list();
        return lstUbigeo;           
                       
    }

    @Override
    public ArrayList allDepartamento() {
        String sql = " SELECT DISTINCT RPAD(SUBSTR(C_UBIGEO_PK,1,2),6,'0') AS codigo, C_DEPARTAMENTO AS descripcion"
                + " FROM VW_UBIGEO"
                + " ORDER BY 2";
        SQLQuery query = this.getSession().createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        ArrayList list = new ArrayList(query.list());
        return list;
    }

    @Override
    public ArrayList getProvincia(String dep) {
        String sql = " SELECT DISTINCT RPAD(SUBSTR(C_UBIGEO_PK,1,4),6,'0') AS codigo, RPAD(SUBSTR(C_UBIGEO_PK,1,2),6,'0') AS padre, c_provincia AS descripcion"
                + " FROM VW_UBIGEO"
                + " WHERE RPAD(SUBSTR(C_UBIGEO_PK,1,2),6,'0') = :dep"
                + " ORDER BY 2";
        SQLQuery query = this.getSession().createSQLQuery(sql);
        query.setParameter("dep", dep);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);       
        ArrayList list = new ArrayList(query.list());
        return list;
    }

    @Override
    public ArrayList getDistrito(String prov) {
        String sql = " SELECT DISTINCT c_ubigeo_pk AS codigo, RPAD(SUBSTR(c_ubigeo_pk,1,4),6,'0') AS padre, c_distrito AS descripcion"
                + " FROM VW_UBIGEO"
                + " WHERE RPAD(SUBSTR(C_UBIGEO_PK,1,4),6,'0') = :prov"
                + " ORDER BY 2";
        SQLQuery query = this.getSession().createSQLQuery(sql);
        query.setParameter("prov", prov);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP); 
        ArrayList list = new ArrayList(query.list());
        return list;
    }
    
    @Override
    public UbigeoVw findByUbigeo(String ubigeo) {
        Criteria criteria = this.getSession().createCriteria(UbigeoVw.class);
        criteria.add(Restrictions.eq("id", ubigeo));
        return (UbigeoVw) criteria.uniqueResult();  
    }
    
    @Override
    public Ubigeo findByCodeUbigeo(String ubigeo) {
        Criteria criteria = this.getSession().createCriteria(Ubigeo.class);
        criteria.add(Restrictions.eq("ubigeo", ubigeo));
        return (Ubigeo) criteria.uniqueResult();  
    }
}

