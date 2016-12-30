/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.onpe.rae.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author AQuispec
 */
@Entity
@Table(name = "ubigeo")
public class Ubigeo implements Serializable{
    
    @Id    
    @Column(name = "ubigeo")
    private String ubigeo;
    
    @Column(name="descripcion")
    private String descripcion;
    
    @Column(name="ubipadre")
    private String ubiPadre;
    
    @Column(name="odpe")
    private String odpe;
    
    @Column(name="ccomputo")
    private String ccomputo;
    
    @Column(name="capital")
    private String capital;
    
    @Column(name="region")
    private String region;
    
    @Column(name="distritoe")
    private String distritoE;
    
    @Column(name="sede_odpe")
    private String sedeOdpe;

    public Ubigeo() {
    }

    public String getUbigeo() {
        return ubigeo;
    }

    public void setUbigeo(String ubigeo) {
        this.ubigeo = ubigeo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUbiPadre() {
        return ubiPadre;
    }

    public void setUbiPadre(String ubiPadre) {
        this.ubiPadre = ubiPadre;
    }

    public String getOdpe() {
        return odpe;
    }

    public void setOdpe(String odpe) {
        this.odpe = odpe;
    }

    public String getCcomputo() {
        return ccomputo;
    }

    public void setCcomputo(String ccomputo) {
        this.ccomputo = ccomputo;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDistritoE() {
        return distritoE;
    }

    public void setDistritoE(String distritoE) {
        this.distritoE = distritoE;
    }

    public String getSedeOdpe() {
        return sedeOdpe;
    }

    public void setSedeOdpe(String sedeOdpe) {
        this.sedeOdpe = sedeOdpe;
    }
    
    
}
