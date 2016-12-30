package pe.gob.onpe.rae.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author Bryan Valdez <bvaldez at onpe.gob.pe>
 */
@Entity
@Table(name= "vw_ubigeo")
public class UbigeoVw implements Serializable{
    
    @Id
    @Column(name = "c_ubigeo_pk")
    private String id;
    
    @Column(name = "c_departamento")
    private String departamento;
    
    @Column(name = "c_provincia")
    private String provincia;
    
    @Column(name = "c_distrito")
    private String distrito;

    public UbigeoVw() {
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
    
    
    
}
