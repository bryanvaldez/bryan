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
import pe.gob.onpe.rae.dao.ExpedienteImpresionDAO;
import pe.gob.onpe.rae.model.ExpedienteImpresion;

/**
 * 
 * @author Bryan Valdez <bvaldez at onpe.gob.pe>
 */
@Repository
public class ExpedienteImpresionDAOH extends HibernateTemplate implements ExpedienteImpresionDAO{

    @Autowired    
    public ExpedienteImpresionDAOH(SessionFactory sessionFactory) {
        super (sessionFactory);
    }
    
    @Override
    public ExpedienteImpresion find(ExpedienteImpresion t) {
        Criteria criteria = this.getSession().createCriteria(ExpedienteImpresion.class);
        criteria.add(Restrictions.eq("id", t.getId()));
        return (ExpedienteImpresion) criteria.uniqueResult();
    }

    @Override
    public List<ExpedienteImpresion> all() {
        Criteria criteria = this.getSession().createCriteria(ExpedienteImpresion.class);
        return criteria.list(); 
    }

    @Override
    public void saveDAO(ExpedienteImpresion t) {
        this.save(t);
    }

    @Override
    public void updateDAO(ExpedienteImpresion t) {
        this.merge(t);
    }

    @Override
    public void deleteDAO(ExpedienteImpresion t) {
        this.delete(t);
    }

    @Override
    public Integer getCountPaginas(int codExpediente, int indicador){
        String sql = "SELECT COUNT(N_EXPEDIENTE_IMPRESION_PK) FROM TAB_EXPEDIENTE_IMPRESION WHERE N_EXPEDIENTE=:expediente AND N_INDICADOR=:indicador";
        SQLQuery query = this.getSession().createSQLQuery(sql);
        query.setParameter("expediente", codExpediente);
        query.setParameter("indicador", indicador);
        return (Integer) ((BigDecimal) query.uniqueResult()).intValueExact();
    }
    
    @Override
    public Integer getRegistroFinal(int codExpediente, int indicador, int pagina){
        String sql = "SELECT N_REGISTRO_FINAL FROM TAB_EXPEDIENTE_IMPRESION WHERE N_EXPEDIENTE=:expediente AND N_INDICADOR=:indicador AND N_PAGINA=:pagina";
        SQLQuery query = this.getSession().createSQLQuery(sql);
        query.setParameter("expediente", codExpediente);
        query.setParameter("indicador", indicador);
        query.setParameter("pagina", pagina);
        return (Integer) ((BigDecimal) query.uniqueResult()).intValueExact();
    }
    
    @Override
    public List<ExpedienteImpresion> getCompaginados(int codExpediente, int indicador){
        String sql = "SELECT N_EXPEDIENTE_IMPRESION_PK AS ID,"
                + "N_REGISTRO_INICIAL AS REGISTROINICIAL,"
                + "N_REGISTRO_FINAL AS REGISTROFINAL,"
                + "N_PAGINA AS PAGINA FROM TAB_EXPEDIENTE_IMPRESION "
                + "WHERE N_EXPEDIENTE = :N_EXPEDIENTE AND N_INDICADOR =:N_INDICADOR ORDER BY N_EXPEDIENTE_IMPRESION_PK";
        SQLQuery query = this.getSession().createSQLQuery(sql);
        query.setParameter("N_EXPEDIENTE", codExpediente);
        query.setParameter("N_INDICADOR", indicador);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        return (List<ExpedienteImpresion>) query.list();
    }
}
