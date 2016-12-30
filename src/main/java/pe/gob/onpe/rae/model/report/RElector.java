/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.onpe.rae.model.report;

/**
 *
 * @author AQuispec
 */
public class RElector {

    private int ordenRegistro;
    private String documentoIdentidad;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombre;
    private String distritoElector;
    private String observacion;
    private String subsanarObservacion;

    public int getOrdenRegistro() {
        return ordenRegistro;
    }

    public void setOrdenRegistro(int ordenRegistro) {
        this.ordenRegistro = ordenRegistro;
    }

    public String getDocumentoIdentidad() {
        return documentoIdentidad;
    }

    public void setDocumentoIdentidad(String documentoIdentidad) {
        this.documentoIdentidad = documentoIdentidad;
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

    public String getDistritoElector() {
        return distritoElector;
    }

    public void setDistritoElector(String distritoElector) {
        this.distritoElector = distritoElector;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getSubsanarObservacion() {
        return subsanarObservacion;
    }

    public void setSubsanarObservacion(String subsanarObservacion) {
        this.subsanarObservacion = subsanarObservacion;

    }
}
