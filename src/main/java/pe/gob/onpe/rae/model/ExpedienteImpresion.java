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
@Table(name = "tab_expediente_impresion")
public class ExpedienteImpresion implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "n_expediente_impresion_pk")
    private Long id;

    @Column(name = "n_registro_inicial")
    private int registroInicial;

    @Column(name = "n_registro_final")
    private int resgitroFinal;

    @Column(name = "n_indicador")
    private int indicador;

    @Column(name = "n_pagina")
    private int pagina;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "n_expediente")
    private Expediente expediente;

    public ExpedienteImpresion() {
    }
        
    public ExpedienteImpresion(Object id) {
        this.id = TypesUtil.getDefaultLong(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRegistroInicial() {
        return registroInicial;
    }

    public void setRegistroInicial(int registroInicial) {
        this.registroInicial = registroInicial;
    }

    public int getResgitroFinal() {
        return resgitroFinal;
    }

    public void setResgitroFinal(int resgitroFinal) {
        this.resgitroFinal = resgitroFinal;
    }

    public int getIndicador() {
        return indicador;
    }

    public void setIndicador(int indicador) {
        this.indicador = indicador;
    }

    public int getPagina() {
        return pagina;
    }

    public void setPagina(int pagina) {
        this.pagina = pagina;
    }

    public Expediente getExpediente() {
        return expediente;
    }

    public void setExpediente(Expediente expediente) {
        this.expediente = expediente;
    }

}
