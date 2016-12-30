package pe.gob.onpe.rae.dao.hibernate;

import java.util.List;
import net.sf.jasperreports.engine.util.LinkedMap;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import pe.gob.onpe.rae.constant.Parametros;
import pe.gob.onpe.rae.dao.ReporteDAO;
import pe.gob.onpe.rae.model.Reporte;
import pe.gob.onpe.rae.model.json.Filtro;

/**
 *
 * @author Manuel Arrisueno <marrisueno at onpe.gob.pe>
 */
@Repository
public class ReporteDAOH extends HibernateTemplate implements ReporteDAO {

    private JdbcTemplate jdbcTemplateObject;

    public void setDataSource(SimpleDriverDataSource dataSource) {
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    @Autowired
    public ReporteDAOH(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Reporte find(Reporte t) {
        Criteria criteria = this.getSession().createCriteria(Reporte.class);
        criteria.add(Restrictions.eq("id", t.getId()));
        return (Reporte) criteria.uniqueResult();
    }

    @Override
    public List<Reporte> all() {
        Criteria criteria = this.getSession().createCriteria(Reporte.class);
        return criteria.list();
    }

    @Override
    public void saveDAO(Reporte t) {
        this.save(t);
    }

    @Override
    public void updateDAO(Reporte t) {
        this.merge(t);
    }

    @Override
    public void deleteDAO(Reporte t) {
        this.delete(t);
    }

    @Override
    public List execute(String sql, Filtro filter) {
        if (filter.getFiltro().equals("reporteForm1")) {
            sql = sql.replace(":UBIGEO", "'" + filter.getUbigeo() + "%'");
        }
        if (filter.getFiltro().equals("reporteForm2")) {
            sql = sql.replace(":UBIGEO", "'" + filter.getUbigeo() + "%'");
            sql = sql.replace(":FECHAINI", "'" + filter.getFechaIni() + "'");
            sql = sql.replace(":FECHAFIN", "'" + filter.getFechaFin() + "'");
        }
        if (filter.getFiltro().equals("reporteForm3")) {
            sql = sql.replace(":AMBITO", String.valueOf(filter.getAmbito()));
            String estados = "";
            if (filter.getHabilitado() == 1) {
                estados = addEstado(estados, Parametros.ESTADO_FINAL_ELECTOR_HABILITADO);
            }
            if (filter.getPendiente() == 1) {
                estados = addEstado(estados, Parametros.ESTADO_FINAL_ELECTOR_PENDIENTE);
            }
            if (filter.getRechazado() == 1) {
                estados = addEstado(estados, Parametros.ESTADO_FINAL_ELECTOR_RECHAZADO);
            }
            sql = sql.replace(":ESTADO", estados);
        }
        return jdbcTemplateObject.queryForList(sql);
    }

    @Override 
    public List exportarExpediente(int id){
        String sql = "SELECT N_ORDEN_REGISTRO, N_EXPEDIENTE, N_AMBITO, C_UBIGEO_ELECTOR, C_DOCUMENTO_IDENTIDAD, C_APELLIDO_PATERNO, C_APELLIDO_MATERNO, C_NOMBRE, N_ESTADO FROM TAB_EXPEDIENTE_PADRON WHERE N_EXPEDIENTE = :lista ORDER BY N_ORDEN_REGISTRO";
        sql = sql.replace(":lista", String.valueOf(id));
        return jdbcTemplateObject.queryForList(sql);
    }
    
    private String addEstado(String estado, int codeEstado){
        if (estado.equals("")) {
            estado = estado + codeEstado;
        }else{
            estado = estado + "," + codeEstado;
        }
        return estado;
    }
}
