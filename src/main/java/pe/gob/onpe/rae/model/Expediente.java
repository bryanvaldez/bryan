/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.onpe.rae.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import pe.gob.onpe.rae.helper.TypesUtil;

/**
 *
 * @author MArrisueno
 */
@Entity
@Table(name = "tab_expediente")
public class Expediente implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "n_expediente_pk", unique = true, nullable = false)    
    private Long id;

    @Column(name = "c_expediente")    
    private String expediente;
    
    @Column(name = "d_fecha_recepcion")    
    private Date fechaExpediente;
        
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "n_ambito")    
    private Ambito ambito;    
    
    @OneToMany(mappedBy = "expediente", fetch = FetchType.LAZY)
    private List<ExpedientePadron> expedientesPadron;
            
    @OneToMany(mappedBy = "expediente", fetch = FetchType.LAZY)    
    private List<ExpedienteImpresion> expedientesImpresion;
       
//    @OneToMany(mappedBy = "expediente", fetch = FetchType.LAZY)    
//    private List<Elector> electores;        
        
    @Column(name = "n_estado")    
    private int estado;
    
    public Expediente() {
    }
    
    public Expediente(Object id) {
        this.id = TypesUtil.getDefaultLong(id);
    } 

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExpediente() {
        return expediente;
    }

    public void setExpediente(String expediente) {        
        this.expediente = expediente;
    }

    public Date getFechaExpediente() {
        return fechaExpediente;
    }

    public void setFechaExpediente(Date fechaExpediente) {
        this.fechaExpediente = fechaExpediente;
    }

    public List<ExpedientePadron> getExpedientesPadron() {
        return expedientesPadron;
    }

    public void setExpedientesPadron(List<ExpedientePadron> expedientesPadron) {
        this.expedientesPadron = expedientesPadron;
    }

    public List<ExpedienteImpresion> getExpedientesImpresion() {
        return expedientesImpresion;
    }

    public void setExpedientesImpresion(List<ExpedienteImpresion> expedientesImpresion) {
        this.expedientesImpresion = expedientesImpresion;
    }

//    public List<Elector> getElectores() {
//        return electores;
//    }
//
//    public void setElectores(List<Elector> electores) {
//        this.electores = electores;
//    }

    public Ambito getAmbito() {
        return ambito;
    }

    public void setAmbito(Ambito ambito) {
        this.ambito = ambito;
    }     

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
            
}
