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
public class JCategoria {
    private Integer codigoCategoria;
    private String categoria;

    public JCategoria(Integer codigoCategoria, String categoria) {
        this.codigoCategoria = codigoCategoria;
        this.categoria = categoria;
    }

    public JCategoria(){        
    }
    
    public Integer getCodigoCategoria() {
        return codigoCategoria;
    }

    public void setCodigoCategoria(Integer codigoCategoria) {
        this.codigoCategoria = codigoCategoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
       
}
