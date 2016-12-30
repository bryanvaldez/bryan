/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.onpe.rae.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import pe.gob.onpe.rae.dao.UploadDAO;
import pe.gob.onpe.rae.model.Upload;

/**
 *
 * @author antares
 */
@Repository
public class UploadDAOH extends HibernateTemplate implements UploadDAO {
    
    @Autowired    
    public UploadDAOH(SessionFactory sessionFactory) {
        super (sessionFactory);
    }
    
    @Override
    public List<pe.gob.onpe.excelvalidator.model.Upload> getColumns(int uploadType) {
        Criteria criteria = this.getSession().createCriteria(Upload.class);
        criteria.add(Restrictions.eq("uploadType", uploadType));
        List<Upload> list = criteria.list();        
        
        List<pe.gob.onpe.excelvalidator.model.Upload> columnas = new ArrayList<pe.gob.onpe.excelvalidator.model.Upload>();
        
        for (Upload temp : list) {
            pe.gob.onpe.excelvalidator.model.Upload columna = new pe.gob.onpe.excelvalidator.model.Upload();
            columna.setId(temp.getId());
            columna.setUploadType(temp.getUploadType());
            columna.setExcelColumn(temp.getExcelColumn());
            columna.setTableColumn(temp.getTableColumn());            
            columna.setUnique(temp.getUnique());
            columna.setValidation(temp.getValidation());            
            columna.setMessageValidation(temp.getMessageValidation());
            columna.setComment(temp.getComment());
            columna.setObligatory(temp.getObligatory());
            columna.setOrder(temp.getOrder());

            columnas.add(columna);
        }
        
        return columnas;
    }    
    
}