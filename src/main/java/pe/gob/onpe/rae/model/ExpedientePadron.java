/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.onpe.rae.model;

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
@Table(name="tab_expediente_padron")
public class ExpedientePadron implements Serializable{
    
    @Id
    @GeneratedValue
    @Column(name = "n_expediente_padron_pk")
    private Long id; 
    
    @Column(name="c_documento_identidad")
    private String dni;
    
    @Column(name="c_apellido_paterno")
    private String apellidoPaterno;
    
    @Column(name="c_apellido_materno")
    private String apellidoMaterno;
    
    @Column(name="c_nombre")
    private String nombre;    
    
    @Column(name="c_ubigeo_elector")
    private String ubigeoElector;    
    
    @Column(name="c_ubigeo_expediente")
    private String ubigeoLista;    
    
    @Column(name="n_orden_registro")
    private int ordenRegistro;       
    
    @Column(name="n_indicador")
    private int indicador;
    
    @Column(name="n_estado")    
    private int estado;
    
    @Column(name="c_observacion")
    private String observacion;
    
    @Column(name="N_FLAG")
    private int flag;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "n_expediente")
    private Expediente expediente;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="n_ambito")
    private Ambito ambito;
    
    public ExpedientePadron() {
    }
    
    public ExpedientePadron(Object id) {
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

    public String getUbigeoElector() {
        return ubigeoElector;
    }

    public void setUbigeoElector(String ubigeoElector) {
        this.ubigeoElector = ubigeoElector;
    }

    public String getUbigeoLista() {
        return ubigeoLista;
    }

    public void setUbigeoLista(String ubigeoLista) {
        this.ubigeoLista = ubigeoLista;
    }

    public int getOrdenRegistro() {
        return ordenRegistro;
    }

    public void setOrdenRegistro(int ordenRegistro) {
        this.ordenRegistro = ordenRegistro;
    }

    public int getIndicador() {
        return indicador;
    }

    public void setIndicador(int indicador) {
        this.indicador = indicador;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Expediente getExpediente() {
        return expediente;
    }

    public void setExpediente(Expediente expediente) {
        this.expediente = expediente;
    }

    public Ambito getAmbito() {
        return ambito;
    }

    public void setAmbito(Ambito ambito) {
        this.ambito = ambito;
    }    

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }        
}