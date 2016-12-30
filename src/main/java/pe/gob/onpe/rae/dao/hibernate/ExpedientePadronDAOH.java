/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.onpe.rae.dao.hibernate;

import java.math.BigDecimal;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import pe.gob.onpe.rae.constant.Parametros;
import pe.gob.onpe.rae.dao.ExpedientePadronDAO;
import pe.gob.onpe.rae.model.Expediente;
import pe.gob.onpe.rae.model.ExpedienteImpresion;
import pe.gob.onpe.rae.model.ExpedientePadron;

/**
 *
 * @author Manuel Arrisueno <marrisueno at onpe.gob.pe>
 */
@Repository
public class ExpedientePadronDAOH extends HibernateTemplate implements ExpedientePadronDAO {

    @Autowired
    public ExpedientePadronDAOH(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public ExpedientePadron find(ExpedientePadron t) {
        Criteria criteria = this.getSession().createCriteria(ExpedientePadron.class);
        criteria.add(Restrictions.eq("id", t.getId()));
        return (ExpedientePadron) criteria.uniqueResult();
    }

    @Override
    public List<ExpedientePadron> all() {
        Criteria criteria = this.getSession().createCriteria(ExpedientePadron.class);
        return criteria.list();
    }

    @Override
    public List<ExpedientePadron> all(Expediente expediente) {
        Criteria criteria = this.getSession().createCriteria(ExpedientePadron.class);
        criteria.add(Restrictions.eq("expediente", expediente));
        return criteria.list();
    }

    @Override
    public void saveDAO(ExpedientePadron t) {
        this.save(t);
    }

    @Override
    public void updateDAO(ExpedientePadron t) {
        this.merge(t);
    }

    @Override
    public void deleteDAO(ExpedientePadron t) {
        this.delete(t);
    }

    @Override
    public List<ExpedientePadron> listarElectores(ExpedientePadron listaPadron, int iOrdenMayorQue) {
        String sql = "SELECT * FROM TAB_EXPEDIENTE_PADRON WHERE N_EXPEDIENTE = :lista AND N_INDICADOR = :indicador AND N_ESTADO NOT IN(:P_ELIMINADO,:P_RECHAZADO) AND N_ORDEN_REGISTRO>:ORDEN ORDER BY N_ORDEN_REGISTRO";
        SQLQuery query = this.getSession().createSQLQuery(sql);
        query.setParameter("lista", listaPadron.getExpediente().getId());
        query.setParameter("indicador", listaPadron.getIndicador());
        query.setParameter("P_ELIMINADO", Parametros.ESTADO_ELECTOR_ELIMINADO);
        query.setParameter("P_RECHAZADO", Parametros.ESTADO_ELECTOR_RECHAZADO);
        query.setParameter("ORDEN", iOrdenMayorQue);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<ExpedientePadron> list = query.list();
        return list;
    }

    @Override
    public ExpedientePadron fetchListaPadronByDni(String numele) {
        Criteria criteria = this.getSession().createCriteria(ExpedientePadron.class);
        criteria.add(Restrictions.eq("dni", numele));
        criteria.add(Restrictions.not(Restrictions.in("estado", new Integer[]{Parametros.ESTADO_ELECTOR_REG_OTRA_LISTA, Parametros.ESTADO_ELECTOR_ELIMINADO, Parametros.ESTADO_ELECTOR_RECHAZADO})));
        return (ExpedientePadron) criteria.uniqueResult();
    }

    @Override
    public List<ExpedientePadron> exportarExpediente(int id) {
        String sql = "SELECT N_ORDEN_REGISTRO, N_EXPEDIENTE, N_AMBITO, C_UBIGEO_ELECTOR, C_DOCUMENTO_IDENTIDAD, C_APELLIDO_PATERNO, C_APELLIDO_MATERNO, C_NOMBRE, N_ESTADO FROM TAB_EXPEDIENTE_PADRON WHERE N_EXPEDIENTE = :lista ORDER BY N_ORDEN_REGISTRO";
        SQLQuery query = this.getSession().createSQLQuery(sql);
        query.setParameter("lista", id);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<ExpedientePadron> list = query.list();
        return list;
    }

    @Override
    public Integer getCountExportarExpediente(int id){
        String sql = "SELECT COUNT(N_ORDEN_REGISTRO) FROM TAB_EXPEDIENTE_PADRON WHERE N_EXPEDIENTE = :lista ORDER BY N_ORDEN_REGISTRO";
        SQLQuery query = this.getSession().createSQLQuery(sql);
        query.setParameter("lista", id);
        return (Integer) ((BigDecimal) query.uniqueResult()).intValueExact();
        
    }
    
    @Override
    public Integer fetchCountExpedPadron(int expediente, int indicador) {
        String sql = "SELECT COUNT(C_DOCUMENTO_IDENTIDAD) FROM TAB_EXPEDIENTE_PADRON WHERE N_EXPEDIENTE = :expediente AND N_ESTADO NOT IN(:P_ELIMINADO,:P_RECHAZADO) AND N_INDICADOR = :indicador";
        SQLQuery query = this.getSession().createSQLQuery(sql);
        query.setParameter("expediente", expediente);
        //query.setParameter("P_OTRA_LISTA", Parametros.ESTADO_ELECTOR_REG_OTRA_LISTA);
        query.setParameter("P_ELIMINADO", Parametros.ESTADO_ELECTOR_ELIMINADO);
        query.setParameter("P_RECHAZADO", Parametros.ESTADO_ELECTOR_RECHAZADO);
        query.setParameter("indicador", indicador);
        return (Integer) ((BigDecimal) query.uniqueResult()).intValueExact();
    }

    @Override
    public List<ExpedientePadron> fetchByExpediente(int codExpediente) {
        String sql = "SELECT * FROM TAB_EXPEDIENTE_PADRON WHERE N_EXPEDIENTE = :N_EXPEDIENTE AND N_ESTADO NOT IN(:P_ELIMINADO,:P_RECHAZADO) ORDER BY N_EXPEDIENTE_PADRON_PK DESC";
        SQLQuery query = this.getSession().createSQLQuery(sql);
        query.setParameter("N_EXPEDIENTE", codExpediente);
        //query.setParameter("P_OTRA_LISTA", Parametros.ESTADO_ELECTOR_REG_OTRA_LISTA);
        query.setParameter("P_ELIMINADO", Parametros.ESTADO_ELECTOR_ELIMINADO);
        query.setParameter("P_RECHAZADO", Parametros.ESTADO_ELECTOR_RECHAZADO);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<ExpedientePadron> list = query.list();
        return list;
    }

    @Override
    public List<ExpedientePadron> allPendientes(Expediente id) {
        Criteria criteria = this.getSession().createCriteria(ExpedientePadron.class);
        criteria.add(Restrictions.eq("expediente", id));
        criteria.add(Restrictions.eq("flag", 1));
        return (List<ExpedientePadron>) criteria.list();
    }
    
    @Override
    public List<ExpedientePadron> fetchByRangoInicialInd(Expediente expediente, int indicador, int desde) {
        
        Criteria criteria = this.getSession().createCriteria(ExpedientePadron.class);
        criteria.add(Restrictions.eq("expediente", expediente));
        criteria.add(Restrictions.not(Restrictions.in("estado", new Integer[]{Parametros.ESTADO_ELECTOR_ELIMINADO, Parametros.ESTADO_ELECTOR_RECHAZADO})));
        criteria.add(Restrictions.eq("indicador", indicador));
        criteria.add(Restrictions.gt("ordenRegistro", desde));
        criteria.addOrder(Order.asc("ordenRegistro"));
        return (List<ExpedientePadron>) criteria.list();        
    }
    
    @Override
    public List<ExpedientePadron> getByCompaginado(ExpedienteImpresion expImpresion){
        Criteria criteria = this.getSession().createCriteria(ExpedientePadron.class);
        criteria.add(Restrictions.eq("expediente", expImpresion.getExpediente()));
        criteria.add(Restrictions.not(Restrictions.in("estado", new Integer[]{Parametros.ESTADO_ELECTOR_ELIMINADO, Parametros.ESTADO_ELECTOR_RECHAZADO})));
        criteria.add(Restrictions.eq("indicador", expImpresion.getIndicador()));
        criteria.add(Restrictions.gt("ordenRegistro", expImpresion.getRegistroInicial()-1));
        criteria.add(Restrictions.lt("ordenRegistro", expImpresion.getResgitroFinal()+1));
        criteria.addOrder(Order.asc("ordenRegistro"));
        return (List<ExpedientePadron>) criteria.list();    
    }
    @Override
    public List<ExpedientePadron> getByExpedienteIndicador(Expediente expediente, int indicador){
        Criteria criteria = this.getSession().createCriteria(ExpedientePadron.class);
        criteria.add(Restrictions.eq("expediente", expediente));
        criteria.add(Restrictions.not(Restrictions.in("estado", new Integer[]{Parametros.ESTADO_ELECTOR_ELIMINADO, Parametros.ESTADO_ELECTOR_RECHAZADO})));
        criteria.add(Restrictions.eq("indicador", indicador));
        criteria.addOrder(Order.asc("ordenRegistro"));
        return (List<ExpedientePadron>) criteria.list();
    }
    
    @Override
    public List<ExpedientePadron> getElectores(Expediente expediente, String observacion){
        Criteria criteria = this.getSession().createCriteria(ExpedientePadron.class);
        criteria.add(Restrictions.eq("expediente", expediente));
        criteria.add(Restrictions.in("estado", new Integer[]{Parametros.ESTADO_ELECTOR_PENDIENTE}));
        criteria.add(Restrictions.like("observacion", "%" + observacion + "%"));
        criteria.addOrder(Order.asc("apellidoPaterno"));
        criteria.addOrder(Order.asc("apellidoMaterno"));
        criteria.addOrder(Order.asc("nombre"));
        return (List<ExpedientePadron>) criteria.list();
    }
    
    @Override
    public List<ExpedientePadron> getExpedientesByAmbitoAdjunto(Expediente expediente){
        Criteria criteria = this.getSession().createCriteria(ExpedientePadron.class);
        criteria.add(Restrictions.eq("ambito", expediente.getAmbito()));
        criteria.add(Restrictions.in("estado", new Integer[]{Parametros.ESTADO_ELECTOR_ACTIVO, Parametros.ESTADO_ELECTOR_PENDIENTE}));        
        return (List<ExpedientePadron>) criteria.list();
    }
    
    @Override
    public int getCountByExpedienteAndEstado(Expediente expediente,int estado){
        Criteria criteria = this.getSession().createCriteria(ExpedientePadron.class);
        criteria.add(Restrictions.eq("expediente", expediente));
        criteria.add(Restrictions.eq("estado", estado));
        return (Integer) criteria.list().size();
    }
    
    @Override
    public int getCountByExpediente(Expediente expediente){
        Criteria criteria = this.getSession().createCriteria(ExpedientePadron.class);
        criteria.add(Restrictions.eq("expediente", expediente));
        return (Integer) criteria.list().size();
    }
}
