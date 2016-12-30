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
public class JAmbito {
    private Long codigoAmbito;
    private String nombreAmbito;
    private Integer tipoAmbito;
    private Integer categoria;
    private String ubigeo;
    private String departamento;
    private String provincia;
    private String distrito;
    private Long codigoAmbitoPadre;
    private Long codigoExpediente;

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

    public Integer getTipoAmbito() {
        return tipoAmbito;
    }

    public void setTipoAmbito(Integer tipoAmbito) {
        this.tipoAmbito = tipoAmbito;
    }

    public Integer getCategoria() {
        return categoria;
    }

    public void setCategoria(Integer categoria) {
        this.categoria = categoria;
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

    public Long getCodigoAmbitoPadre() {
        return codigoAmbitoPadre;
    }

    public void setCodigoAmbitoPadre(Long codigoAmbitoPadre) {
        this.codigoAmbitoPadre = codigoAmbitoPadre;
    }

    public Long getCodigoExpediente() {
        return codigoExpediente;
    }

    public void setCodigoExpediente(Long codigoExpediente) {
        this.codigoExpediente = codigoExpediente;
    }
        
}
