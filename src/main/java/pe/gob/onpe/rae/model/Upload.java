/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.onpe.rae.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author antares
 */
@Entity
@Table(name = "tab_upload")
public class Upload implements Serializable{
    
    @Id
    @GeneratedValue
    @Column(name = "n_upload_pk")
    private int id;
    
    @Column(name = "n_tipo_carga")
    private int uploadType;
    
    @Column(name = "c_columna_excel")
    private String excelColumn;
    
    @Column(name = "c_columna_tabla") 
    private String tableColumn;
    
    @Column(name = "n_unico")
    private int unique;
    
    @Column(name = "c_validacion") 
    private String validation;
    
    @Column(name = "c_mensaje_validacion") 
    private String messageValidation;
    
    @Column(name = "c_comentario") 
    private String comment;
    
    @Column(name = "n_obligatorio") 
    private int obligatory;
    
    @Column(name = "n_orden") 
    private int order;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUploadType() {
        return uploadType;
    }

    public void setUploadType(int uploadType) {
        this.uploadType = uploadType;
    }

    public String getExcelColumn() {
        return excelColumn;
    }

    public void setExcelColumn(String excelColumn) {
        this.excelColumn = excelColumn;
    }

    public String getTableColumn() {
        return tableColumn;
    }

    public void setTableColumn(String tableColumn) {
        this.tableColumn = tableColumn;
    }

    public int getUnique() {
        return unique;
    }

    public void setUnique(int unique) {
        this.unique = unique;
    }

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }

    public String getMessageValidation() {
        return messageValidation;
    }

    public void setMessageValidation(String messageValidation) {
        this.messageValidation = messageValidation;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getObligatory() {
        return obligatory;
    }

    public void setObligatory(int obligatory) {
        this.obligatory = obligatory;
    }

    public int getOrder() {
        return order;
    }
    public void setOrder(int order) {
        this.order = order;
    }
    
    
}