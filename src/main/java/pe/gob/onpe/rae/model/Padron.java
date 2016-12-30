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
@Table(name = "padron")
public class Padron implements Serializable{
    
    @Id    
    @Column(name = "numele")
    private String numele;
    
    @Column(name = "digver")
    private String digver;
    
    @Column(name="nummesa")
    private String numMesa;
    
    @Column(name="ubigeo")
    private String ubigeo;
    
    @Column(name="appat")
    private String apePat;
    
    @Column(name="apmat")
    private String apMat;
    
    @Column(name="nombres")
    private String nombres;
    
    @Column(name="fecnac")
    private String fecNac;
    
    @Column(name="sexo")
    private String sexo;
    
    @Column(name="gradins")
    private String gradIns;
    
    @Column(name="tipodoc")
    private String tipoDoc;
    
    @Column(name="restric")
    private String restric;
    
    @Column(name="discap")
    private String disCap;
    
    @Column(name="nummesa_ant")
    private String numMesaAnt;
    
    @Column(name="ubigeo_reniec")
    private String ubigeoReniex;
    
    @Column(name="norden")
    private int nOrden;

    public Padron() {
    }

    public String getNumEle() {
        return numele;
    }

    public void setNumEle(String numEle) {
        this.numele = numEle;
    }

    public String getDigver() {
        return digver;
    }

    public void setDigver(String digver) {
        this.digver = digver;
    }

    public String getNumMesa() {
        return numMesa;
    }

    public void setNumMesa(String numMesa) {
        this.numMesa = numMesa;
    }

    public String getUbigeo() {
        return ubigeo;
    }

    public void setUbigeo(String ubigeo) {
        this.ubigeo = ubigeo;
    }

    public String getApePat() {
        return apePat;
    }

    public void setApePat(String apePat) {
        this.apePat = apePat;
    }

    public String getApMat() {
        return apMat;
    }

    public void setApMat(String apMat) {
        this.apMat = apMat;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getFecNac() {
        return fecNac;
    }

    public void setFecNac(String fecNac) {
        this.fecNac = fecNac;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getGradIns() {
        return gradIns;
    }

    public void setGradIns(String gradIns) {
        this.gradIns = gradIns;
    }

    public String getRestric() {
        return restric;
    }

    public void setRestric(String restric) {
        this.restric = restric;
    }

    public String getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public String getDisCap() {
        return disCap;
    }

    public void setDisCap(String disCap) {
        this.disCap = disCap;
    }

    public String getNumMesaAnt() {
        return numMesaAnt;
    }

    public void setNumMesaAnt(String numMesaAnt) {
        this.numMesaAnt = numMesaAnt;
    }

    public String getUbigeoReniex() {
        return ubigeoReniex;
    }

    public void setUbigeoReniex(String ubigeoReniex) {
        this.ubigeoReniex = ubigeoReniex;
    }

    public int getnOrder() {
        return nOrden;
    }

    public void setnOrder(int nOrder) {
        this.nOrden = nOrder;
    }
    
    
}
