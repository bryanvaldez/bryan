/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gob.onpe.rae.model.json;

/**
 *
 * @author MArrisueno
 */
public class JExpediente {
    private Long codigoExpediente;
    private String nombreExpediente;
    private String ubigeo;
    private String departamento;
    private String provincia;
    private String distrito;
    private Long codigoAmbito;
    private String nombreAmbito;

    public Long getCodigoExpediente() {
        return codigoExpediente;
    }

    public void setCodigoExpediente(Long codigoExpediente) {
        this.codigoExpediente = codigoExpediente;
    }

    public String getNombreExpediente() {
        return nombreExpediente;
    }

    public void setNombreExpediente(String nombreExpediente) {
        this.nombreExpediente = nombreExpediente;
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

    public Long getCodigoAmbito() {
        return codigoAmbito;
    }

    public void setCodigoAmbito(Long codigoAmbito) {
        this.codigoAmbito = codigoAmbito;
    }

    public String getNombreAmbito() {
        return nombreAmbito;
    }

    public void setNombreAmbito(String nombreAmbito) {
        this.nombreAmbito = nombreAmbito;
    }
    
    
}
