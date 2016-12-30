/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.onpe.rae.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import pe.gob.onpe.rae.helper.TypesUtil;

/**
 *
 * @author AQuispec
 */
@Entity
@Table(name = "tab_elector")
public class Elector implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "n_elector_pk")
    private Long id;

    @Column(name = "c_documento_identidad")
    private String dni;

    @Column(name = "c_digito_verificacion")
    private String digitoVerificacion;
    
    @Column(name = "c_apellido_paterno")
    private String apellidoPaterno;

    @Column(name = "c_apellido_materno")
    private String apellidoMaterno;

    @Column(name = "c_nombre")
    private String nombre;    
    
    @Column(name = "c_ubigeo")
    private String ubigeo; 
    
    public Elector() {
    }
    
    public Elector(Object id) {
        this.id = TypesUtil.getDefaultLong(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getDigitoVerificacion() {
        return digitoVerificacion;
    }

    public void setDigitoVerificacion(String digitoVerificacion) {
        this.digitoVerificacion = digitoVerificacion;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbigeo() {
        return ubigeo;
    }

    public void setUbigeo(String ubigeo) {
        this.ubigeo = ubigeo;
    }

}
