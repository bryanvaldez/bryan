package pe.gob.onpe.rae.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import pe.gob.onpe.rae.helper.TypesUtil;

/**
 * 
 * @author Bryan Valdez <bvaldez at onpe.gob.pe>
 */
@Entity
@Table(name = "tab_tipo_parametro")
public class TipoParametro implements Serializable{
    @Id
    @GeneratedValue
    @Column(name = "n_tipo_parametro_pk")
    private Long id;
    
    @Column(name = "c_descripcion")
    private String descripcion;

//    @OneToOne(mappedBy = "tipoParametro", fetch = FetchType.LAZY)
//    private Parametro parametro;    
    
    public TipoParametro() {
    }
    
    public TipoParametro(Object id) {
        this.id = TypesUtil.getDefaultLong(id);
    }    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

//    public Parametro getParametro() {
//        return parametro;
//    }
//
//    public void setParametro(Parametro parametro) {
//        this.parametro = parametro;
//    }
    
    
}
