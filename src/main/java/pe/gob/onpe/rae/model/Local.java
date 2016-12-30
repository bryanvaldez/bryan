/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.onpe.rae.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import pe.gob.onpe.rae.helper.TypesUtil;

/**
 *
 * @author AQuispec
 */
@Entity
@Table(name="tab_local")
public class Local implements Serializable{
    
    @Id
    @GeneratedValue
    @Column(name = "n_local_pk", unique = true, nullable = false)
    private Long id;
    
    @Column(name="c_codigo")
    private String codigo;
    
    @Column(name="c_nombre")
    private String nombre;    

    @Column(name="c_ubigeo")
    private String ubigeo;
    
    @Column(name="c_departamento")
    private String departamento;
    
    @Column(name="c_provincia")
    private String provincia;
    
    @Column(name="c_distrito")
    private String distrito;        
    
    @Column(name="c_direccion")
    private String direccion;
    
    @Column(name="c_referencia")
    private String referencia;
    
    @Column(name="n_estado")
    private String estado;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="n_ambito", nullable = true)
    private Ambito ambito;        

    public Local() {
    }       
    
    public Local(Object id) {
        this.id = TypesUtil.getDefaultLong(id);
    }    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }
    
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public Ambito getAmbito() {
        return ambito;
    }

    public void setAmbito(Ambito ambito) {
        this.ambito = ambito;
    }       
    
}
