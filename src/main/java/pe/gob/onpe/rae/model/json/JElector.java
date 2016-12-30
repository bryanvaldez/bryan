/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.onpe.rae.model.json;

/**
 *
 * @author AQuispec
 */
public class JElector {
    private String departamento;
    private String provincia;
    private String distrito;
    private String ccpp_principal;
    private String adjunto;
    private Integer total;
    private Integer habilitados;
    private Integer pendientes;
    private Integer rechazados;
    
    public JElector() {
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

    public String getCcpp_principal() {
        return ccpp_principal;
    }

    public void setCcpp_principal(String ccpp_principal) {
        this.ccpp_principal = ccpp_principal;
    }

    public String getAdjunto() {
        return adjunto;
    }

    public void setAdjunto(String adjunto) {
        this.adjunto = adjunto;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getHabilitados() {
        return habilitados;
    }

    public void setHabilitados(Integer habilitados) {
        this.habilitados = habilitados;
    }

    public Integer getPendientes() {
        return pendientes;
    }

    public void setPendientes(Integer pendientes) {
        this.pendientes = pendientes;
    }

    public Integer getRechazados() {
        return rechazados;
    }

    public void setRechazados(Integer rechazados) {
        this.rechazados = rechazados;
    }
    
    
}
