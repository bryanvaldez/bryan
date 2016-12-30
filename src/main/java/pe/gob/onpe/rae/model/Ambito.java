/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.onpe.rae.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import pe.gob.onpe.rae.helper.TypesUtil;

/**
 *
 * @author MArrisueno
 */
@Entity
@Table(name = "tab_ambito")
public class Ambito implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "n_ambito_pk", unique = true, nullable = false)    
    private Long id;

    @Column(name = "c_nombre_ambito")    
    private String nombreAmbito;

    @Column(name = "n_tipo_ambito")
    private int tipoAmbito;
    
    @Column(name="n_categoria")
    private int categoria;

    @Column(name = "c_informacion")
    private String informacion;

    @Column(name = "c_ubigeo")
    private String ubigeo;

    @Column(name = "c_departamento")
    private String departamento;

    @Column(name = "c_provincia")
    private String provincia;

    @Column(name = "c_distrito")
    private String distrito;

    @OneToMany(mappedBy = "ambito")
    private List<Expediente> expedientes;

    @OneToMany(mappedBy = "ambito", fetch = FetchType.LAZY)
    private List<Local> locales;    
    
    @OneToMany(mappedBy = "ambito", fetch = FetchType.LAZY)
    private List<ExpedientePadron> expedientesPadron;    
    
    @Column(name = "n_ambito_padre")    
    private Long ambitoPadre;

    public Ambito() {
    }

    public Ambito(Object id) {
        this.id = TypesUtil.getDefaultLong(id);
    }

    public String getAmbitoCompleto(){
        return this.departamento + "/" + this.provincia + "/" + this.distrito;
    }   

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreAmbito() {
        return nombreAmbito;
    }

    public void setNombreAmbito(String nombreAmbito) {
        this.nombreAmbito = nombreAmbito;
    }

    public int getTipoAmbito() {
        return tipoAmbito;
    }

    public void setTipoAmbito(int tipoAmbito) {
        this.tipoAmbito = tipoAmbito;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public String getInformacion() {
        return informacion;
    }

    public void setInformacion(String informacion) {
        this.informacion = informacion;
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

    public List<Expediente> getExpedientes() {
        return expedientes;
    }

    public void setExpedientes(List<Expediente> expedientes) {
        this.expedientes = expedientes;
    }

    public List<Local> getLocales() {
        return locales;
    }

    public void setLocales(List<Local> locales) {
        this.locales = locales;
    }

    public List<ExpedientePadron> getExpedientesPadron() {
        return expedientesPadron;
    }

    public void setExpedientesPadron(List<ExpedientePadron> expedientesPadron) {
        this.expedientesPadron = expedientesPadron;
    }

    public Long getAmbitoPadre() {
        return ambitoPadre;
    }

    public void setAmbitoPadre(Long ambitoPadre) {
        this.ambitoPadre = ambitoPadre;
    }

}
